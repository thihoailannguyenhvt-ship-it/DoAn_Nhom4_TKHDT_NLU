package module3_GiamSatVaQuanLyNguong.In;

import dinhDanh.TinhTrangVung;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Bộ đọc kết quả Module 4.
 * <p>
 * Lưu ý: File đầu vào không phải dạng CSV hay TXT 1 dòng, 
 * mà là dạng Log khối (Multi-line Block). Mỗi bản ghi nằm trên nhiều dòng, 
 * phân cách nhau bằng keyword "ID con vat".
 * </p>
 */
public class BoDocKetQuaModule4 {

    /**
     * Đọc file dạng khối (block) với định dạng "Key : Value".
     * Thay vì đọc và ép kiểu ngay trên 1 dòng, hàm này dùng cơ chế "tích lũy":
     * Gom dần dữ liệu của các dòng lại, khi nào gặp ID con vật mới thì chốt bản ghi cũ.
     *
     * @param duongDanFile Đường dẫn tới file kết quả của Module 4
     * @return Danh sách các đối tượng BanGhiModule4 đã được parse thành công
     * @throws IOException Bắn lỗi nếu không tìm thấy file hoặc file bị hỏng
     */
    public List<BanGhiModule4> docFile(String duongDanFile) throws IOException {
        List<BanGhiModule4> ketQua = new ArrayList<>();

        // 1. CÁC BIẾN TẠM ĐỂ TÍCH LŨY DỮ LIỆU CỦA 1 BẢN GHI
        // Giống như một cái giỏ, ta đi nhặt từng dòng bỏ vào đây trước khi đóng gói
        String id = null;
        String nhanVungStr = null;
        Double viDo = null;
        Double kinhDo = null;
        Double tyLeGiaoThoa = null;
        Double banKinh = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(duongDanFile))) {
            String dong;
            
            // Đọc lần lượt từng dòng từ trên xuống dưới
            while ((dong = reader.readLine()) != null) {
                dong = dong.trim();
                
                // Bỏ qua các dòng trống để tránh lỗi
                if (dong.isEmpty()) continue;

                // 2. PHÁT HIỆN BẢN GHI MỚI & CHỐT BẢN GHI CŨ
                // Nếu dòng bắt đầu bằng "ID con vat", nghĩa là ta chuẩn bị đọc sang 1 con vật mới
                if (dong.startsWith("ID con vat")) {
                    
                    // Nếu biến id khác null, tức là "cái giỏ" đang chứa dữ liệu của con vật TRƯỚC ĐÓ.
                    // Ta phải đóng gói nó lại và ném vào List kết quả.
                    if (id != null) {
                        ketQua.add(new BanGhiModule4(id, parseNhan(nhanVungStr), viDo, kinhDo, tyLeGiaoThoa, banKinh));
                    }
                    
                    // Bắt đầu nhặt dữ liệu cho con vật MỚI:
                    // Lấy ID mới và reset toàn bộ các biến tạm khác về null để tránh dính dữ liệu cũ
                    id = tachGiaTri(dong);
                    nhanVungStr = null; 
                    viDo = null; 
                    kinhDo = null; 
                    tyLeGiaoThoa = null; 
                    banKinh = null;
                } 
                
                // 3. TÍCH LŨY CÁC TRƯỜNG DỮ LIỆU CÒN LẠI
                // Chỉ lấy những dòng ta cần, các dòng khác (như ODBA, Hệ số K...) tự động bị lờ đi.
                else if (dong.startsWith("Vi tri")) {
                    // Dữ liệu thô: (11.435684, 107.442002)
                    // Xóa 2 dấu ngoặc tròn ở hai đầu
                    String coords = tachGiaTri(dong).replaceAll("[()]", ""); 
                    // Tách bằng dấu phẩy để lấy vĩ độ và kinh độ
                    String[] parts = coords.split(",");
                    viDo = Double.parseDouble(parts[0].trim());
                    kinhDo = Double.parseDouble(parts[1].trim());
                } 
                else if (dong.startsWith("Ban kinh Geofence")) {
                    // Dữ liệu thô: 0.002
                    banKinh = Double.parseDouble(tachGiaTri(dong));
                } 
                else if (dong.startsWith("Phan tram giao thoa")) {
                    // Dữ liệu thô: 78.5398165380388%
                    // Phải xóa dấu '%' đi thì hàm Double.parseDouble mới chạy được
                    tyLeGiaoThoa = Double.parseDouble(tachGiaTri(dong).replace("%", ""));
                } 
                else if (dong.startsWith("Nguy co khong gian")) {
                    // Dữ liệu thô: CANHBAO
                    nhanVungStr = tachGiaTri(dong);
                }
            }
            
            // 4. CHỐT BẢN GHI CUỐI CÙNG
            // Vòng lặp while kết thúc khi hết file, lúc này "cái giỏ" vẫn đang chứa con vật cuối cùng.
            // Nếu không có đoạn này, con vật ở dòng cuối file sẽ bị bỏ sót!
            if (id != null) {
                ketQua.add(new BanGhiModule4(id, parseNhan(nhanVungStr), viDo, kinhDo, tyLeGiaoThoa, banKinh));
            }
        }
        
        System.out.println("[BoDocKetQuaModule4] Đã đọc xong, tổng số bản ghi: " + ketQua.size());
        return ketQua;
    }

    /**
     * Hàm tiện ích giúp lấy phần giá trị nằm sau dấu hai chấm (:).
     * Ví dụ: Truyền vào "Trang thai hanh vi : BINH_THUONG" -> Trả về "BINH_THUONG"
     */
    private String tachGiaTri(String dong) {
        int idx = dong.indexOf(":");
        // Nếu tìm thấy dấu ':', cắt từ sau dấu ':' đến hết chuỗi và xóa khoảng trắng 2 đầu.
        // Nếu không có dấu ':', trả về chuỗi rỗng để tránh lỗi NullPointer.
        return (idx != -1) ? dong.substring(idx + 1).trim() : "";
    }

    /**
     * Hàm tiện ích giúp chuyển đổi chuỗi nhãn đọc từ file sang kiểu Enum an toàn.
     * Bao phủ cả các trường hợp viết liền hoặc có dấu gạch dưới.
     */
    private TinhTrangVung parseNhan(String tenNhan) {
        // Phòng hờ dữ liệu bị thiếu ở file log, set mặc định là AN_TOAN
        if (tenNhan == null) return TinhTrangVung.AN_TOAN;
        
        // Chuẩn hóa: Cắt khoảng trắng và viết hoa toàn bộ để dễ so sánh
        tenNhan = tenNhan.trim().toUpperCase();
        
        switch (tenNhan) {
            case "ANTOAN": 
            case "AN_TOAN": 
                return TinhTrangVung.AN_TOAN;
            case "TIENCANHBAO": 
            case "TIEN_CANH_BAO": 
                return TinhTrangVung.TIEN_CANH_BAO;
            case "CANHBAO": 
            case "CANH_BAO": 
                return TinhTrangVung.CANH_BAO;
            case "NGUYCAP": 
            case "NGUY_CAP": 
                return TinhTrangVung.NGUY_CAP;
            default: 
                // Nếu xuất hiện một nhãn lạ, trả về AN_TOAN tránh crash ứng dụng
                return TinhTrangVung.AN_TOAN;
        }
    }
}