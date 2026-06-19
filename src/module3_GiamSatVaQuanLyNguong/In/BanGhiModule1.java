package module3_GiamSatVaQuanLyNguong.In;


public class BanGhiModule1 {
	private final String idConVat;
	private final String thoiGianSuKien; // Unix timestamp (ms)

	public BanGhiModule1(String idConVat, String thoiGianSuKien) {
		this.idConVat = idConVat;
		this.thoiGianSuKien = thoiGianSuKien;
	}

	public String layIdConVat() {
		return idConVat;
	}

	public String layThoiGianSuKien() {
		return thoiGianSuKien;
	}

	@Override
	public String toString() {
		return "BanGhiModule1 [idConVat=" + idConVat + ", thoiGianSuKien=" + thoiGianSuKien + "]";
	}

}
