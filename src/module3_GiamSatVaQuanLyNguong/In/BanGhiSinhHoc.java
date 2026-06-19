package module3_GiamSatVaQuanLyNguong.In;

import dinhDanh.*;
import dinhDanh.TinhTrangVung;

public class BanGhiSinhHoc {

	private final String idCaThe;
	private final NhanTrangThai nhanTrangThai; 
	private final TinhTrangVung tinhTrangVung; 
	private final double viDo;
	private final double kinhDo;
	private final String thoiGianSuKien; // ms Unix – T_event
	
	
	private final String idVung;
	private final String loaiVung;
	private final double phanTramGiaoThoa;

	public BanGhiSinhHoc(String idCaThe, NhanTrangThai nhanTrangThai, TinhTrangVung tinhTrangVung, double viDo,
			double kinhDo, String thoiGianSuKien, String idVung, String loaiVung, double phanTramGiaoThoa) {
		this.idCaThe = idCaThe;
		this.nhanTrangThai = nhanTrangThai;
		this.tinhTrangVung = tinhTrangVung;
		this.viDo = viDo;
		this.kinhDo = kinhDo;
		this.thoiGianSuKien = thoiGianSuKien;
		this.idVung = idVung;
		this.loaiVung = loaiVung;
		this.phanTramGiaoThoa = phanTramGiaoThoa;
	}

	public String layIdCaThe() {
		return idCaThe;
	}

	public NhanTrangThai layNhanTrangThai() {
		return nhanTrangThai;
	}

	public TinhTrangVung layTinhTrangVung() {
		return tinhTrangVung;
	}

	public double layViDo() {
		return viDo;
	}

	public double layKinhDo() {
		return kinhDo;
	}

	public String layThoiGianSuKien() {
		return thoiGianSuKien;
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
		return String.format("BanGhi{id='%s', nhan=%s, vung=%s, idVung='%s', loaiVung='%s', %%GiaoThoa=%.2f, t=%s}", 
				idCaThe, nhanTrangThai, tinhTrangVung, idVung, loaiVung, phanTramGiaoThoa, thoiGianSuKien);
	}
}