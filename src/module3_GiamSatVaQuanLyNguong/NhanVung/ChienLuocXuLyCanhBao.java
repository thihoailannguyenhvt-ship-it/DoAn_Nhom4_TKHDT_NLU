package module3_GiamSatVaQuanLyNguong.NhanVung;

import module3_GiamSatVaQuanLyNguong.In.*;


/**
 * Chiến lược xử lý vùng CANH_BAO.
 * Ủy quyền cho BoDanhGiaVung – tích lũy cửa sổ và kiểm tra đồng nhất.
 * Mức CANH_BAO có trọng số cao hơn TIEN_CANH_BAO khi tính mức độ nghiêm trọng.
 */
public class ChienLuocXuLyCanhBao implements ChienLuocDanhGiaVung {

    private final BoDanhGiaVung boDanhGia;

    public ChienLuocXuLyCanhBao(BoDanhGiaVung boDanhGia) {
        this.boDanhGia = boDanhGia;
    }

    @Override
    public KetQuaDanhGia thucHienDanhGia(BanGhiSinhHoc goiTin) {
        return boDanhGia.danhGia(goiTin);
    }
}


