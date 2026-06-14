package module2_PhanTichHanhVi;
import dinhDanh.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class BoXuLyQuyTac {
	private List<QuyTac> danhSachQuyTac = new ArrayList<>();

	public void themQuyTac(QuyTac qt) {

		danhSachQuyTac.add(qt);
	}

	public KetQuaHanhVi danhGiaTatCa(DongVat dv, CuaSoTruot cuaSoDinhBay, CuaSoTruot cuaSoSuyKiet,
			ThuVienDinhMucSinhHoc dinhMuc) {

		for (QuyTac qt : danhSachQuyTac) {

			CuaSoTruot cuaSo = (qt instanceof QuyTacDinhBay) ? cuaSoDinhBay : cuaSoSuyKiet;

			KetQuaHanhVi kq = qt.danhGia(dv, cuaSo, dinhMuc);

			if (kq != null)
				return kq;
		}

		return new KetQuaHanhVi(NhanTrangThai.BINH_THUONG);
	}
}
