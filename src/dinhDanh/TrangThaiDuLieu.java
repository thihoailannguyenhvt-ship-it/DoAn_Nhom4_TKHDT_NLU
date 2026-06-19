package dinhDanh;

/**
 * Enum TrangThaiDuLieu - Gắn nhãn trạng thái cho từng bản ghi sinh trắc (BioDataRecord).
 * Giúp các module sau nhận biết nguồn gốc tọa độ để đưa ra cảnh báo sinh tồn chính xác.
 */
public enum TrangThaiDuLieu {

    /**
     * Dữ liệu GPS thực tế thu nhận trực tiếp từ thiết bị chip đeo hoặc giả lập khớp thời gian.
     */
    THUC_TE,

    /**
     * Dữ liệu tọa độ được tính toán nội suy tuyến tính khi có sự lệch pha giữa các luồng.
     */
    NOI_SUY,

    /**
     * Dữ liệu mượn tạm tọa độ hợp lệ cuối cùng trong bộ nhớ đệm khi mất dấu GPS ngắn hạn (dưới 15 phút).
     */
    MUON_TAM,

    /**
     * Cảnh báo mất kết nối GPS quá lâu (vượt quá ngưỡng cho phép 15 phút).
     * Hệ thống bắt buộc phải ngừng mượn tọa độ cũ và phát tín hiệu cảnh báo mất dấu con vật.
     */
    MAT_KET_NOI,

    /**
     * Cảnh báo độ trễ mạng nghiêm trọng (LATENCY_WARNING).
     * Dữ liệu thực địa xảy ra từ hơn 2 ngày trước giờ mới truyền về trạm, hệ thống vẫn xử lý nhưng gắn cờ cảnh báo.
     */
    TRE_NGHIEM_TRONG,

    /**
     * Trạng thái khởi động lạnh (WAITING_FOR_INITIAL_GPS).
     * Hệ thống nhận được dữ liệu gia tốc (ACC) nhưng bộ đệm hoàn toàn chưa có bản ghi GPS nào làm mốc ban đầu.
     */
    KHOI_DONG_LANH,
    
    GIA_LAP
}