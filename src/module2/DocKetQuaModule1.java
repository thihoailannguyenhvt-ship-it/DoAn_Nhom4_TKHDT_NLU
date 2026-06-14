package module2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

// Lop doc file KetQuaModule1.csv de lam dau vao cho Module 2
public class DocKetQuaModule1 {

    // Doc file CSV cua Module 1
    public List<BangGhiSinhHoc> docFile(String path) {

        List<BangGhiSinhHoc> danhSach = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            String line;
            boolean boQuaHeader = true;

            while ((line = br.readLine()) != null) {

                if (boQuaHeader) {
                    boQuaHeader = false;
                    continue;
                }

                if (line.trim().isEmpty()) {
                    continue;
                }

                try {
                    BangGhiSinhHoc banGhi = chuyenDongThanhBanGhi(line);

                    if (banGhi != null) {
                        danhSach.add(banGhi);
                    }

                } catch (Exception e) {
                    // Bo qua dong loi de chuong trinh khong bi dung
                    continue;
                }
            }

        } catch (Exception e) {
            System.out.println("Loi doc file Module 1: " + e.getMessage());
        }

        return danhSach;
    }

    // Chuyen 1 dong CSV thanh BangGhiSinhHoc cua Module 2
    private BangGhiSinhHoc chuyenDongThanhBanGhi(String line) {

        List<String> parts = tachCSV(line);

        String idConVat = parts.get(0).trim();

        double viDo = Double.parseDouble(parts.get(1).trim());
        double kinhDo = Double.parseDouble(parts.get(2).trim());

        String viTri = viDo + "," + kinhDo;

        double x = tinhTrungBinh(parts.get(3));
        double y = tinhTrungBinh(parts.get(4));
        double z = tinhTrungBinh(parts.get(5));

        long mocThoiGianSuKien = chuyenThoiGian(parts.get(6).trim());
        long mocThoiGianHeThong = chuyenThoiGian(parts.get(7).trim());

        BangGhiGiaToc giaToc = new BangGhiGiaToc(
                mocThoiGianSuKien,
                x,
                y,
                z
        );

        return new BangGhiSinhHoc(
                idConVat,
                viTri,
                mocThoiGianSuKien,
                mocThoiGianHeThong,
                giaToc
        );
    }

    // Tach CSV nhung van giu noi dung trong dau ngoac kep
    private List<String> tachCSV(String line) {

        List<String> ketQua = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        boolean trongNgoacKep = false;

        for (int i = 0; i < line.length(); i++) {

            char c = line.charAt(i);

            if (c == '"') {
                trongNgoacKep = !trongNgoacKep;
                continue;
            }

            if (c == ',' && !trongNgoacKep) {
                ketQua.add(sb.toString());
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }

        ketQua.add(sb.toString());

        return ketQua;
    }

    // Tinh trung binh tu chuoi dang [1.09, 1.17, 1.20]
    private double tinhTrungBinh(String raw) {

        raw = raw.replace("[", "")
                .replace("]", "")
                .replace("\"", "")
                .trim();

        String[] values = raw.split(",");

        double tong = 0;
        int dem = 0;

        for (String v : values) {

            if (!v.trim().isEmpty()) {
                tong += Double.parseDouble(v.trim());
                dem++;
            }
        }

        if (dem == 0) {
            return 0;
        }

        return tong / dem;
    }

    // Chuyen chuoi ngay gio thanh milliseconds
    private long chuyenThoiGian(String time) {

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime localDateTime =
                LocalDateTime.parse(time, formatter);

        return localDateTime
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();
    }
}