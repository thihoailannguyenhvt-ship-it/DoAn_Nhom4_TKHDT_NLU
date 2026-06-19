package module5_CanhBaoVaDieuPhoi;

public class LenhDieuPhoi {
	private String maLenh;
    private String huongDanHanhDong;
    
    // Thuộc tính theo đúng cấu trúc lớp của bạn để liên kết chi tiết thông tin
    private String thongTinChiTiet; 

    // Các thuộc tính bổ trợ nghiệp vụ phục vụ việc lưu trữ thông tin và hiển thị JTable
    private String maCanhBaoLienKet;
    private String tenKiemLamNhan;
    private String thoiGianPhatHanh;
    private String trangThai; // Ví dụ: "Đang di chuyển", "Đang xử lý", "Hoàn tất"

    // Constructor khởi tạo đầy đủ tham số
    public LenhDieuPhoi(String maLenh, String maCanhBaoLienKet, String tenKiemLamNhan, 
                             String thoiGianPhatHanh, String trangThai, String huongDanHanhDong, String thongTinChiTiet) {
        this.maLenh = maLenh;
        this.maCanhBaoLienKet = maCanhBaoLienKet;
        this.tenKiemLamNhan = tenKiemLamNhan;
        this.thoiGianPhatHanh = thoiGianPhatHanh;
        this.trangThai = trangThai;
        this.huongDanHanhDong = huongDanHanhDong;
        this.thongTinChiTiet = thongTinChiTiet;
    }

    // Hàm nghiệp vụ mô phỏng gửi tín hiệu số lệnh đến phần cứng thiết bị của kiểm lâm viên
    public void guiDenThietBi(KiemLam kiemLam) {
        System.out.println("[Model - Nghiệp vụ] Lệnh " + this.maLenh + " đã được mã hóa vô tuyến và phát tới ID thiết bị: " + kiemLam.getThietBiDauCuoi());
    }

    // --- Hệ thống Getter và Setter chuẩn hóa ---
    public String getMaLenh() { return maLenh; }
    public void setMaLenh(String maLenh) { this.maLenh = maLenh; }

    public String getHuongDanHanhDong() { return huongDanHanhDong; }
    public void setHuongDanHanhDong(String huongDanHanhDong) { this.huongDanHanhDong = huongDanHanhDong; }

    public String getThongTinChiTiet() { return thongTinChiTiet; }
    public void setThongTinChiTiet(String thongTinChiTiet) { this.thongTinChiTiet = thongTinChiTiet; }

    public String getMaCanhBaoLienKet() { return maCanhBaoLienKet; }
    public void setMaCanhBaoLienKet(String maCanhBaoLienKet) { this.maCanhBaoLienKet = maCanhBaoLienKet; }

    public String getTenKiemLamNhan() { return tenKiemLamNhan; }
    public void setTenKiemLamNhan(String tenKiemLamNhan) { this.tenKiemLamNhan = tenKiemLamNhan; }

    public String getThoiGianPhatHanh() { return thoiGianPhatHanh; }
    public void setThoiGianPhatHanh(String thoiGianPhatHanh) { this.thoiGianPhatHanh = thoiGianPhatHanh; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
}
