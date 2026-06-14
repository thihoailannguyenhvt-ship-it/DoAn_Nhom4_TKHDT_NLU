package module2;

import java.util.LinkedList;
import java.util.Queue;

public class DongVat {
	
	    private String idConVat;
	    private double eTichLuy;

	    private Loai loai;

	    private Queue<Double> lichSuODBA =  new LinkedList<>();
	    
	    

	    public DongVat(String idConVat, Loai loai) {

	        this.idConVat = idConVat;
	        this.loai = loai;
	    }

		public DongVat(String idConVat, double eTichLuy, Loai loai) {
			super();
			this.idConVat = idConVat;
			this.eTichLuy = eTichLuy;
			this.loai = loai;
			
		}

		public String getIdConVat() {
			return idConVat;
		}

		public void setIdConVat(String idConVat) {
			this.idConVat = idConVat;
		}

		public double geteTichLuy() {
			return eTichLuy;
		}

		public void seteTichLuy(double eTichLuy) {
			this.eTichLuy = eTichLuy;
		}

		public Loai getLoai() {
			return loai;
		}

		public void setLoai(Loai loai) {
			this.loai = loai;
		}

		public Queue<Double> getLichSuODBA() {
			return lichSuODBA;
		}

		public void setLichSuODBA(Queue<Double> lichSuODBA) {
			this.lichSuODBA = lichSuODBA;
		}
		
		public String layMaLoai() {
			return loai.getTenLoai();
		}
		
		public void capNhatNangLuong(double ODBAMoi) {

		    eTichLuy += ODBAMoi;

		    lichSuODBA.add(ODBAMoi);

		    if (lichSuODBA.size() > 10) {
		        lichSuODBA.poll();
		    }
		}
	    
		@Override
		public String toString() {
			return "DongVat" +"\t"+ idConVat + "\t" + eTichLuy + "\t" + loai.getTenLoai() ;
		}
	    
}
