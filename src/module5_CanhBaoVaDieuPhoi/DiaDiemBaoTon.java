package module5_CanhBaoVaDieuPhoi;

import java.awt.Color;

public class DiaDiemBaoTon {

	private String maDiaDiem;
	private String tenKhuVuc;
	private String mucDoRuiRo;
	private Color mauRuiRo;
	private int soLuongCamera;
	private int soKiemLamPhuTrach;
	private String ghiChu;

	public DiaDiemBaoTon(String maDiaDiem, String tenKhuVuc, String mucDoRuiRo, Color mauRuiRo, int soLuongCamera,
			int soKiemLamPhuTrach, String ghiChu) {
		this.maDiaDiem = maDiaDiem;
		this.tenKhuVuc = tenKhuVuc;
		this.mucDoRuiRo = mucDoRuiRo;
		this.mauRuiRo = mauRuiRo;
		this.soLuongCamera = soLuongCamera;
		this.soKiemLamPhuTrach = soKiemLamPhuTrach;
		this.ghiChu = ghiChu;
	}

	public String getMaDiaDiem() {
		return maDiaDiem;
	}

	public String getTenKhuVuc() {
		return tenKhuVuc;
	}

	public String getMucDoRuiRo() {
		return mucDoRuiRo;
	}

	public Color getMauRuiRo() {
		return mauRuiRo;
	}

	public int getSoLuongCamera() {
		return soLuongCamera;
	}

	public int getSoKiemLamPhuTrach() {
		return soKiemLamPhuTrach;
	}

	public String getGhiChu() {
		return ghiChu;
	}
}

