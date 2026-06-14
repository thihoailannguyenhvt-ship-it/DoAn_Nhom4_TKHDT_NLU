package module3_GiamSatVaQuanLyNguong.In;

import dinhDanh.NhanTrangThai;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Bộ đọc kết quả Module 2 – parse file CSV hoặc TXT đầu vào.
 *
 * <h3>Định dạng file hỗ trợ</h3>
 * <pre>
 * KetQuaHanhVi[idConVat=001-LonRung-CT, trangThai=DINH_BAY,
 *   ODBA=0.019, tyLeNangLuong=0.025, t1=1781344800000,
 *   t2=1781424170295, doTreModule2=79370295 ms]
 * </pre>
 *
 * Module 3 chỉ trích xuất: {@code idConVat}, {@code trangThai}, {@code t1}.
 * Thời gian hệ thống lấy bằng {@code System.currentTimeMillis()}
 * tại thời điểm xử lý – không đọc từ file.
 *
 * <h3>Xử lý lỗi</h3>
 * Dòng không đúng định dạng được ghi log cảnh báo và bỏ qua –
 * không làm gián đoạn việc đọc các dòng còn lại.
 */
public class BoDocKetQuaModule2 {

    private static final String TIEN_TO_DONG      = "KetQuaHanhVi[";
    private static final String TRUONG_ID         = "idConVat=";
    private static final String TRUONG_TRANG_THAI = "trangThai=";
    private static final String TRUONG_T1         = "t1=";

    // ── Phương thức chính ──────────────────────────────────────────────────

    /**
     * Đọc toàn bộ file và trả về danh sách bản ghi đã parse.
     *
     * @param duongDanFile Đường dẫn file CSV hoặc TXT của Module 2
     * @return Danh sách {@link BanGhiModule2}, rỗng nếu không có dòng hợp lệ
     * @throws IOException Nếu không đọc được file
     */
    public List<BanGhiModule2> docFile(String duongDanFile) throws IOException {
        List<BanGhiModule2> ketQua      = new ArrayList<>();
        int                 soDongHopLe = 0;
        int                 soDongBoQua = 0;

        try (BufferedReader reader = new BufferedReader(
                new FileReader(duongDanFile))) {

            String dong;
            while ((dong = reader.readLine()) != null) {
                dong = dong.trim();

                // Bỏ qua dòng trống, tiêu đề, phân cách
                if (dong.isEmpty()
                        || dong.startsWith("=")
                        || dong.startsWith("KET QUA")) {
                    continue;
                }

                if (!dong.startsWith(TIEN_TO_DONG)) {
                    soDongBoQua++;
                    continue;
                }

                try {
                    BanGhiModule2 banGhi = parseDong(dong);
                    ketQua.add(banGhi);
                    soDongHopLe++;
                } catch (Exception e) {
                    soDongBoQua++;
                    System.err.printf(
                        "[BoDocketQuaModule2] Bỏ qua dòng lỗi: %s%n  Lý do: %s%n",
                        dong, e.getMessage());
                }
            }
        }

        System.out.printf(
            "[BoDocketQuaModule2] '%s': %d bản ghi hợp lệ, %d dòng bỏ qua%n",
            duongDanFile, soDongHopLe, soDongBoQua);

        return ketQua;
    }

    // ── Logic parse ───────────────────────────────────────────────────────

    /**
     * Parse một dòng dữ liệu thành {@link BanGhiModule2}.
     * Chỉ đọc idConVat, trangThai, t1 – bỏ qua các trường còn lại.
     */
    private BanGhiModule2 parseDong(String dong) {
        // Bóc nội dung trong [ ]
        int viTriMo   = dong.indexOf('[');
        int viTriDong = dong.lastIndexOf(']');
        if (viTriMo < 0 || viTriDong <= viTriMo) {
            throw new IllegalArgumentException("Không tìm thấy cặp dấu [ ]");
        }
        String noiDung = dong.substring(viTriMo + 1, viTriDong);

        // Split theo ", " đứng trước chữ cái – tránh tách nhầm số khoa học
        String[] truongs = noiDung.split(",\\s*(?=[a-zA-Z])");

        String        idConVat  = null;
        NhanTrangThai trangThai = null;
        long          t1        = -1;

        for (String truong : truongs) {
            truong = truong.trim();

            if (truong.startsWith(TRUONG_ID)) {
                idConVat = layGiaTri(truong, TRUONG_ID);

            } else if (truong.startsWith(TRUONG_TRANG_THAI)) {
                trangThai = parseNhan(layGiaTri(truong, TRUONG_TRANG_THAI));

            } else if (truong.startsWith(TRUONG_T1)) {
                // Chỉ lấy phần số nguyên, loại bỏ ký tự thừa nếu có
                t1 = Long.parseLong(
                    layGiaTri(truong, TRUONG_T1).replaceAll("[^0-9]", ""));
            }
            // t2, ODBA, tyLeNangLuong, doTreModule2 → bỏ qua hoàn toàn
        }

        if (idConVat == null || trangThai == null || t1 < 0) {
            throw new IllegalArgumentException(
                String.format("Thiếu trường bắt buộc – id=%s, trangThai=%s, t1=%d",
                    idConVat, trangThai, t1));
        }

        return new BanGhiModule2(idConVat, trangThai, t1);
    }

    private String layGiaTri(String truong, String tenTruong) {
        return truong.substring(tenTruong.length()).trim();
    }

    /**
     * Ánh xạ chuỗi nhãn từ file sang {@link NhanTrangThai}.
     * Không phân biệt hoa/thường.
     */
    private NhanTrangThai parseNhan(String tenNhan) {
        switch (tenNhan.toUpperCase().trim()) {
            case "BINH_THUONG": return NhanTrangThai.BINH_THUONG;
            case "DINH_BAY":    return NhanTrangThai.DINH_BAY;
            case "SUY_KIET":    return NhanTrangThai.SUY_KIET;
            default:
                throw new IllegalArgumentException(
                    "Nhãn không hợp lệ: '" + tenNhan + "'");
        }
    }
}

