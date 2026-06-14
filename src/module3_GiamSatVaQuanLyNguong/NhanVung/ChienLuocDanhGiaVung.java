package module3_GiamSatVaQuanLyNguong.NhanVung;

import module3_GiamSatVaQuanLyNguong.In.*;


/**
 * Interface chiến lược đánh giá vùng – song song với ChienLuocDanhGia bên NhanHanhVi.
 * BoXuLyNguongVung dùng interface này để chọn chiến lược xử lý vùng phù hợp.
 */
public interface ChienLuocDanhGiaVung {
    KetQuaDanhGia thucHienDanhGia(BanGhiSinhHoc goiTin);
}

