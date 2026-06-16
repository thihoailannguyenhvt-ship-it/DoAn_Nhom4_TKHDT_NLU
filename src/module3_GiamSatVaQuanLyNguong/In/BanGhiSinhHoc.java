package module3_GiamSatVaQuanLyNguong.In;

import dinhDanh.*;
import dinhDanh.TinhTrangVung;

/**
 * Bản ghi sinh học – một sự kiện đầu vào tổng hợp từ Module 2 và Module 4.
 *
 * Data class thuần – không chứa logic nghiệp vụ.
 */
public class BanGhiSinhHoc {

	private final String idCaThe;
	private final NhanTrangThai nhanTrangThai; // từ Module 2
	private final TinhTrangVung tinhTrangVung; // từ Module 4
	private final double viDo;
	private final double kinhDo;
	private final String thoiGianSuKien; // ms Unix – T_event
	
	// CÁC TRƯỜNG DỮ LIỆU BỔ SUNG TỪ MODULE 4
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
	
	// GETTERS CHO DỮ LIỆU MỚI
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