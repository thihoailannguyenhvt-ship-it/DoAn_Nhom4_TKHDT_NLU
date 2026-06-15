package module3_GiamSatVaQuanLyNguong.In;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Bộ đọc kết quả Module 1 – Tự động nhận diện và xử lý file dữ liệu TXT/CSV từ Module 1.
 * Thiết kế kiên cố, chống chịu tốt với sự thay đổi định dạng chuỗi của hệ thống.
 */
public class BoDocKetQuaModule1 {

    // Hằng số phân tách dữ liệu định dạng CSV
    private static final int CSV_COT_ID           = 0;
    private static final int CSV_COT_THOI_GIAN    = 1;
    private static final int CSV_SO_COT_TOI_THIEU = 2;

    /**
     * Đọc file dữ liệu của Module 1, tự động nhận diện định dạng TXT hoặc CSV dựa trên đuôi file.
     *
     * @param duongDanFile Đường dẫn vật lý đến file kết quả của Module 1
     * @return Danh sách các đối tượng BanGhiModule1 đã parse thành công
     * @throws IOException Bắn lỗi nếu không đọc được file hoặc file không tồn tại
     */
    public List<BanGhiModule1> docFile(String duongDanFile) throws IOException {
        if (duongDanFile == null || duongDanFile.trim().isEmpty()) {
            throw new IllegalArgumentException("Đường dẫn file Module 1 không hợp lệ!");
        }
        
        List<BanGhiModule1> ketQua = new ArrayList<>();
        int soDongHopLe = 0;
        int soDongBoQua = 0;
        boolean laCSV = duongDanFile.toLowerCase().endsWith(".csv");

        try (BufferedReader reader = new BufferedReader(new FileReader(duongDanFile))) {
            String dong;
            boolean boQuaHeader = laCSV; // Nếu là CSV thì dòng đầu tiên luôn là tiêu đề cột

            while ((dong = reader.readLine()) != null) {
                dong = dong.trim();
                if (dong.isEmpty()) continue;

                // XỬ LÝ CSV: Bỏ qua dòng tiêu đề (Header)
                if (boQuaHeader) {
                    boQuaHeader = false;
                    continue;
                }

                // XỬ LÝ TXT: Bỏ qua các dòng phân cách trang trí nếu có
                if (!laCSV && (dong.startsWith("=")
                        || dong.startsWith("KET QUA")
                        || dong.startsWith("MODULE"))) {
                    continue;
                }

                try {
                    // Gọi hàm parse tương ứng theo định dạng file
                    BanGhiModule1 banGhi = laCSV ? parseDongCSV(dong) : parseDongTXT(dong);

                    if (banGhi != null) {
                        ketQua.add(banGhi);
                        soDongHopLe++;
                    }
                } catch (Exception e) {
                    soDongBoQua++;
                    System.err.printf("[BoDocKetQuaModule1] Bỏ qua dòng lỗi: %s | Lý do: %s%n", dong, e.getMessage());
                }
            }
        }

        System.out.printf("[BoDocKetQuaModule1] Hoàn tất đọc file '%s' (%s): %d hợp lệ, %d bị bỏ qua.%n",
                duongDanFile, laCSV ? "CSV" : "TXT", soDongHopLe, soDongBoQua);

        return ketQua;
    }

    /**
     * Parse dòng dữ liệu văn bản (TXT).
     * Áp dụng thuật toán tìm kiếm từ khóa linh hoạt, chấp nhận mọi tiền tố như "KetQuaGoc[" hay "BanGhiSinhHoc[".
     */
    private BanGhiModule1 parseDongTXT(String dong) {
        if (dong == null || dong.trim().isEmpty()) return null;

        // KIỂM TRA ĐIỀU KIỆN CẦN: Dòng bắt buộc phải chứa từ khóa "idConVat=" để xử lý
        if (!dong.contains("idConVat=")) return null;

        // Trích xuất linh hoạt giá trị của trường idConVat
        String idConVat = trichXuatGiaTriLinhHoat(dong, "idConVat=");
        
        // Trích xuất linh hoạt trường thời gian (Hỗ trợ cả 2 cách đặt tên trường phổ biến)
        String thoiGianStr = trichXuatGiaTriLinhHoat(dong, "thoiGian=");
        if (thoiGianStr == null) {
            thoiGianStr = trichXuatGiaTriLinhHoat(dong, "mocThoiGianSuKien=");
        }

        // Báo lỗi nếu cấu trúc dòng bị thiếu thông tin cốt lõi
        if (idConVat == null || thoiGianStr == null) {
            throw new IllegalArgumentException("Dòng dữ liệu thiếu trường idConVat hoặc thoiGian!");
        }

        // Đổi chuỗi thời gian văn bản sang dạng chuỗi chuẩn đẹp đọc được
        String thoiGianSuKien = chuyenDoiThoiGian(thoiGianStr); 

        return new BanGhiModule1(idConVat, thoiGianSuKien);
    }

    /**
     * Parse dòng dữ liệu phân cách bằng dấu phẩy (CSV).
     */
    private BanGhiModule1 parseDongCSV(String dong) {
        if (dong == null || dong.trim().isEmpty()) return null;
        
        // Tách chuỗi theo dấu phẩy, giữ lại cả các cột rỗng ở cuối (-1)
        String[] cots = dong.split(",", -1);

        if (cots.length < CSV_SO_COT_TOI_THIEU) {
            throw new IllegalArgumentException(
                String.format("File CSV thiếu cột dữ liệu – Cần tối thiểu %d cột, thực tế có %d cột",
                    CSV_SO_COT_TOI_THIEU, cots.length));
        }

        String idConVat = cots[CSV_COT_ID].trim();
        String thoiGianStr = cots[CSV_COT_THOI_GIAN].trim();

        // Sử dụng bộ chuyển đổi thông minh, giúp file CSV nhận diện được cả số Unix lẫn chữ ngày tháng
        String thoiGianSuKien = chuyenDoiThoiGianVeString(thoiGianStr);

        if (idConVat.isEmpty()) {
            throw new IllegalArgumentException("Trường idConVat trong file CSV bị trống!");
        }

        return new BanGhiModule1(idConVat, thoiGianSuKien);
    }

    /**
     * HÀM TIỆN ÍCH: Tìm kiếm từ khóa và cắt chuỗi thông minh.
     * Tự động nhận biết điểm dừng tại dấu phẩy tiếp theo `,` hoặc dấu đóng ngoặc vuông `]`
     */
    private String trichXuatGiaTriLinhHoat(String noiDung, String tuKhoa) {
        int viTriBatDau = noiDung.indexOf(tuKhoa);
        if (viTriBatDau == -1) return null;
        
        viTriBatDau += tuKhoa.length();
        int viTriKetThuc = noiDung.length();
        
        int indexPhay = noiDung.indexOf(",", viTriBatDau);
        int indexDongNgoac = noiDung.indexOf("]", viTriBatDau);
        
        if (indexPhay != -1 && indexPhay < viTriKetThuc) viTriKetThuc = indexPhay;
        if (indexDongNgoac != -1 && indexDongNgoac < viTriKetThuc) viTriKetThuc = indexDongNgoac;
        
        return noiDung.substring(viTriBatDau, viTriKetThuc).trim();
    }

    /**
     * Hàm điều phối phụ thuộc, đồng bộ kết quả trả về dạng String theo lời gọi của parseDongTXT
     */
    private String chuyenDoiThoiGian(String timeStr) {
        return chuyenDoiThoiGianVeString(timeStr);
    }

    /**
     * HÀM XỬ LÝ CHUẨN HÓA THỜI GIAN CHÍNH CHẤT: Đổi toàn bộ Unix Epoch (Mili-giây) hoặc Chuỗi thô về chuỗi "yyyy-MM-dd HH:mm:ss"
     */
    private String chuyenDoiThoiGianVeString(String timeStr) {
        if (timeStr == null || timeStr.trim().isEmpty()) {
            return "1970-01-01 00:00:00";
        }
        timeStr = timeStr.trim();

        // Trường hợp 1: Nếu là số thuần túy (Mili-giây dài ngoằng từ file CSV) -> Đổi thành ngày tháng đẹp
        if (timeStr.matches("\\d+")) {
            try {
                long miliSeconds = Long.parseLong(timeStr);
                LocalDateTime ldt = LocalDateTime.ofInstant(Instant.ofEpochMilli(miliSeconds), ZoneId.systemDefault());
                return ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            } catch (Exception e) {
                return "1970-01-01 00:00:00";
            }
        }

        // Trường hợp 2: Nếu vốn dĩ đầu vào đã là chuỗi chữ ngày tháng dạng văn bản
        try {
            String chuoiChuanHoa = timeStr.replace("T", " "); // Khử ký tự T nếu có
            if (chuoiChuanHoa.contains("/")) {
                chuoiChuanHoa = chuoiChuanHoa.replace("/", "-");
            }
            
            // Thử kiểm tra định dạng xem có hợp lệ cấu trúc không
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime ldt = LocalDateTime.parse(chuoiChuanHoa, formatter);
            return ldt.format(formatter);
        } catch (Exception e) {
            // Nếu có lỗi định dạng lạ nhưng bản chất là chữ, trả về chính chuỗi sạch đó để tránh mất mát dữ liệu
            return timeStr;
        }
    }
}