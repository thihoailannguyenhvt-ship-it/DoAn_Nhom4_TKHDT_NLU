package module4_GeofenceDongVaNguCanhKhongGian;

import java.util.List;

public class TuyenDuongDuLich extends VungKhongGian {
	private List<Diem> danhSachDinh;
	private double doRongBuffer;

	public TuyenDuongDuLich(String idVung, String tenVung, List<Diem> danhSachDinh, double doRongBuffer) {

		super(idVung, tenVung, tinhTamTrungBinh(danhSachDinh));
		this.danhSachDinh = danhSachDinh;
		this.doRongBuffer = doRongBuffer;
	}

	@Override
	public double tinhToanGiaoThoa(Diem tamGeofence, double banKinhGeofenceDo) {

		double banKinhGeofenceMet = banKinhGeofenceDo * 111320.0;

		for (int i = 0; i < danhSachDinh.size() - 1; i++) {
			Diem p1 = danhSachDinh.get(i);
			Diem p2 = danhSachDinh.get(i + 1);

			double khoangCachDenDoan = tinhKhoangCachDenDoanThang(tamGeofence, p1, p2);

			if (khoangCachDenDoan <= (banKinhGeofenceMet + doRongBuffer)) {
				return 100.0;
			}
		}
		return 0.0;
	}

	private double tinhKhoangCachDenDoanThang(Diem p, Diem a, Diem b) {
		double dLat = b.getViDo() - a.getViDo();
		double dLon = b.getKinhDo() - a.getKinhDo();

		double len2 = dLat * dLat + dLon * dLon;

		if (len2 == 0.0)
			return p.tinhKhoangCachDen(a);

		double t = ((p.getViDo() - a.getViDo()) * dLat + (p.getKinhDo() - a.getKinhDo()) * dLon) / len2;
		t = Math.max(0.0, Math.min(1.0, t));
		double projectionLat = a.getViDo() + t * dLat;
		double projectionLon = a.getKinhDo() + t * dLon;
		Diem diemGanNhat = new Diem(projectionLon, projectionLat);

		return p.tinhKhoangCachDen(diemGanNhat);
	}

	private static Diem tinhTamTrungBinh(List<Diem> points) {
		if (points == null || points.isEmpty()) {
			return new Diem(0, 0);
		}
		double latSum = 0, lonSum = 0;
		for (Diem p : points) {
			latSum += p.getViDo();
			lonSum += p.getKinhDo();
		}
		return new Diem(lonSum / points.size(), latSum / points.size());
	}

	public List<Diem> getDanhSachDinh() {
		return danhSachDinh;
	}

	public double getDoRongBuffer() {
		return doRongBuffer;
	}

	@Override
	public String getLoaiVung() {

		return "TUYEN_DUONG_DU_LICH";
	}
}