package module3_GiamSatVaQuanLyNguong.NhanVung;

import module3_GiamSatVaQuanLyNguong.In.*;


/**
 * Chiến lược xử lý vùng TIEN_CANH_BAO.
 * Ủy quyền cho BoDanhGiaVung – tích lũy cửa sổ và kiểm tra đồng nhất.
 */
public class ChienLuocXuLyTienCanhBao implements ChienLuocDanhGiaVung {

    private final BoDanhGiaVung boDanhGia;

    public ChienLuocXuLyTienCanhBao(BoDanhGiaVung boDanhGia) {
        this.boDanhGia = boDanhGia;
    }

    @Override
    public KetQuaDanhGia thucHienDanhGia(BanGhiSinhHoc goiTin) {
        return boDanhGia.danhGia(goiTin);
    }
}

