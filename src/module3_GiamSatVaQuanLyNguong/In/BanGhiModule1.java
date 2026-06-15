package module3_GiamSatVaQuanLyNguong.In;

/**
 * Bản ghi gốc từ Module 1 – Nguồn dữ liệu thời gian chuẩn của hệ thống.
 */
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
