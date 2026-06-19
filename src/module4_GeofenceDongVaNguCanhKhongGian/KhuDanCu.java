package module4_GeofenceDongVaNguCanhKhongGian;

import java.util.List;

/**
 * Lớp đại diện cho Khu Dân Cư - một vùng tĩnh dạng ĐA GIÁC phức tạp.
 * Sử dụng giải thuật Shoelace, Ray Casting và Grid Sampling để tính toán giao thoa.
 */
public class KhuDanCu extends VungKhongGian {
    private List<Diem> vertices; // Danh sách các đỉnh của đa giác khu dân cư

    public KhuDanCu(String idVung, String tenVung, Diem toaDoTam, List<Diem> vertices) {
        // Gọi Constructor của lớp cha VungKhongGian
        super(idVung, tenVung, toaDoTam);
        this.vertices = vertices;
    }

    // =========================================================================
    // HIỆN THỰC HÓA PHƯƠNG THỨC TRỪU TƯỢNG ĐÃ CHUẨN HÓA TỪ LỚP CHA
    // =========================================================================
    @Override
    public double tinhToanGiaoThoa(Diem tamGeofence, double banKinhGeofenceDo) {
    	
        double dienTichDaGiac = tinhDienTichDaGiac(vertices);
        if (dienTichDaGiac <= 0) return 0.0;
       
       
        double gridStepMeters = 5.0; 
        
        double dienTichGiaoThoa = tinhDienTichGiaoThoaBangLuoi(tamGeofence, banKinhGeofenceDo, gridStepMeters);
        System.out.println(">>> [VẠCH TRẦN TÍNH TOÁN] Vùng: " + idVung + 
                " | Tổng DT: " + dienTichDaGiac + 
                " | DT Giao thoa: " + dienTichGiaoThoa);
       
        return (dienTichGiaoThoa / dienTichDaGiac) * 100.0;
        
    }

    private double tinhDienTichGiaoThoaBangLuoi(Diem tamGeofence, double banKinhGeofenceDo, double stepMeters) {
    	
        double minX = vertices.get(0).getKinhDo(), maxX = minX;
        double minY = vertices.get(0).getViDo(), maxY = minY;
        for (Diem d : vertices) {
            if (d.getKinhDo() < minX) minX = d.getKinhDo();
            if (d.getKinhDo() > maxX) maxX = d.getKinhDo();
            if (d.getViDo() < minY) minY = d.getViDo();
            if (d.getViDo() > maxY) maxY = d.getViDo();
        }

    
        double latDegreeToMeters = 111320.0;
        double lonDegreeToMeters = 111320.0 * Math.cos(Math.toRadians(toaDoTam.getViDo()));
        
        double stepLon = stepMeters / lonDegreeToMeters;
        double stepLat = stepMeters / latDegreeToMeters;

        int countOverlap = 0;

        for (double lon = minX; lon <= maxX; lon += stepLon) {
            for (double lat = minY; lat <= maxY; lat += stepLat) {
                Diem p = new Diem(lon, lat);
                
              
                double dx = lon - tamGeofence.getKinhDo();
                double dy = lat - tamGeofence.getViDo();
                double khoangCachDo = Math.sqrt(dx * dx + dy * dy);

               
                if (isPointInPolygon(p, vertices) && khoangCachDo <= banKinhGeofenceDo) {
                    countOverlap++;
                }
            }
        }
     
        return countOverlap * (stepMeters * stepMeters);
    }

    private boolean isPointInPolygon(Diem p, List<Diem> polygon) {
        boolean inside = false;
        int n = polygon.size();
        for (int i = 0, j = n - 1; i < n; j = i++) {
            if (((polygon.get(i).getViDo() > p.getViDo()) != (polygon.get(j).getViDo() > p.getViDo())) &&
                (p.getKinhDo() < (polygon.get(j).getKinhDo() - polygon.get(i).getKinhDo()) * (p.getViDo() - polygon.get(i).getViDo()) / 
                (polygon.get(j).getViDo() - polygon.get(i).getViDo()) + polygon.get(i).getKinhDo())) {
                inside = !inside;
            }
        }
        return inside;
    }

   
    private double tinhDienTichDaGiac(List<Diem> pts) {
        double area = 0;
        for (int i = 0; i < pts.size(); i++) {
            Diem p1 = pts.get(i);
            Diem p2 = pts.get((i + 1) % pts.size());
            area += (p1.getKinhDo() * p2.getViDo()) - (p2.getKinhDo() * p1.getViDo());
        }
        
        return Math.abs(area) * 0.5 * 111320.0 * 111320.0;
    }

   
    public List<Diem> getVertices() {
        return vertices;
    }

	@Override
	public String getLoaiVung() {
		
		return "KHU_DAN_CU";
	}
}