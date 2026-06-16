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
    	System.out.println(">>> DEBUG: ID Vùng=" + idVung + " | Bán kính Geofence (độ)=" + banKinhGeofenceDo);
        // 1. Tính tổng diện tích của khu dân cư bằng mét vuông (dùng thuật toán Shoelace)
        double dienTichDaGiac = tinhDienTichDaGiac(vertices);
        if (dienTichDaGiac <= 0) return 0.0;
       
        // 2. Grid Sampling để tính diện tích vùng giao thoa giữa đa giác và Geofence tròn
        // Chia vùng thành các ô vuông nhỏ có cạnh 5.0 mét để đảm bảo độ phân giải cao
        double gridStepMeters = 5.0; 
        //dg
        double dienTichGiaoThoa = tinhDienTichGiaoThoaBangLuoi(tamGeofence, banKinhGeofenceDo, gridStepMeters);
        System.out.println(">>> [VẠCH TRẦN TÍNH TOÁN] Vùng: " + idVung + 
                " | Tổng DT: " + dienTichDaGiac + 
                " | DT Giao thoa: " + dienTichGiaoThoa);
        // 3. Trả về tỷ lệ phần trăm xâm lấn (từ 0.0% đến 100.0%)
        return (dienTichGiaoThoa / dienTichDaGiac) * 100.0;
        
    }

    /**
     * Giải thuật Grid Sampling (Lấy mẫu lưới ô vuông) đã đồng bộ đơn vị Độ
     */
    private double tinhDienTichGiaoThoaBangLuoi(Diem tamGeofence, double banKinhGeofenceDo, double stepMeters) {
    	// --- [DEBUG 1]: Thông báo bắt đầu quét ---
        System.out.println(">>> [DEBUG] Đang bắt đầu quét lưới cho vùng: " + this.idVung);
        // Tìm Bounding Box (Hộp giới hạn biên) của đa giác để khoanh vùng không gian quét lưới trên RAM
        double minX = vertices.get(0).getKinhDo(), maxX = minX;
        double minY = vertices.get(0).getViDo(), maxY = minY;
        for (Diem d : vertices) {
            if (d.getKinhDo() < minX) minX = d.getKinhDo();
            if (d.getKinhDo() > maxX) maxX = d.getKinhDo();
            if (d.getViDo() < minY) minY = d.getViDo();
            if (d.getViDo() > maxY) maxY = d.getViDo();
        }

        // Quy đổi bước quét (Mét) sang tọa độ Độ để chạy vòng lặp địa lý
        double latDegreeToMeters = 111320.0;
        double lonDegreeToMeters = 111320.0 * Math.cos(Math.toRadians(toaDoTam.getViDo()));
        
        double stepLon = stepMeters / lonDegreeToMeters;
        double stepLat = stepMeters / latDegreeToMeters;

        int countOverlap = 0;

        // Tiến hành quét lưới hai chiều (Spatial Grid Scanning)
        for (double lon = minX; lon <= maxX; lon += stepLon) {
            for (double lat = minY; lat <= maxY; lat += stepLat) {
                Diem p = new Diem(lon, lat);
                
                // Thuật toán hình học phẳng: Tính khoảng cách Euclide bằng Độ từ điểm quét p đến tâm Geofence
                double dx = lon - tamGeofence.getKinhDo();
                double dy = lat - tamGeofence.getViDo();
                double khoangCachDo = Math.sqrt(dx * dx + dy * dy);

                // KIỂM TRA ĐỒNG THỜI: Điểm p phải nằm TRONG đa giác dân cư VÀ nằm TRONG vòng tròn Geofence độ
                if (isPointInPolygon(p, vertices) && khoangCachDo <= banKinhGeofenceDo) {
                    countOverlap++;
                }
            }
        }
     // --- [DEBUG 2]: Thông báo kết quả sau khi quét xong ---
        System.out.println(">>> [DEBUG] Vùng [" + this.idVung + "] - Số ô giao thoa đếm được: " + countOverlap);
        // Tổng diện tích giao thoa (m2) = Số ô vuông trùng khớp * Diện tích của một ô (5m * 5m = 25m2)
        return countOverlap * (stepMeters * stepMeters);
    }

    /**
     * Thuật toán Ray Casting (Bắn tia) kiểm tra một điểm bất kỳ nằm trong hay ngoài đa giác
     */
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

    /**
     * Công thức Shoelace tính toán chính xác diện tích hình học của đa giác nền Khu dân cư
     */
    private double tinhDienTichDaGiac(List<Diem> pts) {
        double area = 0;
        for (int i = 0; i < pts.size(); i++) {
            Diem p1 = pts.get(i);
            Diem p2 = pts.get((i + 1) % pts.size());
            area += (p1.getKinhDo() * p2.getViDo()) - (p2.getKinhDo() * p1.getViDo());
        }
        // Nhân với bình phương hệ số trắc địa địa cầu để chuyển đổi đơn vị diện tích từ Độ^2 sang Mét vuông (m^2)
        return Math.abs(area) * 0.5 * 111320.0 * 111320.0;
    }

    // Getter phục vụ trích xuất dữ liệu đa giác
    public List<Diem> getVertices() {
        return vertices;
    }

	@Override
	public String getLoaiVung() {
		
		return "KHU_DAN_CU";
	}
}