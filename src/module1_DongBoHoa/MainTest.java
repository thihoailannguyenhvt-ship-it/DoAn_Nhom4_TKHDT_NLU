package module1_DongBoHoa;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Lop chay thu Module 1
public class MainTest {

    // Ham main la diem bat dau chuong trinh
    public static void main(String[] args) {

        // Duong dan file dau vao
        String path = "Data/Input/Wildlife_Professional_Module2_Fixed.csv";

        // Danh sach luu ket qua dau ra
        List<BanGhiSinhHoc> ketQua = new ArrayList<>();

        // Tao bo kiem tra du lieu
        BoKiemTraDuLieu boKiemTra = new BoKiemTraDuLieu();

        // Tao bo chuyen doi du lieu
        BoChuyenDoiDuLieu boChuyenDoi = new BoChuyenDoiDuLieu("CSV", ";", boKiemTra);

        // Tao bo noi suy GPS
        BoNoiSuyGPS boNoiSuy = new BoNoiSuyGPS();

        // Tao quan ly thoi gian
        QuanLyThoiGian quanLyThoiGian = new QuanLyThoiGian();

        // Tao cau hinh dong bo
        CauHinhDongBo cauHinh = new CauHinhDongBo(5000, 10000);

        // Tao doi tuong dieu phoi dong bo
        DieuPhoiDongBo module1 = new DieuPhoiDongBo(
                boChuyenDoi,
                boKiemTra,
                boNoiSuy,
                quanLyThoiGian,
                cauHinh
        );

        // Doc file CSV
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            // Bien luu tung dong doc duoc
            String line;

            // Doc tung dong trong file
            while ((line = br.readLine()) != null) {

                // Bo qua dong rong
                if (line.trim().isEmpty()) {
                    continue;
                }

                // Chi xu ly dong du lieu that
                if (!line.contains(";") || !line.contains("acceleration")) {
                    continue;
                }

                try {

                    // Goi module 1 xu ly tung dong du lieu
                    BanGhiSinhHoc banGhi = module1.thucHienDongBo(line);

                    // Neu ban ghi hop le thi them vao ket qua
                    if (banGhi != null) {
                        ketQua.add(banGhi);
                    }

                } catch (Exception e) {

                    // Neu dong du lieu loi thi bo qua
                    continue;
                }
            }

        } catch (Exception e) {

            // Bao loi neu khong doc duoc file
            System.out.println("Loi doc file CSV: " + e.getMessage());
        }

        // In ket qua tong quan
        System.out.println("\n===== KET QUA MODULE 1 =====");
        System.out.println("Tong so ban ghi dong bo thanh cong : " + ketQua.size());

        // In 20 ban ghi dau tien
       

        System.out.println("\n===== 20 BAN GHI DAU =====");

        for (int i = 0; i < Math.min(20, ketQua.size()); i++) {
            System.out.println(ketQua.get(i));
        }

        // Tao map thong ke theo con vat
        Map<String, Integer> thongKe = new HashMap<>();

        // Duyet danh sach ket qua de thong ke
        for (BanGhiSinhHoc banGhi : ketQua) {

            // Lay ma con vat
            String idConVat = banGhi.getIdConVat();

            // Tang so ban ghi cua con vat
            thongKe.put(idConVat, thongKe.getOrDefault(idConVat, 0) + 1);
        }

        // In bang thong ke
        System.out.println();
        System.out.println("\n===== THONG KE THEO CON VAT =====");
        for (String idConVat : thongKe.keySet()) {

            System.out.printf("%-30s : %5d ban ghi%n",
                    idConVat,
                    thongKe.get(idConVat));
        }

        // Tao thu muc output neu chua co
        new File("Data/Output").mkdirs();

        // Ghi ket qua ra file TXT
        try (PrintWriter out = new PrintWriter("Data/Output/KetQuaModule1.txt")) {

            out.println("====================================================");
            out.println("                  KET QUA MODULE 1");
        
            out.println("Tong so ban ghi dong bo thanh cong : " + ketQua.size());
            out.println();

            for (BanGhiSinhHoc banGhi : ketQua) {

                out.println(banGhi);
            }

            System.out.println();
            System.out.println("Da xuat file TXT: Data/Output/KetQuaModule1.txt");

        } catch (Exception e) {

            System.out.println("Loi ghi file TXT: " + e.getMessage());
        }

        // Ghi ket qua ra file CSV
        try (PrintWriter out = new PrintWriter("Data/Output/KetQuaModule1.csv")) {

            out.println("idConVat,viDo,kinhDo,trucX,trucY,trucZ,mocThoiGianSuKien,mocThoiGianHeThong,trangThai,t1,doTre");

            for (BanGhiSinhHoc banGhi : ketQua) {
                out.println(banGhi.toCSV());
            }

            System.out.println("Da xuat file CSV: Data/Output/KetQuaModule1.csv");

        } catch (Exception e) {

            System.out.println("Loi ghi file CSV: " + e.getMessage());
        }

        // In thong bao ket thuc
        System.out.println();
        
        System.out.println("              MODULE 1 KET THUC            ");
        System.out.println("====================================================");
    }
}