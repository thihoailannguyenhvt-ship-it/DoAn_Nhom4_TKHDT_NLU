package module4_GeofenceDongVaNguCanhKhongGian;

public class GeofenceDong {
	private String idConVat;
	private Diem tamHienTai; // Vị trí GPS (Kinh độ, Vĩ độ) cập nhật mới nhất
	private double banKinhBaseMet; // R_base: Bán kính cơ sở cố định (Đơn vị: MÉT)
	private double currentK; // Hệ số k ngữ cảnh hiện tại từ Module 2
	private double odbaHienTai; // Chỉ số ODBA tính toán thực tế (Đơn vị: g)

	// ==========================================
	// CÁC HẰNG SỐ CƠ SỞ KHOA HỌC (ĐỂ ĐẢM BẢO TÍNH CHÍNH XÁC)
	// ==========================================
	private static final double METERS_PER_DEGREE = 111320.0; // Định lý trắc địa: 1 độ vĩ độ = 111,320 mét
	private static final double ALPHA_LOCOMOTION = 11.0 / 1.5; // Hệ số sinh học: Vận tốc bứt tốc (11m/s) / ODBA đỉnh
																// (1.5g) ~7.333
	private static final double R_MAX_METERS = 0.02 * METERS_PER_DEGREE; // Giới hạn chặn trên nghiêm ngặt (2226.4 mét)

	/**
	 * Hàm khởi tạo (Constructor)
	 */
	public GeofenceDong(String idConVat, double banKinhBaseMet, double kNormal) {
		this.idConVat = idConVat;
		this.banKinhBaseMet = banKinhBaseMet;
		this.currentK = kNormal;
		this.odbaHienTai = 0.0;
	}

	public void capNhatTam(Diem viTriMoi) {
		this.tamHienTai = viTriMoi;
	}

	public void capNhatHeSoK(double kMoi) {
		this.currentK = kMoi;
	}

	public void setOdbaHienTai(double odbaHienTai) {
		this.odbaHienTai = odbaHienTai;
	}

	/**
	 * BẢN CHẤT TOÁN HỌC 1: Tính bán kính động chuẩn vật lý theo đơn vị MÉT
	 * 
	 * @param thoiGianTreGiay Độ trễ tính toán trích xuất từ trường doTre của bản
	 *                        ghi (giây)
	 */
	public double getBanKinhRInMeters(double thoiGianTreGiay) {
		// 1. Chuyển đổi ODBA sang vận tốc tức thời (m/s) dựa trên cơ học động vật
		double vanTocTucThoi = ALPHA_LOCOMOTION * this.odbaHienTai;

		// 2. Tính khoảng cách con vật có khả năng di chuyển động học trong thời gian
		// trễ: d = v * t
		double khoangCachDuPhong = vanTocTucThoi * thoiGianTreGiay;

		// 3. Cộng đồng nhất thứ nguyên (Mét + Mét): R_dynamic = R_base + K * d_buffer
		double rMeters = this.banKinhBaseMet + (this.currentK * khoangCachDuPhong);

		// 4. Kiểm soát biên giới hạn (Clamping) an toàn dữ liệu
		return Math.max(this.banKinhBaseMet, Math.min(rMeters, R_MAX_METERS));
	}

	/**
	 * BẢN CHẤT TOÁN HỌC 2: Quy đổi bán kính ra ĐỘ (Decimal Degrees) phục vụ thuật
	 * toán GIS Hàm này sẽ được Module 4 gọi khi chạy vòng lặp kiểm tra giao thoa
	 * không gian.
	 */
	public double getBanKinhRInDegrees(double thoiGianTreGiay) {
		// Lấy kết quả tính toán bằng Mét chia cho hệ số trắc địa địa cầu
		return this.getBanKinhRInMeters(thoiGianTreGiay) / METERS_PER_DEGREE;
	}

	// Các hàm Getter phục vụ biểu diễn dữ liệu
	public String getIdConVat() {
		return idConVat;
	}

	public Diem getTamHienTai() {
		return tamHienTai;
	}

	public double getOdbaHienTai() {
		return odbaHienTai;
	}

	public double getCurrentK() {
		return currentK;
	}
}