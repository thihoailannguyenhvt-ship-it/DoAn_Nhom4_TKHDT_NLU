package module3_GiamSatVaQuanLyNguong;

import dinhDanh.*;


public class BanGhiSinhHoc {
	private String maSoDongVat;
	private double kinhDo;
	private double viDo;
	private NhanTrangThai trangThaiHanhVi;
	private TinhTrangVung tinhTrangVung;
	private long mocThoiGianSuKien;
	private long mocThoiGianGhiNhan;

	public BanGhiSinhHoc(String maSoDongVat, double kinhDo, double viDo, NhanTrangThai trangThaiHanhVi,
			TinhTrangVung tinhTrangVung, long mocThoiGianSuKien, long mocThoiGianGhiNhan) {
		this.maSoDongVat = maSoDongVat;
		this.kinhDo = kinhDo;
		this.viDo = viDo;
		this.trangThaiHanhVi = trangThaiHanhVi;
		this.tinhTrangVung = tinhTrangVung;
		this.mocThoiGianSuKien = mocThoiGianSuKien;
		this.mocThoiGianGhiNhan = mocThoiGianGhiNhan;
	}

	public String getMaSoDongVat() {
		return maSoDongVat;
	}

	public double getKinhDo() {
		return kinhDo;
	}

	public double getViDo() {
		return viDo;
	}

	public NhanTrangThai getTrangThaiHanhVi() {
		return trangThaiHanhVi;
	}

	public TinhTrangVung getTinhTrangVung() {
		return tinhTrangVung;
	}

	public long getMocThoiGianSuKien() {
		return mocThoiGianSuKien;
	}

	public long getMocThoiGianGhiNhan() {
		return mocThoiGianGhiNhan;
	}
}
