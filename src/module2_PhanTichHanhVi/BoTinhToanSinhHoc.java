package module2_PhanTichHanhVi;

import java.util.Queue;

public class BoTinhToanSinhHoc {

	public double tinhODBA(Queue<BangGhiSinhHoc> buffer) {
		if (buffer == null || buffer.isEmpty())
			return 0;
		double tong = 0;
		for (BangGhiSinhHoc bg : buffer)
			tong += tinhODBATungBanGhi(bg);
		return tong / buffer.size();
	}

	public double tinhTongODBA(Queue<BangGhiSinhHoc> buffer) {
		if (buffer == null || buffer.isEmpty())
			return 0;
		double tong = 0;
		for (BangGhiSinhHoc bg : buffer)
			tong += tinhODBATungBanGhi(bg);
		return tong;
	}

	public double tinhODBATungBanGhi(BangGhiSinhHoc bg) {
		if (bg == null || bg.getGiaToc() == null)
			return 0;

		BangGhiGiaToc gt = bg.getGiaToc();
		double[] x = gt.getTrucX();
		double[] y = gt.getTrucY();
		double[] z = gt.getTrucZ();
		int n = x.length;
		if (n == 0)
			return 0;

		double meanX = gt.getGiaTocTinhX();
		double meanY = gt.getGiaTocTinhY();
		double meanZ = gt.getGiaTocTinhZ();

		double odba = 0;
		for (int i = 0; i < n; i++) {
			odba += Math.abs(x[i] - meanX) + Math.abs(y[i] - meanY) + Math.abs(z[i] - meanZ);
		}
		return odba;
	}

	public double tinhVarianceODBA(Queue<BangGhiSinhHoc> buffer) {
		if (buffer == null || buffer.size() < 2)
			return 0;

		double[] dsODBA = new double[buffer.size()];
		int i = 0;
		double tong = 0;
		for (BangGhiSinhHoc bg : buffer) {
			dsODBA[i] = tinhODBATungBanGhi(bg);
			tong += dsODBA[i];
			i++;
		}
		double mean = tong / dsODBA.length;

		double tongBinhPhuong = 0;
		for (double odba : dsODBA) {
			tongBinhPhuong += Math.pow(odba - mean, 2);
		}

		return tongBinhPhuong / dsODBA.length;
	}

	public double tinhTongDiChuyen(Queue<BangGhiSinhHoc> buffer) {
		if (buffer == null || buffer.size() < 2)
			return 0;

		double tongDiChuyen = 0;
		BangGhiSinhHoc truoc = null;

		for (BangGhiSinhHoc bg : buffer) {
			if (truoc != null) {

				tongDiChuyen += tinhKhoangCachHaversine(truoc.getViDo(), truoc.getKinhDo(), bg.getViDo(),
						bg.getKinhDo());
			}
			truoc = bg;
		}
		return tongDiChuyen;
	}

	private double tinhKhoangCachHaversine(double lat1, double lon1, double lat2, double lon2) {
		final double R = 6_371_000.0; // Bán kính trái đất (mét)

		double phi1 = Math.toRadians(lat1);
		double phi2 = Math.toRadians(lat2);
		double deltaPhi = Math.toRadians(lat2 - lat1);
		double deltaLambda = Math.toRadians(lon2 - lon1);

		double a = Math.sin(deltaPhi / 2) * Math.sin(deltaPhi / 2)
				+ Math.cos(phi1) * Math.cos(phi2) * Math.sin(deltaLambda / 2) * Math.sin(deltaLambda / 2);

		return R * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	}

	public double tinhTyLeNangLuong(Queue<BangGhiSinhHoc> buffer, Loai loai) {
		if (buffer == null || buffer.isEmpty() || loai == null)
			return 0;

		double normal12h = loai.tinhNormal12h(240, 300, 180);
		if (normal12h == 0)
			return 0;

		return tinhTongODBA(buffer) / normal12h;
	}

	public double tinhKhoangCach(BangGhiSinhHoc b1, BangGhiSinhHoc b2) {
		if (b1 == null || b2 == null)
			return 0;
		return tinhKhoangCachHaversine(b1.getViDo(), b1.getKinhDo(), b2.getViDo(), b2.getKinhDo());
	}
}