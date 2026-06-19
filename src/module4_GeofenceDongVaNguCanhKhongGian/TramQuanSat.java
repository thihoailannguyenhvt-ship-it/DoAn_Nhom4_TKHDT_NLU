package module4_GeofenceDongVaNguCanhKhongGian;


public class TramQuanSat extends VungKhongGian {
    private double banKinhBaoVe; 

    public TramQuanSat(String idVung, String tenVung, Diem tam, double banKinhBaoVe) {
        
        super(idVung, tenVung, tam);
        this.banKinhBaoVe = banKinhBaoVe;
    }


    @Override
    public double tinhToanGiaoThoa(Diem tamGeofence, double banKinhGeofenceDo) {
      
        double d = this.toaDoTam.tinhKhoangCachDen(tamGeofence);

       
        double banKinhGeofenceMet = banKinhGeofenceDo * 111320.0;
 
      
        double Soverlap = tinhDienTichGiaoNhau(d, this.banKinhBaoVe, banKinhGeofenceMet);

      
        double S_station = Math.PI * Math.pow(this.banKinhBaoVe, 2);

        if (S_station <= 0) return 0.0;

      
        return (Soverlap / S_station) * 100.0;
    }

   
    private double tinhDienTichGiaoNhau(double d, double R1, double R2) {
    	
        System.out.println(">>> [DEBUG LOGIC] d=" + d + ", R1=" + R1 + ", R2=" + R2);
       
        if (d >= R1 + R2) {
        	System.out.println(">>> [DEBUG LOGIC] -> Kết luận: KHÔNG GIAO NHAU (Return 0.0)");
        	return 0.0;
        }
        
        if (d <= Math.abs(R1 - R2)) {
        	System.out.println(">>> [DEBUG LOGIC] -> Kết luận: NẰM TRONG NHAU");
            double rNho = Math.min(R1, R2);
            return Math.PI * rNho * rNho;
        }

       
        System.out.println(">>> [DEBUG LOGIC] -> Kết luận: GIAO NHAU MỘT PHẦN");
        double r1Sq = R1 * R1;
        double r2Sq = R2 * R2;
        
        double p1 = r1Sq * Math.acos((d * d + r1Sq - r2Sq) / (2 * d * R1));
        double p2 = r2Sq * Math.acos((d * d + r2Sq - r1Sq) / (2 * d * R2));
        double p3 = 0.5 * Math.sqrt((-d + R1 + R2) * (d + R1 - R2) * (d - R1 + R2) * (d + R1 + R2));
        
        return p1 + p2 - p3;
    }

   
    public double getBanKinhBaoVe() {
        return banKinhBaoVe;
    }

	@Override
	public String getLoaiVung() {
		
		return "TRAM_QUAN_SAT";
	}
}