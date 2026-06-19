package module4_GeofenceDongVaNguCanhKhongGian;

import java.util.List;

public class VungFactory {

	public static VungKhongGian taoTramQuanSat(String idVung, String tenVung, Diem tam, double banKinhBaoVeMet) {
		return new TramQuanSat(idVung, tenVung, tam, banKinhBaoVeMet);
	}

	public static VungKhongGian taoKhuDanCu(String idVung, String tenVung, Diem toaDoTam, List<Diem> vertices) {
		return new KhuDanCu(idVung, tenVung, toaDoTam, vertices);
	}

	public static VungKhongGian taoTuyenDuongDuLich(String idVung, String tenVung, List<Diem> danhSachDinh,
			double doRongBufferMet) {
		return new TuyenDuongDuLich(idVung, tenVung, danhSachDinh, doRongBufferMet);
	}
}