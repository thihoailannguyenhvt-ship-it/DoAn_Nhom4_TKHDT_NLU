package module4_GeofenceDongVaNguCanhKhongGian;

/**
 * Lớp trừu tượng định nghĩa khung xương cho mọi vùng không gian tĩnh.
 * Đã được chuẩn hóa hình học để tách biệt logic không gian khỏi thực thể sinh học.
 */
public abstract class VungKhongGian {
    
    // Các thuộc tính chung được giữ nguyên
    protected String idVung;
    protected String tenVung;
    protected Diem toaDoTam; 

    /**
     * Constructor bắt buộc các lớp con phải tuân thủ
     */
    public VungKhongGian(String idVung, String tenVung, Diem toaDoTam) {
        this.idVung = idVung;
        this.tenVung = tenVung;
        this.toaDoTam = toaDoTam;
    }
    /**
     * BỔ SUNG: Phương thức trừu tượng bắt buộc các lớp con phải khai báo danh tính loại vùng.
     * @return Chuỗi định danh loại vùng: "KHU_DAN_CU", "TRAM_QUAN_SAT", hoặc "TUYEN_DUONG_DU_LICH"
     */
    public abstract String getLoaiVung();

   

    /**
     * PHƯƠNG THỨC TRỪU TƯỢNG ĐÃ CHUẨN HÓA HÌNH HỌC
     * Tính toán tỷ lệ hoặc mức độ giao thoa giữa vùng tĩnh này với vòng tròn Geofence động.
     * * @param tamGeofence       Vị trí tâm hiện tại của con vật (Kinh độ, Vĩ độ từ Module 1)
     * @param banKinhGeofenceDo Bán kính động của con vật ĐÃ QUY ĐỔI RA ĐƠN VỊ ĐỘ
     * @return Tỷ lệ giao thoa (0.0 nếu hoàn toàn không chạm, > 0.0 nếu có sự xâm lấn)
     */
    public abstract double tinhToanGiaoThoa(Diem tamGeofence, double banKinhGeofenceDo);

    // Các Getter chung giữ nguyên
    public String getIdVung() { return idVung; }
    public String getTenVung() { return tenVung; }
    public Diem getToaDoTam() { return toaDoTam; }
 // Trong VungKhongGian.java
    @Override
    public String toString() {
        return getLoaiVung() + " (" + getIdVung() + ")";
    }
}