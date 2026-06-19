package module3_GiamSatVaQuanLyNguong.In;

import dinhDanh.TinhTrangVung;

public class BanGhiModule4 {

	private final String idConVat;
	private final TinhTrangVung nhanVung;
	private final double viDo;
	private final double kinhDo;
	private final String idVung;
	private final String loaiVung;

	private final double phanTramGiaoThoa;

	public BanGhiModule4(String idConVat, TinhTrangVung nhanVung, double viDo, double kinhDo, String idVung,
			String loaiVung, double phanTramGiaoThoa) {
		this.idConVat = idConVat;
		this.nhanVung = nhanVung;
		this.viDo = viDo;
		this.kinhDo = kinhDo;
		this.idVung = idVung;
		this.loaiVung = loaiVung;

		this.phanTramGiaoThoa = phanTramGiaoThoa;

	}

	// Getters
	public String layIdConVat() {
		return idConVat;
	}

	public TinhTrangVung layNhanVung() {
		return nhanVung;
	}

	public double layViDo() {
		return viDo;
	}

	public double layKinhDo() {
		return kinhDo;
	}

	public String layIdVung() {
		return idVung;
	}

	public String layLoaiVung() {
		return loaiVung;
	}

	public double layPhanTramGiaoThoa() {
		return phanTramGiaoThoa;
	}

	@Override
	public String toString() {
		return String.format("BanGhiModule4{id='%s', loaiVung='%s', idVung='%s', nhanVung=%s, %%GiaoThoa=%.2f}",
				idConVat, loaiVung, idVung, loaiVung, nhanVung, phanTramGiaoThoa);
	}
}