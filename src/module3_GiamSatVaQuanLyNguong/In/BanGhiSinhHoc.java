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

	public BanGhiSinhHoc(String idCaThe, NhanTrangThai nhanTrangThai, TinhTrangVung tinhTrangVung, double viDo,
			double kinhDo, String thoiGianSuKien) {
		this.idCaThe = idCaThe;
		this.nhanTrangThai = nhanTrangThai;
		this.tinhTrangVung = tinhTrangVung;
		this.viDo = viDo;
		this.kinhDo = kinhDo;
		this.thoiGianSuKien = thoiGianSuKien;
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

	@Override
	public String toString() {
		return String.format("BanGhi{id='%s', nhan=%s, vung=%s, t=%d}", idCaThe, nhanTrangThai, tinhTrangVung,
				thoiGianSuKien);
	}
}
