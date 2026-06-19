package module5_CanhBaoVaDieuPhoi;

public class KiemLam {
	private String maKiemLam;
    private String tenKiemLam;
    private Diem viTriHienTai; 
    private boolean dangRanh; 
    private String thietBiDauCuoi; // Chuỗi định danh ID phần cứng (Ví dụ: "DEV_01", "DEV_02")

    // Thuộc tính bổ trợ nghiệp vụ để hiển thị phân vùng quản lý trên JTable
    private String khuVucPhuTrach;

    // Constructor khởi tạo đầy đủ tham số
    public KiemLam(String maKiemLam, String tenKiemLam, Diem viTriHienTai, boolean dangRanh, String thietBiDauCuoi, String khuVucPhuTrach) {
        this.maKiemLam = maKiemLam;
        this.tenKiemLam = tenKiemLam;
        this.viTriHienTai = viTriHienTai;
        this.dangRanh = dangRanh;
        this.thietBiDauCuoi = thietBiDauCuoi;
        this.khuVucPhuTrach = khuVucPhuTrach;
    }

    // Hàm nghiệp vụ cập nhật tọa độ không gian thực địa
    public void capNhatViTri(Diem toaDoMoi) {
        this.viTriHienTai = toaDoMoi;
        System.out.println("[Model - Nghiệp vụ] Kiểm lâm " + this.tenKiemLam + " đã cập nhật tọa độ GPS mới: " + toaDoMoi.toString());
    }

    // Hàm nghiệp vụ xác nhận phản hồi từ thiết bị cầm tay về trung tâm
    public boolean xacNhanTiepNhan(String maLenh) {
        System.out.println("[Model - Nghiệp vụ] Thiết bị " + this.thietBiDauCuoi + " phản hồi: Đã nhận lệnh số " + maLenh);
        return true;
    }

    // --- Hệ thống Getter và Setter chuẩn hóa ---
    public String getMaKiemLam() { return maKiemLam; }
    public void setMaKiemLam(String maKiemLam) { this.maKiemLam = maKiemLam; }

    public String getTenKiemLam() { return tenKiemLam; }
    public void setTenKiemLam(String tenKiemLam) { this.tenKiemLam = tenKiemLam; }

    public Diem getViTriHienTai() { return viTriHienTai; }
    public void setViTriHienTai(Diem viTriHienTai) { this.viTriHienTai = viTriHienTai; }

    public boolean isDangRanh() { return dangRanh; }
    public void setDangRanh(boolean dangRanh) { this.dangRanh = dangRanh; }

    public String getThietBiDauCuoi() { return thietBiDauCuoi; }
    public void setThietBiDauCuoi(String thietBiDauCuoi) { this.thietBiDauCuoi = thietBiDauCuoi; }

    public String getKhuVucPhuTrach() { return khuVucPhuTrach; }
    public void setKhuVucPhuTrach(String khuVucPhuTrach) { this.khuVucPhuTrach = khuVucPhuTrach; }
    
    // Hàm bổ trợ chuyển đổi trạng thái boolean sang chuỗi text để hiển thị lên bảng JTable
    public String getTrangThaiText() {
        return dangRanh ? "Sẵn sàng" : "Đang nhiệm vụ";
    }
}
