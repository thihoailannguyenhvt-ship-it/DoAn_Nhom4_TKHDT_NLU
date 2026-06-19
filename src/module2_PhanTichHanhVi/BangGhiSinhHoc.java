package module2_PhanTichHanhVi;

public class BangGhiSinhHoc {
	private String idConVat;

	private double viDo;
	private double kinhDo;

	private long mocThoiGianSuKien;
	private long mocThoiGianHeThong;

	private BangGhiGiaToc giaToc;

	public BangGhiSinhHoc() {
		super();
		this.mocThoiGianHeThong = System.currentTimeMillis();
	}

	public BangGhiSinhHoc(String idConVat, double viDo, double kinhDo, long mocThoiGianSuKien, BangGhiGiaToc giaToc) {
		this.idConVat = idConVat;
		this.viDo = viDo;
		this.kinhDo = kinhDo;
		this.mocThoiGianSuKien = mocThoiGianSuKien;
		this.giaToc = giaToc;
		this.mocThoiGianHeThong = System.currentTimeMillis();
	}

	public String getIdConVat() {
		return idConVat;
	}

	public void setIdConVat(String idConVat) {
		this.idConVat = idConVat;
	}

	public double getViDo() {
		return viDo;
	}

	public void setViDo(double viDo) {
		this.viDo = viDo;
	}

	public double getKinhDo() {
		return kinhDo;
	}

	public void setKinhDo(double kinhDo) {
		this.kinhDo = kinhDo;
	}

	public long getMocThoiGianSuKien() {
		return mocThoiGianSuKien;
	}

	public void setMocThoiGianSuKien(long mocThoiGianSuKien) {
		this.mocThoiGianSuKien = mocThoiGianSuKien;
	}

	public long getMocThoiGianHeThong() {
		return mocThoiGianHeThong;
	}

	public void setMocThoiGianHeThong(long mocThoiGianHeThong) {
		this.mocThoiGianHeThong = mocThoiGianHeThong;
	}

	public BangGhiGiaToc getGiaToc() {
		return giaToc;
	}

	public void setGiaToc(BangGhiGiaToc giaToc) {
		this.giaToc = giaToc;
	}

	@Override
	public String toString() {
		return "BangGhiSinhHoc{" + "id='" + idConVat + '\'' + ", time=" + mocThoiGianSuKien + '}';
	}
}