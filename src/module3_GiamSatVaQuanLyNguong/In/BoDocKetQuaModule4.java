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
 * Lưu ý: File đầu vào là dạng Log khối (Multi-line Block). Mỗi bản ghi nằm trên nhiều dòng, 
 * phân cách nhau bằng keyword "ID con vat".
 * </p>
 */
public class BoDocKetQuaModule4 {

    /**
     * Đọc file dạng khối (block) với định dạng "Key : Value".
     * Hàm dùng cơ chế "tích lũy": gom dần dữ liệu các dòng, khi gặp "ID con vat" mới thì chốt bản ghi cũ.
     *
     * @param duongDanFile Đường dẫn tới file kết quả của Module 4
     * @return Danh sách các đối tượng BanGhiModule4 đã parse thành công
     * @throws IOException Lỗi nếu không tìm thấy file hoặc file bị hỏng
     */
    public List<BanGhiModule4> docFile(String duongDanFile) throws IOException {
        List<BanGhiModule4> ketQua = new ArrayList<>();

        // 1. CÁC BIẾN TẠM ĐỂ TÍCH LŨY DỮ LIỆU (CẦN RESET KHI GẶP CON VẬT MỚI)
        String id = null;
        String nhanVungStr = null;
        Double viDo = null;
        Double kinhDo = null;
        String idVung = null;           // ID của vùng không gian bị giao thoa
        String loaiVung = null;         // Loại vùng (ví dụ: TramQuanSat, KhuDanCu...)
        Double phanTramGiaoThoa = 0.0; // Tỷ lệ giao thoa được tính theo %
      

        try (BufferedReader reader = new BufferedReader(new FileReader(duongDanFile))) {
            String dong;
            
            while ((dong = reader.readLine()) != null) {
                dong = dong.trim();
                
                // Bỏ qua dòng trống
                if (dong.isEmpty()) continue;
             // THÊM DÒNG NÀY ĐỂ XEM FILE ĐANG CHỨA GÌ
                System.out.println("DEBUG - Dòng đang đọc: '" + dong + "'");
                // 2. PHÁT HIỆN BẢN GHI MỚI & CHỐT BẢN GHI CŨ
                if (dong.startsWith("ID con vat")) {
                    
                    // Nếu đã có ID, nghĩa là trước đó đang đọc 1 con vật khác -> lưu lại
                    if (id != null) {
                        ketQua.add(new BanGhiModule4(id, parseNhan(nhanVungStr), viDo, kinhDo, loaiVung, idVung, phanTramGiaoThoa));
                    }
                    
                    // Reset dữ liệu cũ để chuẩn bị cho con vật mới
                    id = tachGiaTri(dong);
                    nhanVungStr = null; 
                    viDo = null; 
                    kinhDo = null; 
                    phanTramGiaoThoa = 0.0; 
                    idVung = null; 
                    loaiVung = null;
                } 
                
                // 3. TÍCH LŨY CÁC TRƯỜNG DỮ LIỆU
                else if (dong.startsWith("Vi tri")) {
                    String coords = tachGiaTri(dong).replaceAll("[()]", ""); 
                    String[] parts = coords.split(",");
                    viDo = Double.parseDouble(parts[0].trim());
                    kinhDo = Double.parseDouble(parts[1].trim());
                    
                } 
                
                else if (dong.startsWith("ID vung")) {
                    idVung = tachGiaTri(dong);
                }
                else if (dong.startsWith("Loai Vung")) {
                    loaiVung = tachGiaTri(dong);
                    System.out.println("-> Đã nhận Loai Vung: " + loaiVung); // Dòng này sẽ xác nhận
                }
                else if (dong.startsWith("Phan tram giao thoa")) {
                    // Loại bỏ % và chuyển sang double
                    phanTramGiaoThoa = Double.parseDouble(tachGiaTri(dong).replace("%", ""));
                } 
                else if (dong.startsWith("Nguy co khong gian")) {
                    nhanVungStr = tachGiaTri(dong);
                }
            }
            // 4. CHỐT BẢN GHI CUỐI CÙNG
            // Vì bản ghi cuối không có ID con vật tiếp theo để trigger, ta phải thêm thủ công
            if (id != null) {
                ketQua.add(new BanGhiModule4(id, parseNhan(nhanVungStr), viDo, kinhDo, loaiVung, idVung, phanTramGiaoThoa));
             // Trong BoDocKetQuaModule4.java
             // Sau khi add vào list
             BanGhiModule4 moiTao = new BanGhiModule4(id, parseNhan(nhanVungStr), viDo, kinhDo, loaiVung, idVung, phanTramGiaoThoa);
             ketQua.add(moiTao);

             // DEBUG NGAY TẠI CHỖ
             System.out.println("-> DEBUG Object vừa tạo: ID Vung=" + moiTao.layIdVung() + " | Loai Vung=" + moiTao.layLoaiVung());
            }
        }
        
        System.out.println("[BoDocKetQuaModule4] Đã đọc xong, tổng số bản ghi: " + ketQua.size());
        return ketQua;
    }

    /**
     * Tách giá trị sau dấu hai chấm (:).
     */
    private String tachGiaTri(String dong) {
        int idx = dong.indexOf(":");
        return (idx != -1) ? dong.substring(idx + 1).trim() : "";
    }

    /**
     * Chuyển đổi chuỗi nhãn đọc từ file sang Enum TinhTrangVung.
     */
    private TinhTrangVung parseNhan(String tenNhan) {
        if (tenNhan == null) return TinhTrangVung.AN_TOAN;
        
        tenNhan = tenNhan.trim().toUpperCase();
        
        switch (tenNhan) {
            case "ANTOAN": 
            case "AN_TOAN": return TinhTrangVung.AN_TOAN;
            case "TIENCANHBAO": 
            case "TIEN_CANH_BAO": return TinhTrangVung.TIEN_CANH_BAO;
            case "CANHBAO": 
            case "CANH_BAO": return TinhTrangVung.CANH_BAO;
            case "NGUYCAP": 
            case "NGUY_CAP": return TinhTrangVung.NGUY_CAP;
            default: return TinhTrangVung.AN_TOAN;
        }
    }
}