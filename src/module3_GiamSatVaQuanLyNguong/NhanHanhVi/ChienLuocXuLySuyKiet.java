package module3_GiamSatVaQuanLyNguong.NhanHanhVi;

import module3_GiamSatVaQuanLyNguong.In.*;


import dinhDanh.TrangThaiThamDinh;
import dinhDanh.LoaiHanhDong;

public class ChienLuocXuLySuyKiet implements ChienLuocDanhGia {
    private final BoDanhGiaHanhVi boDanhGia;

    public ChienLuocXuLySuyKiet(BoDanhGiaHanhVi boDanhGia) {
        this.boDanhGia = boDanhGia;
    }

    @Override
    public KetQuaDanhGia thucHienDanhGia(BanGhiSinhHoc goiTin, BanGhiSinhHoc hoSo) {
        // Sử dụng hàm layIdCaThe() chuẩn của bạn để giải phóng cửa sổ trượt
        boDanhGia.xoaCuaSo(goiTin.layIdCaThe());

        // Gửi thẳng thông tin đi xử lý khẩn cấp, kích hoạt xóa hồ sơ ở Bước 6 của BoXuLyNguong
        return new KetQuaDanhGia(
            TrangThaiThamDinh.DA_XAC_NHAN, 
            LoaiHanhDong.GUI_THANG_M5, 
            "Xác nhận cá thể có dấu hiệu SUY_KIỆT nghiêm trọng – gửi thẳng Module 5"
        );
    }
}
