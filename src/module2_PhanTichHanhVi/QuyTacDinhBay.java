package module2_PhanTichHanhVi;
import dinhDanh.*;
import java.util.LinkedList;
import java.util.Queue;

public class QuyTacDinhBay extends QuyTac {

	public QuyTacDinhBay(int mucDoUuTien) {
		super(mucDoUuTien);
	}

	@Override
	protected boolean duDieuKien(CuaSoTruot cuaSo) {
		return cuaSo.layDanhSachHienTai().size() >= 2;
	}

	@Override
	protected boolean kiemTraChiTiet(DongVat dv, CuaSoTruot cuaSo, ThuVienDinhMucSinhHoc dinhMuc) {

		Queue<BangGhiSinhHoc> buffer = cuaSo.layDanhSachHienTai();

		BoTinhToanSinhHoc boTinhToan = new BoTinhToanSinhHoc();

		double odbaTB = boTinhToan.tinhODBA(buffer);

		LinkedList<BangGhiSinhHoc> ds = new LinkedList<>(buffer);

		double khoangCach = boTinhToan.tinhKhoangCach(ds.getFirst().getViTriToaDo(), ds.getLast().getViTriToaDo());

		return odbaTB < dinhMuc.getMucHoatDongToiThieu() && khoangCach < 0.001;
	}

	@Override
	protected NhanTrangThai getTrangThai() {
		// TODO Auto-generated method stub
		return NhanTrangThai.DINH_BAY;
	}
}
