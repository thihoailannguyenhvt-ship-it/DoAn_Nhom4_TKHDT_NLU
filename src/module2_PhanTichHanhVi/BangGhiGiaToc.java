package module2_PhanTichHanhVi;

import java.util.Arrays;

public class BangGhiGiaToc {

	private long thoiDiemSuKien;

	private double[] trucX;
	private double[] trucY;
	private double[] trucZ;

	public BangGhiGiaToc(long thoiDiemSuKien, double[] trucX, double[] trucY, double[] trucZ) {
		this.thoiDiemSuKien = thoiDiemSuKien;

		this.trucX = trucX == null ? new double[0] : trucX.clone();
		this.trucY = trucY == null ? new double[0] : trucY.clone();
		this.trucZ = trucZ == null ? new double[0] : trucZ.clone();
	}

	public BangGhiGiaToc(long thoiDiemSuKien, double x, double y, double z) {
		this(thoiDiemSuKien, new double[] { x }, new double[] { y }, new double[] { z });
	}

	public long getThoiDiemSuKien() {
		return thoiDiemSuKien;
	}

	public double[] getTrucX() {
		return trucX.clone();
	}

	public double[] getTrucY() {
		return trucY.clone();
	}

	public double[] getTrucZ() {
		return trucZ.clone();
	}

	public double getGiaTocTinhX() {
		return tinhTrungBinh(trucX);
	}

	public double getGiaTocTinhY() {
		return tinhTrungBinh(trucY);
	}

	public double getGiaTocTinhZ() {
		return tinhTrungBinh(trucZ);
	}

	public int getSoMau() {
		return trucX.length;
	}

	private double tinhTrungBinh(double[] arr) {
		if (arr == null || arr.length == 0)
			return 0;
		double tong = 0;
		for (double v : arr)
			tong += v;
		return tong / arr.length;
	}

	@Override
	public String toString() {
		return "BangGhiGiaToc[soMau=" + trucX.length + ", X=" + Arrays.toString(trucX) + ", Y=" + Arrays.toString(trucY)
				+ ", Z=" + Arrays.toString(trucZ) + "]";
	}
}
