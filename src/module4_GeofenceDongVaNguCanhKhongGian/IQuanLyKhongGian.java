package module4_GeofenceDongVaNguCanhKhongGian;

import java.util.List;

/**
 * Interface quản lý các vùng tĩnh trong hệ thống.
 * Đã được chuẩn hóa để hỗ trợ luồng tính toán động học không gian thời gian thực.
 */
public interface IQuanLyKhongGian {
    
    // --- Các hàm quản lý dữ liệu nền (Giữ nguyên cấu trúc của bạn) ---
    void themVung(VungKhongGian vung);
    void xoaVung(String maVung);
    void capNhatVung(VungKhongGian vung);
    List<VungKhongGian> layTatCaCacVung();
    VungKhongGian timKiemVung(String maVung);

    // --- BỔ SUNG QUAN TRỌNG: HÀM TRUY VẤN KHÔNG GIAN KHOA HỌC ---
    /**
     * Quét và lọc nhanh các vùng tĩnh đang có sự va chạm/giao thoa với Geofence động.
     * Hàm này che giấu thuật toán tối ưu bên trong (Encapsulation), giúp Trình điều phối không cần 
     * phải ôm đồm việc lặp qua toàn bộ danh sách vùng tĩnh một cách thủ công.
     * * @param geofence Đối tượng Geofence động của con vật đang xét trên RAM
     * @param thoiGianTreGiay Khoảng thời gian trễ hệ thống (để Geofence tính bán kính chuẩn độ)
     * @return Danh sách các vùng tĩnh bị xâm nhập hoặc có nguy cơ rủi ro cao
     */
    List<VungKhongGian> timVungCoGiaoThoa(GeofenceDong geofence, double thoiGianTreGiay);
}