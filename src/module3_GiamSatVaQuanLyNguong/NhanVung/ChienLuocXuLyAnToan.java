package module3_GiamSatVaQuanLyNguong.NhanVung;

import module3_GiamSatVaQuanLyNguong.In.*;


/**
 * Chiến lược xử lý vùng AN_TOAN.
 * Reset cửa sổ, trả về DANG_THEO_DOI – không có gì đáng lo.
 */
public class ChienLuocXuLyAnToan implements ChienLuocDanhGiaVung {

    private final BoDanhGiaVung boDanhGia;

    public ChienLuocXuLyAnToan(BoDanhGiaVung boDanhGia) {
        this.boDanhGia = boDanhGia;
    }

    @Override
    public KetQuaDanhGia thucHienDanhGia(BanGhiSinhHoc goiTin) {
        boDanhGia.xoaCuaSo(goiTin.layIdCaThe());
        return KetQuaDanhGia.dangTheoDoi("AN_TOAN – cửa sổ vùng đã reset");
    }
}

