package module3_GiamSatVaQuanLyNguong.In;

import dinhDanh.*;

public class MenhLenhThucThi {
	private String maSoDongVat;
	private MucDoNghiemTrong mucDoNghiemTrong;
	private double kinhDo;
	private double viDo;
	private String mocThoiGianSuKien;
	private String mocThoiGianGhiNhan;
	private final String lyDo;
	private final String idVung;
    private final String loaiVung;
    private final double phanTramGiaoThoa;

	public MenhLenhThucThi(String maSoDongVat, MucDoNghiemTrong mucDoNghiemTrong, double kinhDo, double viDo,
			String mocThoiGianSuKien, String mocThoiGianGhiNhan, String lyDo, String idVung, String loaiVung, double phanTramGiaoThoa) {
		this.maSoDongVat = maSoDongVat;
		this.mucDoNghiemTrong = mucDoNghiemTrong;
		this.kinhDo = kinhDo;
		this.viDo = viDo;
		this.mocThoiGianSuKien = mocThoiGianSuKien;
		this.mocThoiGianGhiNhan = mocThoiGianGhiNhan;
		this.lyDo = lyDo;
		this.idVung = idVung;
        this.loaiVung = loaiVung;
        this.phanTramGiaoThoa = phanTramGiaoThoa;
	}

	public String getIdVung() {
		return idVung;
	}

	public String getLoaiVung() {
		return loaiVung;
	}

	public double getPhanTramGiaoThoa() {
		return phanTramGiaoThoa;
	}

	public String getLyDo() {
		return lyDo;
	}

	public String getMaSoDongVat() {
		return maSoDongVat;
	}

	public MucDoNghiemTrong getMucDoNghiemTrong() {
		return mucDoNghiemTrong;
	}

	public double getKinhDo() {
		return kinhDo;
	}

	public double getViDo() {
		return viDo;
	}

	public String getMocThoiGianSuKien() {
		return mocThoiGianSuKien;
	}

	public String getMocThoiGianGhiNhan() {
		return mocThoiGianGhiNhan;
	}
	@Override
	public String toString() {
	    return String.format(
	        "MenhLenhThucThi[id=%s, mucDo=%s, viDo=%.6f, kinhDo=%.6f, lyDo=%s]",
	        maSoDongVat,
	        mucDoNghiemTrong,
	        viDo,
	        kinhDo,
	        lyDo
	    );
	}
}
