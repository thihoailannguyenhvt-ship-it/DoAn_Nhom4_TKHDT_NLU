package module1_DongBoHoa;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

// Lop chuyen doi du lieu tho thanh du lieu co cau truc
public class BoChuyenDoiDuLieu {

    // Dinh dang nguon du lieu
    private String dinhDangNguon;

    // Ky tu phan cach
    private String kyTuPhanCach;

    // Bo kiem tra du lieu
    private BoKiemTraDuLieu boKiemTra;

    // Ham khoi tao
    public BoChuyenDoiDuLieu(String dinhDangNguon,
                             String kyTuPhanCach,
                             BoKiemTraDuLieu boKiemTra) {

        this.dinhDangNguon = dinhDangNguon;
        this.kyTuPhanCach = kyTuPhanCach;
        this.boKiemTra = boKiemTra;
    }

    // Chuyen du lieu tho thanh ban ghi gia toc
    public BanGhiGiaTocBD chuyenDoiGiaToc(String rawLine) throws NgoaiLeDuLieuLoi {

        if (rawLine == null || rawLine.trim().isEmpty()) {
            throw new NgoaiLeDuLieuLoi("Dong du lieu rong");
        }

        String[] parts = rawLine.split(kyTuPhanCach, -1);

        if (parts.length < 12) {
            throw new NgoaiLeDuLieuLoi(rawLine);
        }

        try {

            // Cot 2 la timestamp
            long thoiDiem = chuyenTimestampSangMillis(parts[2].trim());

            // Cot 5 la accelerations-raw
            String rawAcc = parts[5].trim();

            if (rawAcc.isEmpty()) {
                throw new NgoaiLeDuLieuLoi(rawLine);
            }

            String[] values = rawAcc.split("\\s+");

            List<Double> trucX = new ArrayList<>();
            List<Double> trucY = new ArrayList<>();
            List<Double> trucZ = new ArrayList<>();

            for (int i = 0; i + 2 < values.length; i += 3) {

                trucX.add(Double.parseDouble(values[i]));
                trucY.add(Double.parseDouble(values[i + 1]));
                trucZ.add(Double.parseDouble(values[i + 2]));
            }

            if (trucX.isEmpty()) {
                throw new NgoaiLeDuLieuLoi(rawLine);
            }

            return new BanGhiGiaTocBD(
                    thoiDiem,
                    trucX,
                    trucY,
                    trucZ
            );

        } catch (NgoaiLeDuLieuLoi e) {

            throw e;

        } catch (Exception e) {

            throw new NgoaiLeDuLieuLoi(rawLine);
        }
    }

    // Chuyen raw GPS dang "viDo;kinhDo" thanh toa do
    public ToaDo chuyenDoiToaDo(String rawLine) throws NgoaiLeDuLieuLoi {

        if (rawLine == null || rawLine.trim().isEmpty()) {
            throw new NgoaiLeDuLieuLoi("Dong GPS rong");
        }

        String[] parts = rawLine.split(kyTuPhanCach, -1);

        try {

            double viDo = Double.parseDouble(parts[0].trim());
            double kinhDo = Double.parseDouble(parts[1].trim());

            ToaDo toaDo = new ToaDo(viDo, kinhDo);

            if (!boKiemTra.laToaDoVatly(toaDo)) {
                throw new NgoaiLeDuLieuLoi(rawLine);
            }

            return toaDo;

        } catch (Exception e) {

            throw new NgoaiLeDuLieuLoi(rawLine);
        }
    }

    // Chuyen doi toa do tu dong CSV
    // File CSV co cac cot goc 0-12 va them viDo, kinhDo o cuoi file
    public ToaDo chuyenDoiToaDoTuDongCSV(String rawLine) throws NgoaiLeDuLieuLoi {

        if (rawLine == null || rawLine.trim().isEmpty()) {
            throw new NgoaiLeDuLieuLoi("Dong du lieu rong");
        }

        String[] parts = rawLine.split(kyTuPhanCach, -1);

        try {

            // Neu file co them 2 cot viDo va kinhDo o cuoi
            if (parts.length >= 15) {

                // Cot ke cuoi la viDo
                double viDo = Double.parseDouble(parts[parts.length - 2].trim());

                // Cot cuoi la kinhDo
                double kinhDo = Double.parseDouble(parts[parts.length - 1].trim());

                ToaDo toaDo = new ToaDo(viDo, kinhDo);

                if (!boKiemTra.laToaDoVatly(toaDo)) {
                    throw new NgoaiLeDuLieuLoi(rawLine);
                }

                return toaDo;
            }

            // Neu file khong co GPS thi bao loi de ben DieuPhoiDongBo noi suy
            throw new NgoaiLeDuLieuLoi("Khong co cot GPS trong CSV");

        } catch (NgoaiLeDuLieuLoi e) {

            throw e;

        } catch (Exception e) {

            throw new NgoaiLeDuLieuLoi(rawLine);
        }
    }

    // Kiem tra dong CSV co GPS khong
    public boolean coGPS(String rawLine) {

        if (rawLine == null || rawLine.trim().isEmpty()) {
            return false;
        }

        String[] parts = rawLine.split(kyTuPhanCach, -1);

        // File co GPS khi co it nhat 15 cot
        if (parts.length < 15) {
            return false;
        }

        try {

            Double.parseDouble(parts[parts.length - 2].trim());
            Double.parseDouble(parts[parts.length - 1].trim());

            return true;

        } catch (Exception e) {

            return false;
        }
    }

    // Trich xuat ID con vat
    public String trichXuatIDConVat(String rawLine) throws NgoaiLeDuLieuLoi {

        if (rawLine == null || rawLine.trim().isEmpty()) {
            throw new NgoaiLeDuLieuLoi("Dong du lieu rong");
        }

        String[] parts = rawLine.split(kyTuPhanCach, -1);

        if (parts.length >= 12 && !parts[11].trim().isEmpty()) {
            return parts[11].trim();
        }

        return "ANIMAL_001";
    }

    // Trich xuat thoi diem su kien
    public long trichXuatThoiDiemSuKien(String rawLine) throws NgoaiLeDuLieuLoi {

        if (rawLine == null || rawLine.trim().isEmpty()) {
            throw new NgoaiLeDuLieuLoi("Dong du lieu rong");
        }

        String[] parts = rawLine.split(kyTuPhanCach, -1);

        if (parts.length < 3) {
            throw new NgoaiLeDuLieuLoi(rawLine);
        }

        return chuyenTimestampSangMillis(parts[2].trim());
    }

    // Chuyen timestamp dang yyyy-MM-dd HH:mm:ss.SSS sang milliseconds
    private long chuyenTimestampSangMillis(String timestamp) throws NgoaiLeDuLieuLoi {

        try {

            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

            LocalDateTime localDateTime =
                    LocalDateTime.parse(timestamp, formatter);

            return localDateTime
                    .atZone(ZoneId.systemDefault())
                    .toInstant()
                    .toEpochMilli();

        } catch (Exception e) {

            throw new NgoaiLeDuLieuLoi(timestamp);
        }
    }
}