package module5_CanhBaoVaDieuPhoi;

public class Diem {
	private double kinhDo;
    private double viDo;
	public Diem(double kinhDo, double viDo) {
		super();
		this.kinhDo = kinhDo;
		this.viDo = viDo;
	}
	public double getKinhDo() {
		return kinhDo;
	}
	public void setKinhDo(double kinhDo) {
		this.kinhDo = kinhDo;
	}
	public double getViDo() {
		return viDo;
	}
	public void setViDo(double viDo) {
		this.viDo = viDo;
	}
	@Override
	public String toString() {
		return "Diem [kinhDo=" + kinhDo + ", viDo=" + viDo + "]";
	}
}
