package module2_PhanTichHanhVi;
import dinhDanh.*;
import java.util.Queue;

public abstract class QuyTac {

	protected int mucDoUuTien;

	public QuyTac(int mucDoUuTien) {
		this.mucDoUuTien = mucDoUuTien;
	}

	// Template Method
	public final KetQuaHanhVi danhGia(DongVat dv, CuaSoTruot cuaSo, ThuVienDinhMucSinhHoc dinhMuc) {

		if (!duDieuKien(cuaSo))
			return null;

		if (kiemTraChiTiet(dv, cuaSo, dinhMuc))
			return new KetQuaHanhVi(getTrangThai());

		return null;
	}

	protected abstract boolean duDieuKien(CuaSoTruot cuaSo);

	protected abstract boolean kiemTraChiTiet(DongVat dv, CuaSoTruot cuaSo, ThuVienDinhMucSinhHoc dinhMuc);

	protected abstract NhanTrangThai getTrangThai();
}
