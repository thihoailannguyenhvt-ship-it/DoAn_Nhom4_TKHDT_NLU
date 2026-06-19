package module1_DongBoHoa;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class BoChuyenDoiDuLieu {

    
    private final String dinhDangNguon;
    private final String kyTuPhanCach;

   
    public BoChuyenDoiDuLieu(String dinhDangNguon, String kyTuPhanCach) {
        if (kyTuPhanCach == null || kyTuPhanCach.isEmpty()) {
            throw new IllegalArgumentException("Loi kieu: Ky tu phan cach dong du lieu khong duoc de trong.");
        }
        this.dinhDangNguon = dinhDangNguon;
        this.kyTuPhanCach = kyTuPhanCach;
    }

  
    public BanGhiGiaTocBD chuyenDoiGiaToc(String rawLine) throws NgoaiLeDuLieuLoi {
        if (rawLine == null || rawLine.trim().isEmpty()) {
            throw new NgoaiLeDuLieuLoi("Loi dong bo: Dong du lieu thô truyen vao bi rong.");
        }

        String[] parts = rawLine.split(kyTuPhanCach, -1);
        if (parts.length < 12) {
            throw new NgoaiLeDuLieuLoi("Loi cau truc: Dong du lieu thieu cot thuoc tinh bat buoc: " + rawLine);
        }

        try {
            long thoiDiemSuKien = chuyenTimestampSangMillis(parts[2].trim());
            String idConVat = parts[11].trim();
            if (!BoKiemTraDuLieu.laIdHopLe(idConVat)) {
                idConVat = "ANIMAL_001";
            }

            String rawAcc = parts[5].trim();
            if (rawAcc.isEmpty()) {
                throw new NgoaiLeDuLieuLoi("Loi cam bien: Chuoi gia toc truc X,Y,Z trong: " + rawLine);
            }

            String[] values = rawAcc.split("\\s+");
            List<Double> listX = new ArrayList<>();
            List<Double> listY = new ArrayList<>();
            List<Double> listZ = new ArrayList<>();

            for (int i = 0; i + 2 < values.length; i += 3) {
                listX.add(Double.parseDouble(values[i]));
                listY.add(Double.parseDouble(values[i + 1]));
                listZ.add(Double.parseDouble(values[i + 2]));
            }

            if (listX.isEmpty()) {
                throw new NgoaiLeDuLieuLoi("Loi phan tich: Khong trich xuat duoc mau gia toc nao.");
            }

            
            double[] arrX = listX.stream().mapToDouble(Double::doubleValue).toArray();
            double[] arrY = listY.stream().mapToDouble(Double::doubleValue).toArray();
            double[] arrZ = listZ.stream().mapToDouble(Double::doubleValue).toArray();

            
            return new BanGhiGiaTocBD(idConVat, thoiDiemSuKien, arrX, arrY, arrZ);

        } catch (NgoaiLeDuLieuLoi e) {
            throw e;
        } catch (Exception e) {
            throw new NgoaiLeDuLieuLoi("Loi ngoai le parse du lieu dong: " + rawLine);
        }
    }

   
    public ToaDo chuyenDoiToaDo(String rawLine) throws NgoaiLeDuLieuLoi {
        if (rawLine == null || rawLine.trim().isEmpty()) {
            throw new NgoaiLeDuLieuLoi("Loi: Chuoi toa do doc lap bi rong.");
        }

        String[] parts = rawLine.split(kyTuPhanCach, -1);

        try {
            double viDo = Double.parseDouble(parts[0].trim());
            double kinhDo = Double.parseDouble(parts[1].trim());

           
            if (!BoKiemTraDuLieu.laToaDoHopLe(viDo, kinhDo)) {
                throw new NgoaiLeDuLieuLoi("Toa do vuot bien vat ly trai dat: " + rawLine);
            }

            return new ToaDo(viDo, kinhDo);
        } catch (Exception e) {
            throw new NgoaiLeDuLieuLoi("Loi định dang chuoi toa do thô: " + rawLine);
        }
    }

  
    public ToaDo chuyenDoiToaDoTuDongCSV(String rawLine) throws NgoaiLeDuLieuLoi {
        if (rawLine == null || rawLine.trim().isEmpty()) {
            throw new NgoaiLeDuLieuLoi("Loi dong du lieu rong.");
        }

        String[] parts = rawLine.split(kyTuPhanCach, -1);

        try {
            
            if (parts.length >= 14) {
                
                double viDo = Double.parseDouble(parts[parts.length - 2].trim());
                double kinhDo = Double.parseDouble(parts[parts.length - 1].trim());

                if (!BoKiemTraDuLieu.laToaDoHopLe(viDo, kinhDo)) {
                    throw new NgoaiLeDuLieuLoi("Toa do trong file CSV sai vi tri vat ly.");
                }

                return new ToaDo(viDo, kinhDo);
            }
            throw new NgoaiLeDuLieuLoi("Khong co du cot GPS mo rong trong file CSV.");

        } catch (NgoaiLeDuLieuLoi e) {
            throw e;
        } catch (Exception e) {
            throw new NgoaiLeDuLieuLoi("Loi trich xuat toa do tu dong CSV: " + rawLine);
        }
    }

   
    public boolean coGPS(String rawLine) {
        if (rawLine == null || rawLine.trim().isEmpty()) {
            return false;
        }

        String[] parts = rawLine.split(kyTuPhanCach, -1);
        if (parts.length < 14) {
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

    
    public String trichXuatIDConVat(String rawLine) throws NgoaiLeDuLieuLoi {
        if (rawLine == null || rawLine.trim().isEmpty()) {
            throw new NgoaiLeDuLieuLoi("Dong thô rong.");
        }

        String[] parts = rawLine.split(kyTuPhanCach, -1);
        if (parts.length >= 12 && !parts[11].trim().isEmpty()) {
            return parts[11].trim();
        }
        return "ANIMAL_001";
    }

 
    public long trichXuatThoiDiemSuKien(String rawLine) throws NgoaiLeDuLieuLoi {
        if (rawLine == null || rawLine.trim().isEmpty()) {
            throw new NgoaiLeDuLieuLoi("Dong thô de lay thoi gian bi rong.");
        }

        String[] parts = rawLine.split(kyTuPhanCach, -1);
        if (parts.length < 3) {
            throw new NgoaiLeDuLieuLoi("Thieu truong thoi gian su kien: " + rawLine);
        }

        return chuyenTimestampSangMillis(parts[2].trim());
    }

   
    private long chuyenTimestampSangMillis(String timestamp) throws NgoaiLeDuLieuLoi {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
            LocalDateTime localDateTime = LocalDateTime.parse(timestamp, formatter);
            
            return localDateTime
                    .atZone(ZoneId.systemDefault())
                    .toInstant()
                    .toEpochMilli();
        } catch (Exception e) {
            throw new NgoaiLeDuLieuLoi("Sai dinh dang thoi gian chuan: " + timestamp);
        }
    }

    
    public static class NgoaiLeDuLieuLoi extends Exception {
        public NgoaiLeDuLieuLoi(String thongBao) {
            super(thongBao);
        }
    }
}