package module4_GeofenceDongVaNguCanhKhongGian;

public class BoTinhToanBanKinh {

    // 1. CHUẨN HÓA HỆ TỌA ĐỘ VÀ KÍCH THƯỚC (Đơn vị: Mét)
    private static final double METERS_PER_DEGREE = 111320.0; // Quy đổi toán học từ Độ sang Mét
    private static final double R_BASE_METERS = 0.002 * METERS_PER_DEGREE; // 222.64 mét
    private static final double R_MAX_METERS = 0.02 * METERS_PER_DEGREE;   // 2226.4 mét

    // 2. HẰNG SỐ SINH HỌC THỰC NGHIỆM (Biomechanical Constant)
    // Vận tốc tối đa của lợn rừng (11m/s) chia cho ODBA đỉnh điểm (1.5g)
    private static final double ALPHA_LOCOMOTION = 11.0 / 1.5; // ~7.333 (m/s)/g

    public BoTinhToanBanKinh() {
    }

    /**
     * Tính toán bán kính geofence động dựa trên cơ sở vật lý và sinh học động lực học.
     * * @param k                 Hệ số rủi ro ngữ cảnh (không đơn vị)
     * @param odba              Mức năng lượng vận động từ cảm biến (đơn vị: g)
     * @param deltaThoiGianGiay Chu kỳ gửi tin hoặc độ trễ hệ thống (đơn vị: giây)
     * @return Bán kính vòng tròn geofence động (đơn vị: ĐỘ - Decimal Degrees)
     */
    public double tinhBanKinhChuanKhoaHoc(double k, double odba, double deltaThoiGianGiay) {
        
        // Bước 1: Tính vận tốc tức thời dựa trên mô hình sinh học năng lượng
        double vanTocTucThoi = ALPHA_LOCOMOTION * odba; // Đơn vị: m/s

        // Bước 2: Tính khoảng cách dịch chuyển tiềm năng theo động học (Kinematics)
        double khoangCachDuPhong = vanTocTucThoi * deltaThoiGianGiay; // Đơn vị: mét

        // Bước 3: Áp dụng phương trình cấu trúc không gian vùng đệm có tính đến hệ số K
        double rMeters = R_BASE_METERS + (k * khoangCachDuPhong);

        // Bước 4: Kiểm soát biên giới hạn (Clamping) theo mét
        if (rMeters < R_BASE_METERS) {
            rMeters = R_BASE_METERS;
        }
        if (rMeters > R_MAX_METERS) {
            rMeters = R_MAX_METERS;
        }

        // Bước 5: Chuyển đổi ngược lại hệ đơn vị Độ (Decimal Degrees) để trả về cho mô hình GIS
        return rMeters / METERS_PER_DEGREE;
    }

    public double getRBaseInDegrees() {
        return R_BASE_METERS / METERS_PER_DEGREE;
    }
}
