package module2_PhanTichHanhVi;

public class Loai {
	private String tenLoai;

	private double normal2h;

	private double gMax;

	private double heSoNghi;

	private double heSoDi;

	private double heSoBay;

	public Loai(String tenLoai, double normal2h, double gMax, double heSoNghi, double heSoDi, double heSoBay) {

		this.tenLoai = tenLoai;
		this.normal2h = normal2h;
		this.gMax = gMax;
		this.heSoNghi = heSoNghi;
		this.heSoDi = heSoDi;
		this.heSoBay = heSoBay;
	}

	public String getTenLoai() {
		return tenLoai;
	}

	public void setTenLoai(String tenLoai) {
		this.tenLoai = tenLoai;
	}
	
	

	public double getNormal2h() {
		return normal2h;
	}

	public void setNormal2h(double normal2h) {
		this.normal2h = normal2h;
	}

	public double getgMax() {
		return gMax;
	}

	public void setgMax(double gMax) {
		this.gMax = gMax;
	}

	public double tinhNormal12h(int tNghi, int tDi, int tBay) {

		return tNghi * heSoNghi + tDi * heSoDi + tBay * heSoBay;
	}

	@Override
	public String toString() {
		return "Loai:" + "\t" + tenLoai + "\t" + normal2h + "\t" + gMax;
	}

}
