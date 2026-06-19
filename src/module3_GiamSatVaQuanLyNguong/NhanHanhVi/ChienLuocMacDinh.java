package module3_GiamSatVaQuanLyNguong.NhanHanhVi;


import module3_GiamSatVaQuanLyNguong.In.*;

import dinhDanh.TrangThaiThamDinh;
import dinhDanh.LoaiHanhDong;

public class ChienLuocMacDinh implements ChienLuocDanhGia {

    @Override
    public KetQuaDanhGia thucHienDanhGia(BanGhiSinhHoc goiTin, BanGhiSinhHoc hoSo) {
      
        return new KetQuaDanhGia(
            TrangThaiThamDinh.DANG_THEO_DOI, 
            LoaiHanhDong.DANG_THEO_DOI, 
            "Hành vi chưa xác định – tiếp tục theo dõi hệ thống"
        );
    }
}

	

