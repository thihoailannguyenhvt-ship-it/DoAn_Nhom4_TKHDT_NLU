package module4_GeofenceDongVaNguCanhKhongGian;

/**
 * Lớp đại diện cho Trạm Quan Sát - một vùng tĩnh hình tròn đặc biệt.
 * Sử dụng công thức Haversine và toán học giao thoa hai đường tròn tính theo đơn vị Mét thực tế.
 */
public class TramQuanSat extends VungKhongGian {
    private double banKinhBaoVe; // Bán kính hoạt động bảo vệ của trạm (Đơn vị: Mét)

    public TramQuanSat(String idVung, String tenVung, Diem tam, double banKinhBaoVe) {
        // Gọi Constructor lớp cha để thiết lập idVung, tenVung, toaDoTam
        super(idVung, tenVung, tam);
        this.banKinhBaoVe = banKinhBaoVe;
    }

    // =========================================================================
    // HIỆN THỰC HÓA PHƯƠNG THỨC TRỪU TƯỢNG ĐÃ CHUẨN HÓA TỪ LỚP CHA
    // =========================================================================
    @Override
    public double tinhToanGiaoThoa(Diem tamGeofence, double banKinhGeofenceDo) {
        // 1. Tính khoảng cách thực tế giữa tâm Trạm và tâm Geofence động bằng công thức Haversine (Mét)
        // Kế thừa thuộc tính toaDoTam từ lớp cha VungKhongGian
        double d = this.toaDoTam.tinhKhoangCachDen(tamGeofence);

        // 2. QUY ĐỔI BẮT BUỘC: Chuyển đổi bán kính Geofence từ đơn vị ĐỘ sang MÉT
        // Sử dụng hệ số trắc địa tiêu chuẩn (1 độ vĩ độ xấp xỉ 111,320 mét) để đồng bộ thứ nguyên với d và banKinhBaoVe
        double banKinhGeofenceMet = banKinhGeofenceDo * 111320.0;
     // --- [DEBUG] ---
        System.out.println(">>> [DEBUG T01] d=" + d + " | R_Tram=" + this.banKinhBaoVe + " | R_Geofence=" + banKinhGeofenceMet);
        // 3. Tính diện tích phần giao nhau giữa 2 hình tròn (Đơn vị: Mét vuông)
        double Soverlap = tinhDienTichGiaoNhau(d, this.banKinhBaoVe, banKinhGeofenceMet);

        // 4. Tính diện tích toàn bộ bề mặt bao phủ của trạm (S = π * R^2) (Đơn vị: Mét vuông)
        double S_station = Math.PI * Math.pow(this.banKinhBaoVe, 2);

        if (S_station <= 0) return 0.0;

        // 5. Trả về tỷ lệ phần trăm diện tích vùng quản lý của trạm bị xâm lấn (0.0% - 100.0%)
        return (Soverlap / S_station) * 100.0;
    }

    /**
     * Thuật toán hình học tính chính xác diện tích giao nhau giữa 2 hình tròn trên mặt phẳng.
     * Dữ liệu đầu vào bắt buộc phải đồng bộ đơn vị Mét.
     */
    private double tinhDienTichGiaoNhau(double d, double R1, double R2) {
    	// Thêm log để theo dõi logic
        System.out.println(">>> [DEBUG LOGIC] d=" + d + ", R1=" + R1 + ", R2=" + R2);
        // Trường hợp 1: Hai hình tròn không chạm nhau (Khoảng cách tâm lớn hơn tổng hai bán kính)
        if (d >= R1 + R2) {
        	System.out.println(">>> [DEBUG LOGIC] -> Kết luận: KHÔNG GIAO NHAU (Return 0.0)");
        	return 0.0;
        }
        // Trường hợp 2: Một hình tròn nằm trọn hoàn toàn trong hình tròn còn lại
        if (d <= Math.abs(R1 - R2)) {
        	System.out.println(">>> [DEBUG LOGIC] -> Kết luận: NẰM TRONG NHAU");
            double rNho = Math.min(R1, R2);
            return Math.PI * rNho * rNho;
        }

        // Trường hợp 3: Hai hình tròn giao nhau một phần (Công thức hình học chuẩn xác)
        System.out.println(">>> [DEBUG LOGIC] -> Kết luận: GIAO NHAU MỘT PHẦN");
        double r1Sq = R1 * R1;
        double r2Sq = R2 * R2;
        
        double p1 = r1Sq * Math.acos((d * d + r1Sq - r2Sq) / (2 * d * R1));
        double p2 = r2Sq * Math.acos((d * d + r2Sq - r1Sq) / (2 * d * R2));
        double p3 = 0.5 * Math.sqrt((-d + R1 + R2) * (d + R1 - R2) * (d - R1 + R2) * (d + R1 + R2));
        
        return p1 + p2 - p3;
    }

    // Getter phục vụ cấu hình hệ thống
    public double getBanKinhBaoVe() {
        return banKinhBaoVe;
    }

	@Override
	public String getLoaiVung() {
		
		return "TRAM_QUAN_SAT";
	}
}