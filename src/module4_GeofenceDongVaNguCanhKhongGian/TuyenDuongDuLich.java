package module4_GeofenceDongVaNguCanhKhongGian;

import java.util.ArrayList;
import java.util.List;

public class TuyenDuongDuLich extends VungKhongGian {
	private Diem tam;
	private double banKinhBaoVe;

	public TuyenDuongDuLich(Diem tam, double banKinhBaoVe) {
		super();
		this.tam = tam;
		this.banKinhBaoVe = banKinhBaoVe;
	}

	@Override
	public double tinhToanGiaoThoa(GeofenceDong geofence) {
		Diem tamDong = geofence.getTamHienTai();
		double Rdd = geofence.getBanKinhR();

		double d = tinhKhoangCach(tam, tamDong);
		double Soverlap = tinhDienTichGiaoNhau(d, banKinhBaoVe, Rdd);

		// Diện tích đa giác theo công thức Shoelace thay cho π*Rdd²
		double St = tinhDienTichDaGiac(geofence.getDsDinh());
		if (St <= 0)
			return 0;

		double H = (Soverlap / St) * 100;
		return H;
	}

	// Công thức Shoelace: S = ½|Σ(xᵢ*yᵢ₊₁ - xᵢ₊₁*yᵢ)|
	protected double tinhDienTichDaGiac(List<Diem> danhSachDiem) {
		int n = danhSachDiem.size();
		if (n < 3)
			return 0;
		double tong = 0;
		for (int i = 0; i < n; i++) {
			Diem p1 = danhSachDiem.get(i);
			Diem p2 = danhSachDiem.get((i + 1) % n);
			tong += p1.getKinhDo() * p2.getViDo();
			tong -= p2.getKinhDo() * p1.getViDo();
		}
		return Math.abs(tong) / 2.0;
	}

	private double tinhKhoangCach(Diem a, Diem b) {
		double dx = a.getKinhDo() - b.getKinhDo();
		double dy = a.getViDo() - b.getViDo();
		return Math.sqrt(dx * dx + dy * dy);
	}

	private double tinhDienTichGiaoNhau(double d, double Rt, double Rdd) {
		if (d >= Rt + Rdd) {
			return 0;
		}
		if (d <= Math.abs(Rt - Rdd)) {
			double rNho = Math.min(Rt, Rdd);
			return Math.PI * rNho * rNho;
		}
		double p1 = Rt * Rt * Math.acos((d * d + Rt * Rt - Rdd * Rdd) / (2 * d * Rt));
		double p2 = Rdd * Rdd * Math.acos((d * d + Rdd * Rdd - Rt * Rt) / (2 * d * Rdd));
		double p3 = 0.5 * Math.sqrt((d + Rt + Rdd) * (-d + Rt + Rdd) * (d - Rt + Rdd) * (d + Rdd - Rt));
		return p1 + p2 - p3;
	}
}
