package module2;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        String inputFile = "Data/Output/KetQuaModule1.csv";

        String outputTXT = "Data/Output/KetQuaModule2.txt";
        String outputCSV = "Data/Output/KetQuaModule2.csv";

        DocKetQuaModule1 boDoc = new DocKetQuaModule1();

        List<BangGhiSinhHoc> danhSach =
                boDoc.docFile(inputFile);

        System.out.println("===== MODULE 2 - PHAN TICH HANH VI =====");
        System.out.println("So ban ghi doc tu Module 1: "
                + danhSach.size());
        System.out.println();

        ThuVienDinhMucSinhHoc thuVien =
                new ThuVienDinhMucSinhHoc();

        Map<String, BoPhanTichCaThe> boPhanTichTheoCon =
                new HashMap<>();

        new File("Data/Output").mkdirs();

        try (

                PrintWriter txt =
                        new PrintWriter(outputTXT);

                PrintWriter csv =
                        new PrintWriter(outputCSV)

        ) {

            csv.println(
                    "idConVat,trangThai,ODBA,tyLeNangLuong,t1,t2,doTreModule2");

            txt.println(
                    "========================================");
            txt.println(
                    "      KET QUA MODULE 2");
            txt.println(
                    "========================================");
            txt.println();

            for (BangGhiSinhHoc banGhi : danhSach) {

                String idConVat =
                        banGhi.getIdConVat();

                if (!boPhanTichTheoCon.containsKey(idConVat)) {

                    // Cau hinh loai dong vat
                    Loai loai = new Loai(
                            "DONG_VAT_HOANG_DA",
                            0.5,
                            2.0,

                            // He so nghi
                            0.001,

                            // He so di chuyen
                            0.002,

                            // He so bay / van dong manh
                            0.004
                    );

                    DongVat dongVat =
                            new DongVat(
                                    idConVat,
                                    0,
                                    loai
                            );

                    BoPhanTichCaThe boPhanTich =
                            new BoPhanTichCaThe(
                                    dongVat
                            );

                    boPhanTichTheoCon.put(
                            idConVat,
                            boPhanTich
                    );
                }

                BoPhanTichCaThe boPhanTich =
                        boPhanTichTheoCon.get(
                                idConVat
                        );

                boPhanTich.tiepNhanDuLieu(
                        banGhi
                );

                KetQuaHanhVi ketQua =
                        boPhanTich.thucHienPhanTich(
                                thuVien
                        );

                // Console
                System.out.println(
                        ketQua
                );

                // TXT
                txt.println(
                        ketQua
                );

                // CSV
                csv.println(
                        ketQua.toCSV()
                );
            }

            txt.println(
                    "========================================");
            txt.println(
                    "MODULE 2 KET THUC");
            txt.println(
                    "========================================");

            System.out.println();
            System.out.println(
                    "Da xuat file TXT: "
                            + outputTXT);

            System.out.println(
                    "Da xuat file CSV: "
                            + outputCSV);

        } catch (Exception e) {

            System.out.println(
                    "Loi ghi file ket qua: "
                            + e.getMessage());
        }

        System.out.println();
        System.out.println(
                "===== KET THUC MODULE 2 =====");
    }
}