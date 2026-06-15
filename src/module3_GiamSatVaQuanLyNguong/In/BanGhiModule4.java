package module3_GiamSatVaQuanLyNguong.In;

import dinhDanh.TinhTrangVung;

/**
 * Bản ghi đầu ra Module 4 – Chỉ tập trung vào dữ liệu không gian và hình học
 * Geofence.
 */
public class BanGhiModule4 {

	private final String idConVat;
	private final TinhTrangVung nhanVung;
	private final double viDo;
	private final double kinhDo;
	private final double tyLeGiaoThoa;
	private final double banKinhGeofence;

	public BanGhiModule4(String idConVat, TinhTrangVung nhanVung, double viDo, double kinhDo, double tyLeGiaoThoa,
			double banKinhGeofence) {
		this.idConVat = idConVat;
		this.nhanVung = nhanVung;
		this.viDo = viDo;
		this.kinhDo = kinhDo;
		this.tyLeGiaoThoa = tyLeGiaoThoa;
		this.banKinhGeofence = banKinhGeofence;
	}

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

	public double layTyLeGiaoThoa() {
		return tyLeGiaoThoa;
	}

	public double layBanKinhGeofence() {
		return banKinhGeofence;
	}

	@Override
	public String toString() {
		return String.format("BanGhiModule4{id='%s', nhanVung=%s, viDo=%.6f, kinhDo=%.6f}", idConVat, nhanVung, viDo,
				kinhDo);
	}
}