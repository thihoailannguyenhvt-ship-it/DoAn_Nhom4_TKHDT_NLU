package module4_GeofenceDongVaNguCanhKhongGian;

import java.util.List;

/**
 * Lớp đại diện cho Tuyến Đường Du Lịch - một vùng tĩnh dạng ĐƯỜNG GẤP KHÚC (Polyline) kết hợp VÙNG ĐỆM (Buffer).
 * Đã được chuẩn hóa chữ ký phương thức để đảm bảo tính đa hình trọn vẹn với lớp cha VungKhongGian.
 */
public class TuyenDuongDuLich extends VungKhongGian {
    private List<Diem> danhSachDinh; // Các điểm tạo nên tuyến đường
    private double doRongBuffer;     // Độ rộng hành lang an toàn/vùng đệm của tuyến đường (Đơn vị: Mét)

    public TuyenDuongDuLich(String idVung, String tenVung, List<Diem> danhSachDinh, double doRongBuffer) {
        // Gọi Constructor của lớp cha và tính tâm trung bình hình học làm điểm gốc tham chiếu
        super(idVung, tenVung, tinhTamTrungBinh(danhSachDinh));
        this.danhSachDinh = danhSachDinh;
        this.doRongBuffer = doRongBuffer;
    }

    // =========================================================================
    // HIỆN THỰC HÓA PHƯƠNG THỨC TRỪU TƯỢNG ĐÃ CHUẨN HÓA TỪ LỚP CHA
    // =========================================================================
    @Override
    public double tinhToanGiaoThoa(Diem tamGeofence, double banKinhGeofenceDo) {
        // 1. QUY ĐỔI BẮT BUỘC: Chuyển đổi bán kính Geofence của động vật từ đơn vị ĐỘ sang MÉT
        // Sử dụng hệ số trắc địa tiêu chuẩn để đồng bộ thứ nguyên với doRongBuffer và khoảng cách thực tế Haversine
        double banKinhGeofenceMet = banKinhGeofenceDo * 111320.0;

        // 2. Duyệt qua từng đoạn thẳng (Segment) được tạo bởi các đỉnh liên tiếp trên tuyến đường
        for (int i = 0; i < danhSachDinh.size() - 1; i++) {
            Diem p1 = danhSachDinh.get(i);
            Diem p2 = danhSachDinh.get(i + 1);

            // Tìm khoảng cách ngắn nhất từ tâm con vật tới đoạn thẳng p1-p2 (Đơn vị: Mét - sử dụng Haversine)
            double khoangCachDenDoan = tinhKhoangCachDenDoanThang(tamGeofence, p1, p2);

            // 3. Kiểm tra va chạm: Nếu khoảng cách ngắn nhất nhỏ hơn hoặc bằng tổng bán kính động và vùng đệm tuyến đường
            // thì kết luận con vật đang xâm nhiễm vào hành lang an toàn của tuyến du lịch
            if (khoangCachDenDoan <= (banKinhGeofenceMet + doRongBuffer)) {
                return 100.0; // Trả về 100% rủi ro giao thoa (Đang nằm trong vùng ảnh hưởng nguy hiểm)
            }
        }
        return 0.0; // Hoàn toàn nằm ngoài vùng đệm ảnh hưởng của tuyến đường du lịch này
    }

    /**
     * Thuật toán chiếu vector trên lưới phẳng để tìm hệ số tỷ lệ t,
     * kết hợp công thức Haversine để tính khoảng cách thực tế (Mét) từ điểm P đến đoạn thẳng AB.
     */
    private double tinhKhoangCachDenDoanThang(Diem p, Diem a, Diem b) {
        double dLat = b.getViDo() - a.getViDo();
        double dLon = b.getKinhDo() - a.getKinhDo();
        
        // Độ dài đoạn thẳng bình phương (Xét trên hệ lưới phẳng tọa độ Độ)
        double len2 = dLat * dLat + dLon * dLon;
        
        // Trường hợp đặc biệt: Tuyến đường lỗi hoặc đoạn thẳng co cụm thành 1 điểm (A trùng B)
        if (len2 == 0.0) return p.tinhKhoangCachDen(a);

        // Chiếu điểm P lên đường thẳng chứa AB, t là hệ số định vị vị trí (0.0 <= t <= 1.0)
        double t = ((p.getViDo() - a.getViDo()) * dLat + (p.getKinhDo() - a.getKinhDo()) * dLon) / len2;
        t = Math.max(0.0, Math.min(1.0, t)); // Ràng buộc chặt chẽ t chỉ nằm trên đoạn AB, không vượt ra ngoài tia

        // Xác định tọa độ hình học (Độ) của điểm chiếu gần nhất trên đoạn thẳng
        double projectionLat = a.getViDo() + t * dLat;
        double projectionLon = a.getKinhDo() + t * dLon;
        Diem diemGanNhat = new Diem(projectionLon, projectionLat);

        // Tính khoảng cách thực tế bằng mặt cầu Haversine (Mét) từ P tới điểm gần nhất vừa xác định
        return p.tinhKhoangCachDen(diemGanNhat);
    }

    /**
     * Hàm phụ trợ tĩnh để tính tọa độ trọng tâm hình học trung bình của tuyến đường
     */
    private static Diem tinhTamTrungBinh(List<Diem> points) {
        if (points == null || points.isEmpty()) {
            return new Diem(0, 0);
        }
        double latSum = 0, lonSum = 0;
        for (Diem p : points) {
            latSum += p.getViDo();
            lonSum += p.getKinhDo();
        }
        return new Diem(lonSum / points.size(), latSum / points.size());
    }

    // Các phương thức Getter phục vụ việc truy xuất cấu hình hệ thống
    public List<Diem> getDanhSachDinh() { return danhSachDinh; }
    public double getDoRongBuffer() { return doRongBuffer; }

	@Override
	public String getLoaiVung() {
		
		return "TUYEN_DUONG_DU_LICH";
	}
}