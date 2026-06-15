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

	public MenhLenhThucThi(String maSoDongVat, MucDoNghiemTrong mucDoNghiemTrong, double kinhDo, double viDo,
			String mocThoiGianSuKien, String mocThoiGianGhiNhan, String lyDo) {
		this.maSoDongVat = maSoDongVat;
		this.mucDoNghiemTrong = mucDoNghiemTrong;
		this.kinhDo = kinhDo;
		this.viDo = viDo;
		this.mocThoiGianSuKien = mocThoiGianSuKien;
		this.mocThoiGianGhiNhan = mocThoiGianGhiNhan;
		this.lyDo = lyDo;
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
