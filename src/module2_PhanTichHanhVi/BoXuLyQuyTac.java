package module2_PhanTichHanhVi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dinhDanh.NhanTrangThai;

public class BoXuLyQuyTac {
	private List<QuyTac> danhSachQuyTac = new ArrayList<>();

	public void themQuyTac(QuyTac qt) {
		danhSachQuyTac.add(qt);
	}

	
	public KetQuaHanhVi danhGiaTatCa(DongVat dv, Map<String, CuaSoTruot> quanLy, ThuVienDinhMucSinhHoc dinhMuc) {

		for (QuyTac qt : danhSachQuyTac) {
			
			CuaSoTruot cuaSo = quanLy.get(qt.getLoaiCuaSoCanThiet());

			KetQuaHanhVi kq = qt.danhGia(dv, cuaSo, dinhMuc);

			if (kq != null)
				return kq;
		}

		return new KetQuaHanhVi(NhanTrangThai.BINH_THUONG);
	}
}