package module3_GiamSatVaQuanLyNguong.NhanHanhVi;

import dinhDanh.MucDoNghiemTrong;
import module3_GiamSatVaQuanLyNguong.In.*;

public class ChienLuocXuLySuyKiet implements ChienLuocDanhGia {

    private final BoDanhGiaHanhVi boDanhGia;

    public ChienLuocXuLySuyKiet(BoDanhGiaHanhVi boDanhGia) {
        this.boDanhGia = boDanhGia;
    }

    @Override
    public KetQuaDanhGia thucHienDanhGia(BanGhiSinhHoc goiTin, BanGhiSinhHoc hoSo) {
        // Giải phóng cửa sổ trượt của cá thể này
        boDanhGia.xoaCuaSo(goiTin.layIdCaThe());

        // Dùng static factory để tường minh MucDoNghiemTrong – không để tự suy luận
        // Đã cập nhật truyền thêm thông tin không gian (idVung, loaiVung, phanTram) từ goiTin
        return KetQuaDanhGia.guiThangM5(
            MucDoNghiemTrong.CANH_BAO,
            "Xác nhận cá thể có dấu hiệu SUY_KIET nghiêm trọng – gửi thẳng Module 5",
            goiTin.layIdVung(),
            goiTin.layLoaiVung(),
            goiTin.layPhanTramGiaoThoa()
        );
    }
}
