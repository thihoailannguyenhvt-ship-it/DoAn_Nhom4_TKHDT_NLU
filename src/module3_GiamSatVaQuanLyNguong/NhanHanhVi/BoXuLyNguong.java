package module3_GiamSatVaQuanLyNguong.NhanHanhVi;
import module3_GiamSatVaQuanLyNguong.In.*;
import java.util.*;
import dinhDanh.*;
import module3_GiamSatVaQuanLyNguong.In.*;
/**
 * BoXuLyNguong: Lớp điều phối trung tâm.
 * Nhiệm vụ: Tiếp nhận gói tin -> Quản lý hồ sơ -> Chọn chuyên gia (Chiến lược) để xử lý.
 */
public class BoXuLyNguong {

    // Kho lưu trữ hồ sơ theo dõi (ID con vật -> Hồ sơ của nó)
    private final Map<String, BanGhiSinhHoc> khoLuuTruHoSo = new HashMap<>();
    
    // Danh mục các "Chuyên gia" (Chiến lược) - Khớp theo NhanTrangThai trong bản ghi của bạn
    private final Map<NhanTrangThai, ChienLuocDanhGia> danhMucChienLuoc = new EnumMap<>(NhanTrangThai.class);
    
    // Bộ lưu trữ lịch sử cửa sổ trượt 15 phút dùng chung cho các chiến lược
    private final BoDanhGiaHanhVi boDanhGiaChung;

    public BoXuLyNguong() {
        this.boDanhGiaChung = new BoDanhGiaHanhVi();

        // Đăng ký các chiến lược vào đây dựa trên NhanTrangThai chuẩn của bạn
        // Sau này muốn thêm hành vi mới, chỉ cần thêm 1 dòng ở đây
        danhMucChienLuoc.put(NhanTrangThai.SUY_KIET, new ChienLuocXuLySuyKiet(boDanhGiaChung));
        danhMucChienLuoc.put(NhanTrangThai.DINH_BAY, new ChienLuocXuLyDinhBay(boDanhGiaChung));
        danhMucChienLuoc.put(NhanTrangThai.BINH_THUONG, new ChienLuocXuLyBinhThuong(boDanhGiaChung));
    }

    /**
     * Phương thức chính để xử lý mọi gói tin đi vào
     */
    public KetQuaDanhGia xuLy(BanGhiSinhHoc goiTin) {
        // Sử dụng chính xác các hàm lay...() từ BanGhiSinhHoc của bạn
        String id = goiTin.layIdCaThe();
        NhanTrangThai trangThaiMoi = goiTin.layNhanTrangThai();

        // 1. Quản lý trạng thái: Lấy hồ sơ cũ
        BanGhiSinhHoc hoSo = khoLuuTruHoSo.get(id);

        // 2. Chống nhiễu: Nếu nhãn thay đổi đột ngột -> Reset hồ sơ
        if (hoSo != null && hoSo.layNhanTrangThai() != trangThaiMoi) {
            khoLuuTruHoSo.remove(id);
            boDanhGiaChung.xoaCuaSo(id); // Đồng bộ xóa cửa sổ trượt lịch sử bên trong thuật toán
            hoSo = null;
        }

        // 3. Khởi tạo hồ sơ nếu chưa có
        if (hoSo == null) {
            // Vì BanGhiSinhHoc của bạn yêu cầu đầy đủ 6 tham số, 
            // việc gán thẳng gói tin hiện tại làm hồ sơ gốc là chuẩn xác nhất và không bịa đặt constructor mới.
            hoSo = goiTin; 
            khoLuuTruHoSo.put(id, hoSo);
        }

        // 4. Phân công: Tìm chiến lược phù hợp
        ChienLuocDanhGia chienLuoc = danhMucChienLuoc.getOrDefault(
            trangThaiMoi, 
            new ChienLuocMacDinh() // Nếu không biết là gì, dùng chiến lược mặc định
        );

        // 5. Thực thi
        KetQuaDanhGia ketQua = chienLuoc.thucHienDanhGia(goiTin, hoSo);

        // 6. Dọn dẹp: Nếu đã xác nhận xong (DA_XAC_NHAN) thì xóa khỏi kho lưu trữ để tiết kiệm RAM
        // ĐÃ SỬA: Điền getTrangThai() vào chỗ trống bị khuyết
        if (ketQua.getTrangThai() == TrangThaiThamDinh.DA_XAC_NHAN && 
            ketQua.getHanhDong() != LoaiHanhDong.DANG_THEO_DOI) {
            khoLuuTruHoSo.remove(id);
            boDanhGiaChung.xoaCuaSo(id); // Đồng bộ giải phóng bộ nhớ ở bộ tính toán cửa sổ trượt
        }

        return ketQua;
    }
}
