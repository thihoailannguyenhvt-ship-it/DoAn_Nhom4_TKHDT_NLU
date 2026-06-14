package module3_GiamSatVaQuanLyNguong.NhanHanhVi;

import module3_GiamSatVaQuanLyNguong.In.*;
import dinhDanh.TrangThaiThamDinh;
import dinhDanh.LoaiHanhDong;

/**
 * Chiến lược xử lý BINH_THUONG.
 *
 * Con vật trở lại trạng thái bình thường → xóa cửa sổ lịch sử,
 * trả về DANG_THEO_DOI để BoXuLyNguong dọn hồ sơ.
 * KHÔNG gửi Module 5 vì không có sự cố gì để báo cáo.
 */
public class ChienLuocXuLyBinhThuong implements ChienLuocDanhGia {

    private final BoDanhGiaHanhVi boDanhGia;

    public ChienLuocXuLyBinhThuong(BoDanhGiaHanhVi boDanhGia) {
        this.boDanhGia = boDanhGia;
    }

    @Override
    public KetQuaDanhGia thucHienDanhGia(BanGhiSinhHoc goiTin, BanGhiSinhHoc hoSo) {
        // Xóa toàn bộ lịch sử cửa sổ – con vật đã an toàn trở lại
        boDanhGia.xoaCuaSo(goiTin.layIdCaThe());

        // DANG_THEO_DOI để BoXuLyNguong biết dọn hồ sơ, không phát lệnh gì
        return new KetQuaDanhGia(
            TrangThaiThamDinh.DANG_THEO_DOI,
            LoaiHanhDong.DANG_THEO_DOI,
            "Cá thể trở lại BINH_THUONG – xóa hồ sơ theo dõi"
        );
    }
}

