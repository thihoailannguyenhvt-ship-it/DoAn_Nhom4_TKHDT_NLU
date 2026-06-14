package module2;

import java.util.LinkedList;
import java.util.Queue;

// Lop tinh toan cac chi so sinh hoc nhu ODBA, variance, movement
public class BoTinhToanSinhHoc {

    // Tinh gia toc tinh
    public double tinhGiaTocTinh(double runningSum, int n) {

        if (n == 0) {
            return 0;
        }

        return runningSum / n;
    }

    // Tinh gia toc dong
    public double tinhGiaTocDong(double giaTocTho, double giaTocTinh) {

        return Math.abs(giaTocTho - giaTocTinh);
    }

    // Tinh ODBA trung binh trong buffer
    public double tinhODBA(Queue<BangGhiSinhHoc> buffer) {

        if (buffer == null || buffer.isEmpty()) {
            return 0;
        }

        double tongODBA = 0;

        for (BangGhiSinhHoc bg : buffer) {
            tongODBA += tinhODBATungBanGhi(bg);
        }

        return tongODBA / buffer.size();
    }

    // Tinh tong ODBA tich luy trong cua so
    public double tinhTongODBA(Queue<BangGhiSinhHoc> buffer) {

        if (buffer == null || buffer.isEmpty()) {
            return 0;
        }

        double tong = 0;

        for (BangGhiSinhHoc bg : buffer) {
            tong += tinhODBATungBanGhi(bg);
        }

        return tong;
    }

    // Tinh ODBA cua tung ban ghi
    public double tinhODBATungBanGhi(BangGhiSinhHoc bg) {

        if (bg == null || bg.getGiaToc() == null) {
            return 0;
        }

        BangGhiGiaToc gt = bg.getGiaToc();

        double trungBinhTinh =
                (gt.getTrucX() + gt.getTrucY() + gt.getTrucZ()) / 3.0;

        double dx = Math.abs(gt.getTrucX() - trungBinhTinh);
        double dy = Math.abs(gt.getTrucY() - trungBinhTinh);
        double dz = Math.abs(gt.getTrucZ() - trungBinhTinh);

        return dx + dy + dz;
    }

    // Tinh phuong sai cua ODBA trong cua so
    public double tinhVarianceODBA(Queue<BangGhiSinhHoc> buffer) {

        if (buffer == null || buffer.size() < 2) {
            return 0;
        }

        Queue<Double> dsODBA = new LinkedList<>();

        double tong = 0;

        for (BangGhiSinhHoc bg : buffer) {
            double odba = tinhODBATungBanGhi(bg);
            dsODBA.add(odba);
            tong += odba;
        }

        double trungBinh = tong / dsODBA.size();

        double tongBinhPhuong = 0;

        for (double odba : dsODBA) {
            tongBinhPhuong += Math.pow(odba - trungBinh, 2);
        }

        return tongBinhPhuong / dsODBA.size();
    }

    // Tinh khoang cach GPS giua 2 ban ghi
    public double tinhKhoangCach(String gps1, String gps2) {

        String[] p1 = gps1.split(",");
        String[] p2 = gps2.split(",");

        double lat1 = Double.parseDouble(p1[0]);
        double lon1 = Double.parseDouble(p1[1]);

        double lat2 = Double.parseDouble(p2[0]);
        double lon2 = Double.parseDouble(p2[1]);

        return Math.sqrt(
                Math.pow(lat2 - lat1, 2)
                        + Math.pow(lon2 - lon1, 2)
        );
    }

    // Tinh ti le nang luong so voi dinh muc loai
    public double tinhTyLeNangLuong(Queue<BangGhiSinhHoc> buffer, Loai loai) {

        if (buffer == null || buffer.isEmpty() || loai == null) {
            return 0;
        }

        int tNghi = 240;
        int tDi = 300;
        int tBay = 180;

        double normal12h = loai.tinhNormal12h(tNghi, tDi, tBay);

        if (normal12h == 0) {
            return 0;
        }

        double eThucTe = tinhTongODBA(buffer);

        return eThucTe / normal12h;
    }

    // Ham cu de tuong thich code cu
    public double tinhRatio(double eTichLuy, double normal12h) {

        if (normal12h == 0) {
            return 0;
        }

        return eTichLuy / normal12h;
    }
}