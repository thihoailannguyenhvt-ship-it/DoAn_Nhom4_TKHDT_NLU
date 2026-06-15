package module4_GeofenceDongVaNguCanhKhongGian;

import java.util.List;

public class TramQuanSat extends VungKhongGian {
	private double banKinhBaoVe;
	private Diem tam;

	public TramQuanSat(Diem tam, double banKinhBaoVe) {

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
 
        double I = (Soverlap / St) * 100;
        return I;
    }
 
    // Công thức Shoelace: S = ½|Σ(xᵢ*yᵢ₊₁ - xᵢ₊₁*yᵢ)|
    protected double tinhDienTichDaGiac(List<Diem> danhSachDiem) {
        int n = danhSachDiem.size();
        if (n < 3) return 0;
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
 
    private double tinhDienTichGiaoNhau(double d, double Rt, double Rd) {
        if (d >= Rt + Rd) {
            return 0;
        }
        if (d <= Math.abs(Rt - Rd)) {
            double rNho = Math.min(Rt, Rd);
            return Math.PI * rNho * rNho;
        }
        double p1 = Rt * Rt * Math.acos((d * d + Rt * Rt - Rd * Rd) / (2 * d * Rt));
        double p2 = Rd * Rd * Math.acos((d * d + Rd * Rd - Rt * Rt) / (2 * d * Rd));
        double p3 = 0.5 * Math.sqrt((d + Rt + Rd) * (-d + Rt + Rd) * (d - Rt + Rd) * (d + Rd - Rt));
        return p1 + p2 - p3;
    }
}
 
