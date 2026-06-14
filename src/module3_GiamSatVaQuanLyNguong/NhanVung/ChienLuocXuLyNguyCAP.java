package module3_GiamSatVaQuanLyNguong.NhanVung;

import dinhDanh.MucDoNghiemTrong;
import module3_GiamSatVaQuanLyNguong.In.*;


/**
 * Chiến lược xử lý vùng NGUY_CAP.
 * Phát KHAN_CAP ngay lập tức – không qua cửa sổ, không cần tích lũy.
 * Con vật (dù bình thường) đang ở khu dân cư là mối nguy hiểm tức thì.
 */
public class ChienLuocXuLyNguyCAP implements ChienLuocDanhGiaVung {

    private final BoDanhGiaVung boDanhGia;

    public ChienLuocXuLyNguyCAP(BoDanhGiaVung boDanhGia) {
        this.boDanhGia = boDanhGia;
    }

    @Override
    public KetQuaDanhGia thucHienDanhGia(BanGhiSinhHoc goiTin) {
        // Dọn cửa sổ cũ nếu có
        boDanhGia.xoaCuaSo(goiTin.layIdCaThe());
        return KetQuaDanhGia.guiThangM5(
            MucDoNghiemTrong.KHAN_CAP,
            "Vùng NGUY_CAP – con vật ở khu dân cư, gửi thẳng Module 5"
        );
    }
}

