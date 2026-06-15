package module4_GeofenceDongVaNguCanhKhongGian;

import java.io.*;
import java.util.*;

public class Test {

    static class DuLieuM1 {
        String idConVat;
        double viDo;
        double kinhDo;

        DuLieuM1(String idConVat, double viDo, double kinhDo) {
            this.idConVat = idConVat;
            this.viDo = viDo;
            this.kinhDo = kinhDo;
        }
    }

    static class DuLieuM2 {
        String idConVat;
        String trangThai;
        double odba;
        double tyLeNangLuong;

        DuLieuM2(String idConVat, String trangThai, double odba, double tyLeNangLuong) {
            this.idConVat = idConVat;
            this.trangThai = trangThai;
            this.odba = odba;
            this.tyLeNangLuong = tyLeNangLuong;
        }
    }

    public static void main(String[] args) {

        String fileM1 = "Data/Output/KetQuaModule1.csv";
        String fileM2 = "Data/Output/KetQuaModule2.csv";

        String outputCsv = "Data/Output/KetQuaModule4.csv";
        String outputTxt = "Data/Output/KetQuaModule4.txt";

        Map<String, Queue<DuLieuM1>> duLieuModule1 = docModule1(fileM1);
        List<DuLieuM2> duLieuModule2 = docModule2(fileM2);

        if (duLieuModule1.isEmpty()) {
            System.out.println("Khong co du lieu Module 1.");
            return;
        }

        if (duLieuModule2.isEmpty()) {
            System.out.println("Khong co du lieu Module 2.");
            return;
        }

        new File("Data/Output").mkdirs();

        KhoLuuTruKhongGian kho = new KhoLuuTruKhongGian();

        kho.themVung(VungFactory.taoKhuDanCu(
                new Diem(107.442000, 11.435700),
                0.006
        ));

        kho.themVung(VungFactory.taoTramQuanSat(
                new Diem(107.460700, 11.454240),
                0.006
        ));

        kho.themVung(VungFactory.taoTuyenDuongDuLich(
                new Diem(107.429500, 11.423000),
                0.005
        ));

        BoAnhXaHeSoK boAnhXa = new BoAnhXaHeSoK();
        BoTinhToanBanKinh boTinhBanKinh = new BoTinhToanBanKinh();
        BoPhanLoaiNguyCo boPhanLoai = new BoPhanLoaiNguyCo();

        TrinhDieuPhoiTrungTam trungTam =
                TrinhDieuPhoiTrungTam.getInstance(
                        kho,
                        boAnhXa,
                        boTinhBanKinh,
                        boPhanLoai
                );

        System.out.println("===== MODULE 4 - GEOFENCE DONG VA NGU CANH KHONG GIAN =====");
        System.out.println("So ca the lay tu Module 1 : " + duLieuModule1.size());
        System.out.println("So ban ghi doc tu Module 2: " + duLieuModule2.size());
        System.out.println();

        try (
                PrintWriter csv = new PrintWriter(outputCsv);
                PrintWriter txt = new PrintWriter(outputTxt)
        ) {

            csv.println("idConVat,viDo,kinhDo,trangThaiHanhVi,ODBA,tyLeNangLuong,heSoK,banKinhGeofence,loaiVung,phanTramGiaoThoa,mucDoNguyCoKhongGian");

            txt.println("========================================");
            txt.println("          KET QUA MODULE 4");
            txt.println("========================================");
            txt.println();

            int soBanGhiXuat = 0;

            for (DuLieuM2 m2 : duLieuModule2) {

                Queue<DuLieuM1> hangDoi = duLieuModule1.get(m2.idConVat);

                if (hangDoi == null || hangDoi.isEmpty()) {
                    continue;
                }

                DuLieuM1 m1 = hangDoi.poll();

                boAnhXa.capNhatK(m2.trangThai);

                double heSoK = boAnhXa.layK();
                double banKinh = trungTam.tinhBanKinhR(heSoK, m2.odba);

                Diem tamDongVat = new Diem(m1.kinhDo, m1.viDo);

                GeofenceDong geofence = new GeofenceDong(
                        tamDongVat,
                        banKinh,
                        taoDaGiacVuong(tamDongVat, banKinh)
                );

                double maxGiaoThoa = 0;
                String loaiVungMax = "KHONG_GIAO_THOA";

                for (VungKhongGian vung : trungTam.truyVanKhongGian()) {

                    double phanTram = trungTam.quetGiaoThoa(geofence, vung);

                    if (phanTram > maxGiaoThoa) {
                        maxGiaoThoa = phanTram;
                        loaiVungMax = vung.getClass().getSimpleName();
                    }
                }

                MucDoNguyCo mucDo =
                        boPhanLoai.danhGia(maxGiaoThoa);

                System.out.println(
                        m1.idConVat
                                + " | ViTri=(" + m1.viDo + ", " + m1.kinhDo + ")"
                                + " | TrangThai=" + m2.trangThai
                                + " | ODBA=" + lamTron(m2.odba)
                                + " | K=" + lamTron(heSoK)
                                + " | R=" + lamTron(banKinh)
                                + " | Vung=" + loaiVungMax
                                + " | GiaoThoa=" + lamTron(maxGiaoThoa) + "%"
                                + " | NguyCoKG=" + mucDo
                );

                csv.println(
                        m1.idConVat + ","
                                + m1.viDo + ","
                                + m1.kinhDo + ","
                                + m2.trangThai + ","
                                + m2.odba + ","
                                + m2.tyLeNangLuong + ","
                                + heSoK + ","
                                + banKinh + ","
                                + loaiVungMax + ","
                                + maxGiaoThoa + ","
                                + mucDo
                );

                txt.println("ID con vat              : " + m1.idConVat);
                txt.println("Vi tri                 : (" + m1.viDo + ", " + m1.kinhDo + ")");
                txt.println("Trang thai hanh vi     : " + m2.trangThai);
                txt.println("ODBA                   : " + m2.odba);
                txt.println("Ty le nang luong       : " + m2.tyLeNangLuong);
                txt.println("He so K                : " + heSoK);
                txt.println("Ban kinh Geofence      : " + banKinh);
                txt.println("Vung giao thoa lon nhat: " + loaiVungMax);
                txt.println("Phan tram giao thoa    : " + maxGiaoThoa + "%");
                txt.println("Nguy co khong gian     : " + mucDo);
                txt.println("----------------------------------------");

                soBanGhiXuat++;
            }

            txt.println();
            txt.println("Tong so ban ghi xuat Module 4: " + soBanGhiXuat);
            txt.println("========================================");
            txt.println("MODULE 4 KET THUC");
            txt.println("========================================");

            System.out.println();
            System.out.println("Tong so ban ghi xuat Module 4: " + soBanGhiXuat);
            System.out.println("Da xuat file CSV: " + outputCsv);
            System.out.println("Da xuat file TXT: " + outputTxt);
            System.out.println("===== KET THUC MODULE 4 =====");

        } catch (Exception e) {
            System.out.println("Loi ghi file Module 4: " + e.getMessage());
        }
    }

    private static Map<String, Queue<DuLieuM1>> docModule1(String path) {

        Map<String, Queue<DuLieuM1>> map = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            String header = br.readLine();

            if (header == null) {
                return map;
            }

            char delimiter = header.contains(";") ? ';' : ',';

            String line;

            while ((line = br.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                List<String> p = tachCSV(line, delimiter);

                String id = p.get(0).trim();
                double viDo = Double.parseDouble(p.get(1).trim());
                double kinhDo = Double.parseDouble(p.get(2).trim());

                map.putIfAbsent(id, new LinkedList<DuLieuM1>());
                map.get(id).add(new DuLieuM1(id, viDo, kinhDo));
            }

        } catch (Exception e) {
            System.out.println("Loi doc Module 1: " + e.getMessage());
        }

        return map;
    }

    private static List<DuLieuM2> docModule2(String path) {

        List<DuLieuM2> ds = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            String header = br.readLine();

            if (header == null) {
                return ds;
            }

            char delimiter = header.contains(";") ? ';' : ',';

            String line;

            while ((line = br.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                List<String> p = tachCSV(line, delimiter);

                String id = p.get(0).trim();
                String trangThai = p.get(1).trim();

                double odba = Double.parseDouble(p.get(2).trim());
                double tyLe = Double.parseDouble(p.get(3).trim());

                ds.add(new DuLieuM2(id, trangThai, odba, tyLe));
            }

        } catch (Exception e) {
            System.out.println("Loi doc Module 2: " + e.getMessage());
        }

        return ds;
    }

    private static List<Diem> taoDaGiacVuong(Diem tam, double r) {

        List<Diem> ds = new ArrayList<>();

        ds.add(new Diem(tam.getKinhDo() - r, tam.getViDo() - r));
        ds.add(new Diem(tam.getKinhDo() + r, tam.getViDo() - r));
        ds.add(new Diem(tam.getKinhDo() + r, tam.getViDo() + r));
        ds.add(new Diem(tam.getKinhDo() - r, tam.getViDo() + r));

        return ds;
    }

    private static List<String> tachCSV(String line, char delimiter) {

        List<String> ketQua = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        boolean trongNgoacKep = false;

        for (int i = 0; i < line.length(); i++) {

            char c = line.charAt(i);

            if (c == '"') {
                trongNgoacKep = !trongNgoacKep;
                continue;
            }

            if (c == delimiter && !trongNgoacKep) {
                ketQua.add(sb.toString());
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }

        ketQua.add(sb.toString());

        return ketQua;
    }

    private static double lamTron(double value) {
        return Math.round(value * 100000.0) / 100000.0;
    }
}
