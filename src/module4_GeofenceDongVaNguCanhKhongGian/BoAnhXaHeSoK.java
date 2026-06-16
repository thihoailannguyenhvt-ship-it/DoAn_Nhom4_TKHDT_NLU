package module4_GeofenceDongVaNguCanhKhongGian;

import dinhDanh.NhanTrangThai;

/**
 * BoAnhXaHeSoK (Mapper)
 * Chịu trách nhiệm duy nhất: Chuyển đổi trạng thái sinh học (Input) sang hệ số toán học K.
 * * Tại sao sửa lại?
 * 1. Loại bỏ biến instance 'private double k': Không lưu trạng thái để tránh lỗi dùng chung dữ liệu.
 * 2. Sử dụng Enum NhanTrangThai thay vì String: Tránh lỗi nhập liệu, ép kiểu dữ liệu chặt chẽ.
 * 3. Tách biệt hoàn toàn: Chỉ ánh xạ Input, không chứa logic của Output (NGUYCAP, ANTOAN...).
 */
public class BoAnhXaHeSoK {

    // Định nghĩa các hằng số hệ số K để dễ dàng điều chỉnh sau này
    private static final double K_BINH_THUONG = 1.0;
    private static final double K_SUY_KIET    = 4.0;
    private static final double K_DINH_BAY   = 5.0;

    /**
     * Hàm lấy hệ số K dựa trên nhãn hành vi đầu vào.
     * * @param trangThai Enum trạng thái (BINH_THUONG, SUY_KIET, DINH_BAY).
     * @return Hệ số K (double) tương ứng để tính bán kính động.
     */
    public double layHeSoK(NhanTrangThai trangThai) {
        
        // Kiểm tra an toàn: Nếu đầu vào null, mặc định về trạng thái bình thường (k=1.0)
        if (trangThai == null) {
            return K_BINH_THUONG;
        }

        // Logic ánh xạ rõ ràng: Input Sinh học -> Output Toán học
        switch (trangThai) {
            case BINH_THUONG:
                return K_BINH_THUONG;
                
            case SUY_KIET:
                return K_SUY_KIET; // Trạng thái này cần vùng đệm lớn hơn để cảnh báo sớm
                
            case DINH_BAY:
                return K_DINH_BAY; // Trạng thái nguy cấp nhất, vùng đệm giãn tối đa
                
            default:
                // Trả về mặc định an toàn cho các trường hợp chưa định nghĩa
                return K_BINH_THUONG;
        }
    }
}