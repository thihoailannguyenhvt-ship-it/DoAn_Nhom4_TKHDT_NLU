package module3_GiamSatVaQuanLyNguong.NhanHanhVi;
import module3_GiamSatVaQuanLyNguong.In.*;
/**
 * Chiến lược xử lý DINH_BAY – cài đặt phần [A]: xét độc lập nhãn hành vi.
 *
 * Ủy quyền toàn bộ logic cho {@link BoDanhGiaHanhVi}.
 * Kết quả trả về dựa hoàn toàn vào ngưỡng thời gian 15 phút.
 */


public class ChienLuocXuLyDinhBay implements ChienLuocDanhGia {
    private final BoDanhGiaHanhVi boDanhGia;

    public ChienLuocXuLyDinhBay(BoDanhGiaHanhVi boDanhGia) {
        this.boDanhGia = boDanhGia;
    }

    @Override
    public KetQuaDanhGia thucHienDanhGia(BanGhiSinhHoc goiTin, BanGhiSinhHoc hoSo) {
        // Ủy quyền gói tin cho bộ đánh giá xử lý tính toán cửa sổ trượt 15 phút
        return boDanhGia.danhGia(goiTin);
    }
}
