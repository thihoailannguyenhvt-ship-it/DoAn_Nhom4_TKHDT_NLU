package module3_GiamSatVaQuanLyNguong.NhanHanhVi;

import module3_GiamSatVaQuanLyNguong.In.*;
import dinhDanh.*;

import java.util.EnumMap;
import java.util.Map;

/**
 * BoXuLyNguong – điều phối chiến lược hành vi.
 *
 * FIX #1: Bỏ khoLuuTruHoSo (BanGhiSinhHoc) – không cần thiết vì
 *         BoDanhGiaHanhVi đã tự quản lý cửa sổ trượt theo idCaThe.
 *         Giữ một Map thứ 2 chỉ để track nhãn cuối cùng phục vụ "chống nhiễu".
 *
 * FIX #2: Logic dọn dẹp sau DA_XAC_NHAN được sửa lại:
 *         Chỉ xóa khi hành động là GUI_THANG_M5 (thay vì kiểm tra
 *         hanhDong != DANG_THEO_DOI – điều kiện cũ không bắt đúng case).
 */
public class BoXuLyNguong {

    // FIX #1: Chỉ lưu nhãn cuối để phát hiện thay đổi nhãn đột ngột
    private final Map<String, NhanTrangThai> nhanCuoiCung;

    private final Map<NhanTrangThai, ChienLuocDanhGia> danhMucChienLuoc;
    private final BoDanhGiaHanhVi boDanhGiaChung;

    public BoXuLyNguong() {
        this.nhanCuoiCung    = new java.util.HashMap<>();
        this.boDanhGiaChung  = new BoDanhGiaHanhVi();
        this.danhMucChienLuoc = new EnumMap<>(NhanTrangThai.class);

        danhMucChienLuoc.put(NhanTrangThai.SUY_KIET,    new ChienLuocXuLySuyKiet(boDanhGiaChung));
        danhMucChienLuoc.put(NhanTrangThai.DINH_BAY,    new ChienLuocXuLyDinhBay(boDanhGiaChung));
        danhMucChienLuoc.put(NhanTrangThai.BINH_THUONG, new ChienLuocXuLyBinhThuong(boDanhGiaChung));
    }

    public KetQuaDanhGia xuLy(BanGhiSinhHoc goiTin) {
        String        id          = goiTin.layIdCaThe();
        NhanTrangThai trangThaiMoi = goiTin.layNhanTrangThai();

        // Chống nhiễu: nhãn thay đổi đột ngột → reset cửa sổ
        NhanTrangThai nhanCu = nhanCuoiCung.get(id);
        if (nhanCu != null && nhanCu != trangThaiMoi) {
            boDanhGiaChung.xoaCuaSo(id);
        }
        nhanCuoiCung.put(id, trangThaiMoi);

        // Chọn chiến lược
        ChienLuocDanhGia chienLuoc = danhMucChienLuoc.getOrDefault(
            trangThaiMoi, new ChienLuocMacDinh()
        );

        // FIX #2: truyền goiTin cả 2 tham số (goiTin, goiTin) vì không có hoSo riêng
        KetQuaDanhGia ketQua = chienLuoc.thucHienDanhGia(goiTin, goiTin);

        // Dọn dẹp khi đã gửi M5 thành công
        if (ketQua.getHanhDong() == LoaiHanhDong.GUI_THANG_M5) {
            nhanCuoiCung.remove(id);
            boDanhGiaChung.xoaCuaSo(id);
        }

        return ketQua;
    }
}