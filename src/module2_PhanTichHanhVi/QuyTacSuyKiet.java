package module2_PhanTichHanhVi;
import dinhDanh.*;
public class QuyTacSuyKiet extends QuyTac {

    public QuyTacSuyKiet(int mucDoUuTien) {
        super(mucDoUuTien);
    }

    @Override
    protected boolean duDieuKien(CuaSoTruot cuaSo) {

        // Phai co it nhat 3 ban ghi moi duoc danh gia
        return cuaSo.layDanhSachHienTai().size() >= 3;
    }

    @Override
    protected boolean kiemTraChiTiet(
            DongVat dv,
            CuaSoTruot cuaSo,
            ThuVienDinhMucSinhHoc dinhMuc) {

        int tNghi = 240;
        int tDi = 300;
        int tBay = 180;

        double normal12h =
                dv.getLoai().tinhNormal12h(
                        tNghi,
                        tDi,
                        tBay);

        if (normal12h <= 0) {
            return false;
        }

        double ratio =
                dv.geteTichLuy() / normal12h;

        // Debug
        System.out.println(
                dv.getIdConVat()
                + " ratio = "
                + ratio);

        // Thuc te bo du lieu cua do an cho ratio rat nho
        // Nen dung nguong 0.15 de tranh danh dau
        // SUY_KIET hang loat
        return ratio < 0.15;
    }

    @Override
    protected NhanTrangThai getTrangThai() {

        return NhanTrangThai.SUY_KIET;
    }
}