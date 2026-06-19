package module1_DongBoHoa;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public final class QuanLyThoiGian {

    // DateTimeFormatter cua java.time la Thread-safe va cuc ky nhanh
    private static final DateTimeFormatter FORMATTER = 
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
                             .withZone(ZoneId.systemDefault());

    private QuanLyThoiGian() {
        throw new UnsupportedOperationException("Lop tien ich.");
    }

    public static long layThoiGianHeThongHienTai() {
        return System.currentTimeMillis();
    }

    /**
     * Tinh khoang cach: Bo Math.abs de phat hien du lieu loi (timestamp tuong lai).
     */
    public static long tinhKhoangCach(long mocTruoc, long mocSau) {
        return mocSau - mocTruoc;
    }

    public static boolean laMatTinHieu(long mSuKienTruoc, long mSuKienSau, long gioiHanMatGPS) {
        // Khoang cach > gioi han thi coi la mat tin hieu
        return tinhKhoangCach(mSuKienTruoc, mSuKienSau) > gioiHanMatGPS;
    }

    public static String dinhDangTimestamp(long timestamp) {
        if (timestamp <= 0) return "N/A";
        return FORMATTER.format(Instant.ofEpochMilli(timestamp));
    }
}