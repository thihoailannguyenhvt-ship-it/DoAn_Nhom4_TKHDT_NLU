package module4_GeofenceDongVaNguCanhKhongGian;

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
    public double getViDo() {
		return viDo; 
    	
    }
    public double tinhKhoangCach(Diem diemKhac, Diem toaDo) {
		double dx = this.kinhDo - diemKhac.getKinhDo();
		double dy = this.viDo - diemKhac.getViDo();
		return Math.sqrt(dx * dx + dy * dy);
    }
}
