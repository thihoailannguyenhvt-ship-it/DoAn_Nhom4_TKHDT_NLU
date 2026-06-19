package module1_DongBoHoa;


import java.util.Arrays;



public final class BanGhiGiaTocBD {

	private final String idConVat;
	private final long mocThoiGianSuKien;

	// Sử dụng mảng nguyên thủy
	private final double[] trucX;
	private final double[] trucY;
	private final double[] trucZ;

	public BanGhiGiaTocBD(String idConVat, long mocThoiGianSuKien, double[] trucX, double[] trucY, double[] trucZ) {

		
		if (idConVat == null || idConVat.trim().isEmpty())
			throw new IllegalArgumentException("ID invalid");
		if (mocThoiGianSuKien <= 0)
			throw new IllegalArgumentException("Time invalid");
		if (trucX == null || trucY == null || trucZ == null)
			throw new IllegalArgumentException("Data cannot be null");
		if (trucX.length == 0)
			throw new IllegalArgumentException("Data empty");
		if (trucX.length != trucY.length || trucX.length != trucZ.length) {
			throw new IllegalArgumentException("Array sizes mismatch");
		}

		this.idConVat = idConVat.trim();
		this.mocThoiGianSuKien = mocThoiGianSuKien;

		
		this.trucX = trucX.clone();
		this.trucY = trucY.clone();
		this.trucZ = trucZ.clone();
	}

	public String getIdConVat() {
		return idConVat;
	}

	public long getMocThoiGianSuKien() {
		return mocThoiGianSuKien;
	}

	public int getKichThuocMau() {
		return trucX.length;
	} // Dùng .length thay cho .size()

	public double[] getTrucX() {
		return trucX;
	}

	public double[] getTrucY() {
		return trucY;
	}

	public double[] getTrucZ() {
		return trucZ;
	}

	@Override
	public String toString() {
	    return "GiaToc[" +
	           "trucX=" + Arrays.toString(trucX) +
	           ", trucY=" + Arrays.toString(trucY) +
	           ", trucZ=" + Arrays.toString(trucZ) +
	           "]";
	}
}