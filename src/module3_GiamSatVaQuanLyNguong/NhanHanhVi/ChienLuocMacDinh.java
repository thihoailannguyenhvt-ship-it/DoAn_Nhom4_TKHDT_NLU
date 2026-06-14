package module3_GiamSatVaQuanLyNguong.NhanHanhVi;


import module3_GiamSatVaQuanLyNguong.In.*;

import dinhDanh.TrangThaiThamDinh;
import dinhDanh.LoaiHanhDong;

public class ChienLuocMacDinh implements ChienLuocDanhGia {

    @Override
    public KetQuaDanhGia thucHienDanhGia(BanGhiSinhHoc goiTin, BanGhiSinhHoc hoSo) {
        // Trả về trạng thái CHUA_XAC_NHAN và DANG_THEO_DOI để hệ thống tiếp tục giữ hồ sơ
        return new KetQuaDanhGia(
            TrangThaiThamDinh.DANG_THEO_DOI, 
            LoaiHanhDong.DANG_THEO_DOI, 
            "Hành vi chưa xác định – tiếp tục theo dõi hệ thống"
        );
    }
}

	

