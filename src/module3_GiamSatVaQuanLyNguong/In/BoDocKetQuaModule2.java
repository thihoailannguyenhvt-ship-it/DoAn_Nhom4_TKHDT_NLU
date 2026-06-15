package module3_GiamSatVaQuanLyNguong.In;

import dinhDanh.NhanTrangThai;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Bộ đọc kết quả Module 2 – parse file TXT và CSV thực tế từ Module 2.
 *
 * <h3>Format TXT thực tế từ KetQuaHanhVi.toString()</h3>
 * <pre>
 * KetQuaHanhVi[idConVat=001-LonRung-CT, trangThai=DINH_BAY,
 *   ODBA=0.019, tyLeNangLuong=0.025, doTreModule2=79370295 ms]
 * </pre>
 * Lưu ý: TXT KHÔNG có t1, t2 – chỉ có doTreModule2.
 *
 * <h3>Format CSV thực tế từ KetQuaHanhVi.toCSV()</h3>
 * <pre>
 * idConVat,trangThai,ODBA,tyLeNangLuong,t1,t2,doTreModule2
 * 001-LonRung-CT,DINH_BAY,0.019,0.025,1781344800000,1781424170295,79370295
 * </pre>
 *
 * Module 3 chỉ trích xuất {@code idConVat} và {@code trangThai}.
 * Tất cả trường còn lại bỏ qua – Module 1 đã xử lý thời gian rồi.
 */
public class BoDocKetQuaModule2 {

    // ── Hằng số format TXT ────────────────────────────────────────────────
    private static final String TIEN_TO_TXT       = "KetQuaHanhVi[";
    private static final String TRUONG_ID         = "idConVat=";
    private static final String TRUONG_TRANG_THAI = "trangThai=";

    // ── Hằng số format CSV ────────────────────────────────────────────────
    // Header thực tế: idConVat,trangThai,ODBA,tyLeNangLuong,t1,t2,doTreModule2
    private static final int CSV_COT_ID          = 0;
    private static final int CSV_COT_TRANG_THAI  = 1;
    private static final int CSV_SO_COT_TOI_THIEU = 2;

    // ── Phương thức chính ──────────────────────────────────────────────────

    /**
     * Đọc file và tự động nhận diện format TXT hoặc CSV.
     *
     * @param duongDanFile Đường dẫn file Module 2 (TXT hoặc CSV)
     * @return Danh sách {@link BanGhiModule2}
     * @throws IOException Nếu không đọc được file
     */
    public List<BanGhiModule2> docFile(String duongDanFile) throws IOException {
    	if (duongDanFile == null || duongDanFile.trim().isEmpty()) {
    	    throw new IllegalArgumentException(
    	            "Duong dan file khong hop le");
    	}
        List<BanGhiModule2> ketQua      = new ArrayList<>();
        int                 soDongHopLe = 0;
        int                 soDongBoQua = 0;
        boolean             laCSV       = duongDanFile.toLowerCase().endsWith(".csv");

        try (BufferedReader reader = new BufferedReader(
                new FileReader(duongDanFile))) {

            String  dong;
            boolean boQuaHeader = laCSV;

            while ((dong = reader.readLine()) != null) {
                dong = dong.trim();
                if (dong.isEmpty()) continue;

                // CSV: bỏ qua dòng header
                if (boQuaHeader) {
                    boQuaHeader = false;
                    continue;
                }

                // TXT: bỏ qua tiêu đề và phân cách
                if (!laCSV && (dong.startsWith("=")
                        || dong.startsWith("KET QUA")
                        || dong.startsWith("MODULE"))) {
                    continue;
                }

                try {
                    BanGhiModule2 banGhi = laCSV
                        ? parseDongCSV(dong)
                        : parseDongTXT(dong);

                    if (banGhi != null) {
                        ketQua.add(banGhi);
                        soDongHopLe++;
                    }
                } catch (Exception e) {
                    soDongBoQua++;
                    System.err.printf(
                        "[BoDocKetQuaModule2] Bo qua dong loi: %s%n  Ly do: %s%n",
                        dong, e.getMessage());
                }
            }
        }

        System.out.printf(
            "[BoDocKetQuaModule2] '%s' (%s): %d ban ghi hop le, %d dong bo qua%n",
            duongDanFile, laCSV ? "CSV" : "TXT", soDongHopLe, soDongBoQua);

        return ketQua;
    }

    // ── Parse format TXT ──────────────────────────────────────────────────

    /**
     * Parse dòng TXT thực tế từ KetQuaHanhVi.toString():
     * KetQuaHanhVi[idConVat=..., trangThai=..., ODBA=...,
     *              tyLeNangLuong=..., doTreModule2=... ms]
     *
     * Chỉ lấy idConVat và trangThai, bỏ qua phần còn lại.
     * Trả về null nếu không phải dòng dữ liệu KetQuaHanhVi.
     */
    private BanGhiModule2 parseDongTXT(String dong) {
    	if (dong == null || dong.trim().isEmpty()) {
    	    return null;
    	}
        if (!dong.startsWith(TIEN_TO_TXT)) return null;

        int viTriMo   = dong.indexOf('[');
        int viTriDong = dong.lastIndexOf(']');
        if (viTriMo < 0 || viTriDong <= viTriMo) {
            return null;
        }

        // Split theo ", " trước chữ cái để tránh tách nhầm số khoa học (1.23E-16)
        String   noiDung = dong.substring(viTriMo + 1, viTriDong);
        String[] truongs =
                noiDung
                       .split(",\\s*(?=[a-zA-Z])");

        String        idConVat  = null;
        NhanTrangThai trangThai = null;

        for (String truong : truongs) {
            truong = truong.trim();
            if (truong.startsWith(TRUONG_ID)) {
                idConVat = truong.substring(TRUONG_ID.length()).trim();
            } else if (truong.startsWith(TRUONG_TRANG_THAI)) {
                trangThai = parseNhan(
                    truong.substring(TRUONG_TRANG_THAI.length()).trim());
            }
            // ODBA, tyLeNangLuong, doTreModule2 → bỏ qua hoàn toàn
        }

        kiemTraDuTruong(idConVat, trangThai);
        return new BanGhiModule2(idConVat, trangThai);
    }

    // ── Parse format CSV ──────────────────────────────────────────────────

    /**
     * Parse dòng CSV thực tế từ KetQuaHanhVi.toCSV():
     * idConVat,trangThai,ODBA,tyLeNangLuong,t1,t2,doTreModule2
     *
     * Chỉ lấy cột 0 (idConVat) và cột 1 (trangThai).
     */
    private BanGhiModule2 parseDongCSV(String dong) {
        // Dùng split giới hạn để tránh nhầm dấu phẩy trong số
        String[] cots = dong.split(",", -1);
        if (dong.trim().isEmpty()) {
            return null;
        }

        if (cots.length < CSV_SO_COT_TOI_THIEU) {
            throw new IllegalArgumentException(
                String.format("CSV thieu cot – can toi thieu %d, co %d",
                    CSV_SO_COT_TOI_THIEU, cots.length));
        }

        String        idConVat  = cots[CSV_COT_ID].trim();
        NhanTrangThai trangThai = parseNhan(cots[CSV_COT_TRANG_THAI].trim());

        kiemTraDuTruong(idConVat, trangThai);
        return new BanGhiModule2(idConVat, trangThai);
    }

    // ── Tiện ích ──────────────────────────────────────────────────────────

    /**
     * Ánh xạ chuỗi nhãn từ file sang NhanTrangThai.
     * Không phân biệt hoa/thường để tránh lỗi định dạng file.
     */
    private NhanTrangThai parseNhan(String tenNhan) {

        if (tenNhan == null) {
            throw new IllegalArgumentException("Nhan rong");
        }

        tenNhan = tenNhan.trim().toUpperCase();

        switch (tenNhan) {
            case "BINH_THUONG":
                return NhanTrangThai.BINH_THUONG;

            case "DINH_BAY":
                return NhanTrangThai.DINH_BAY;

            case "SUY_KIET":
                return NhanTrangThai.SUY_KIET;

            default:
                throw new IllegalArgumentException(
                    "Nhan khong hop le: '" + tenNhan + "'");
        }
    }

    private void kiemTraDuTruong(String idConVat, NhanTrangThai trangThai) {
        if (idConVat == null || idConVat.isEmpty()) {
            throw new IllegalArgumentException("Thieu idConVat");
        }
        if (trangThai == null) {
            throw new IllegalArgumentException("Thieu trangThai");
        }
    }
}