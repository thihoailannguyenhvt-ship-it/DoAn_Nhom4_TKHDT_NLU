package module3_GiamSatVaQuanLyNguong.In;

import dinhDanh.*;

public class MenhLenhThucThi {
	private String maSoDongVat;
	private MucDoNghiemTrong mucDoNghiemTrong;
	private double kinhDo;
	private double viDo;
	private long mocThoiGianSuKien;
	private long mocThoiGianGhiNhan;

	public MenhLenhThucThi(String maSoDongVat, MucDoNghiemTrong mucDoNghiemTrong, double kinhDo, double viDo,
			long mocThoiGianSuKien, long mocThoiGianGhiNhan) {
		this.maSoDongVat = maSoDongVat;
		this.mucDoNghiemTrong = mucDoNghiemTrong;
		this.kinhDo = kinhDo;
		this.viDo = viDo;
		this.mocThoiGianSuKien = mocThoiGianSuKien;
		this.mocThoiGianGhiNhan = mocThoiGianGhiNhan;
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

	public long getMocThoiGianSuKien() {
		return mocThoiGianSuKien;
	}

	public long getMocThoiGianGhiNhan() {
		return mocThoiGianGhiNhan;
	}
}
