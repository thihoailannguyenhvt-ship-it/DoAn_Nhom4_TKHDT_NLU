package module4_GeofenceDongVaNguCanhKhongGian;

public class Diem {
    private double kinhDo; // Longitude (Tương ứng với trục X trên bản đồ số)
    private double viDo;  // Latitude (Tương ứng với trục Y trên bản đồ số)

    // Hàm khởi tạo (Constructor) giữ nguyên cấu trúc cũ
    public Diem(double kinhDo, double viDo) {
        this.kinhDo = kinhDo;
        this.viDo = viDo;
    }

    // Các hàm Getter và Setter chuẩn
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

    /**
     * TOÁN GIS CỐT LÕI: Tính khoảng cách từ điểm này đến một điểm khác
     * Sử dụng công thức Haversine để tính khoảng cách cung tròn trên mặt cầu.
     * @param diemKhac Điểm mục tiêu cần tính khoảng cách
     * @return Khoảng cách thực tế giữa hai điểm (Đơn vị tính: MÉT)
     */
    public double tinhKhoangCachDen(Diem diemKhac) {
        // Bán kính trung bình của Trái Đất (Đơn vị: Mét)
        final double EARTH_RADIUS_METERS = 6371000; 

        // Chuyển đổi tọa độ từ đơn vị Độ (Degrees) sang Radian
        double lat1 = Math.toRadians(this.viDo);
        double lon1 = Math.toRadians(this.kinhDo);
        double lat2 = Math.toRadians(diemKhac.getViDo());
        double lon2 = Math.toRadians(diemKhac.getKinhDo());

        // Tính độ lệch giữa các trục tọa độ
        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;

        // Áp dụng công thức giải tích Haversine
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                   Math.cos(lat1) * Math.cos(lat2) *
                   Math.sin(dLon / 2) * Math.sin(dLon / 2);
                   
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Trả về kết quả khoảng cách thực tế bằng Mét cực kỳ chính xác
        return EARTH_RADIUS_METERS * c; 
    }

    /**
     * Hàm hiển thị tọa độ định dạng chuỗi (Phục vụ debug hoặc log)
     */
    @Override
    public String toString() {
        return String.format("(Kinh độ: %.6f, Vĩ độ: %.6f)", kinhDo, viDo);
    }
}
