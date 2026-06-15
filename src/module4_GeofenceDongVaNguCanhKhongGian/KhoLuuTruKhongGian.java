package module4_GeofenceDongVaNguCanhKhongGian;

import java.util.ArrayList;
import java.util.List;

public class KhoLuuTruKhongGian {
	 private ArrayList<VungKhongGian> danhSachVung;
	 private ArrayList<GeofenceDong> danhSachGeofence;

	    public KhoLuuTruKhongGian(ArrayList<VungKhongGian> danhSachVung, ArrayList<GeofenceDong> danhSachGeofence) {
		super();
		this.danhSachVung = danhSachVung;
		this.danhSachGeofence = danhSachGeofence;
	}

		public KhoLuuTruKhongGian() {
	        danhSachVung = new ArrayList<>();
	        danhSachGeofence = new ArrayList<>();
	    }

	    public ArrayList<VungKhongGian> layDanhSachVung() {
	        return danhSachVung;
	    }

	    public void themVung(VungKhongGian vung) {
	        danhSachVung.add(vung);
	    }

	    public void xoaVung(VungKhongGian vung) {
	        danhSachVung.remove(vung);
	    }
	}
