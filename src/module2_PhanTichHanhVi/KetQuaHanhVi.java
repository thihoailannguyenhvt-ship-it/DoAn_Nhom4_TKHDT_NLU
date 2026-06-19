package module2_PhanTichHanhVi;

import dinhDanh.NhanTrangThai;

public class KetQuaHanhVi {

	private NhanTrangThai nhanTrangThai;
	private long t1; 
	private long t2; 
	private String idConVat;
	private double chiSoODBA;
	private double tyLePhanTramNangLuong;

	
	public KetQuaHanhVi(NhanTrangThai nhanTrangThai) {
		this.nhanTrangThai = nhanTrangThai;
		this.idConVat = "";
		this.chiSoODBA = 0.0;
		this.tyLePhanTramNangLuong = 0.0;
		this.t1 = 0;
		this.t2 = System.currentTimeMillis();
	}

	public KetQuaHanhVi(NhanTrangThai nhanTrangThai, long t1, long t2, String idConVat, double chiSoODBA,
			double tyLePhanTramNangLuong) {
		this.nhanTrangThai = nhanTrangThai;
		this.t1 = t1;
		this.t2 = t2;
		this.idConVat = idConVat;
		this.chiSoODBA = chiSoODBA;
		this.tyLePhanTramNangLuong = tyLePhanTramNangLuong;
	}

	
	public long getDoTreModule2() {
		return (t1 == 0) ? 0 : (t2 - t1);
	}

	
	public static String getCSVHeader() {
		return "ID_ConVat,TrangThai,ODBA,TyLeNangLuong,Time_Event,Time_Analyze,DoTre_ms";
	}

	public String toCSV() {
		StringBuilder sb = new StringBuilder();
		sb.append(idConVat).append(",").append(nhanTrangThai).append(",").append(String.format("%.4f", chiSoODBA))
				.append(",").append(String.format("%.2f", tyLePhanTramNangLuong)).append(",").append(t1).append(",")
				.append(t2).append(",").append(getDoTreModule2());
		return sb.toString();
	}

	public NhanTrangThai getNhanTrangThai() {
		return nhanTrangThai;
	}

	public void setNhanTrangThai(NhanTrangThai nhanTrangThai) {
		this.nhanTrangThai = nhanTrangThai;
	}

	public long getT1() {
		return t1;
	}

	public void setT1(long t1) {
		this.t1 = t1;
	}

	public long getT2() {
		return t2;
	}

	public void setT2(long t2) {
		this.t2 = t2;
	}

	public String getIdConVat() {
		return idConVat;
	}

	public void setIdConVat(String idConVat) {
		this.idConVat = idConVat;
	}

	public double getChiSoODBA() {
		return chiSoODBA;
	}

	public void setChiSoODBA(double chiSoODBA) {
		this.chiSoODBA = chiSoODBA;
	}

	public double getTyLePhanTramNangLuong() {
		return tyLePhanTramNangLuong;
	}

	public void setTyLePhanTramNangLuong(double tyLePhanTramNangLuong) {
		this.tyLePhanTramNangLuong = tyLePhanTramNangLuong;
	}

	@Override
	public String toString() {
		return String.format("KetQuaHanhVi[ID=%s, Trạng thái=%s, ODBA=%.4f, Năng lượng=%.2f%%, Trễ=%d ms]", idConVat,
				nhanTrangThai, chiSoODBA, tyLePhanTramNangLuong, getDoTreModule2());
	}
}