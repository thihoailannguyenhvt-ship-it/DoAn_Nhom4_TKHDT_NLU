package module3_GiamSatVaQuanLyNguong.In;

import dinhDanh.NhanTrangThai;


public class BanGhiModule2 {

	private final String idConVat;
	private final NhanTrangThai trangThai;

	public BanGhiModule2(String idConVat, NhanTrangThai trangThai) {
		this.idConVat = idConVat;
		this.trangThai = trangThai;
	}

	public String layIdConVat() {
		return idConVat;
	}

	public NhanTrangThai layTrangThai() {
		return trangThai;
	}

	@Override
	public String toString() {
		return String.format("BanGhiM2{id='%s', trangThai=%s}", idConVat, trangThai);
	}
}
