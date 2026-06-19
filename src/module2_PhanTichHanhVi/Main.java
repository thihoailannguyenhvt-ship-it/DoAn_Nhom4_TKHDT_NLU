package module2_PhanTichHanhVi;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
// Import các class hỗ trợ
import dinhDanh.*; 

public class Main {

    public static void main(String[] args) {
        // 1. Cấu hình đường dẫn
        String inputFile = "Data\\Output\\KetQuaModule1.csv";
        String outputTXT = "Data\\Output\\KetQuaModule2.txt";
        String outputCSV = "Data\\Output\\KetQuaModule2.csv";

        // 2. Nạp dữ liệu qua bộ đọc đã fix lỗi quote/pipe
        DocKetQuaModule1 boDoc = new DocKetQuaModule1();
        List<BangGhiSinhHoc> danhSach = boDoc.docFile(inputFile);

        System.out.println("===== START: MODULE 2 - PHAN TICH HANH VI =====");
        System.out.println("Tổng số bản ghi: " + danhSach.size());
        
        if (danhSach.isEmpty()) {
            System.err.println("LỖI: Không đọc được dữ liệu. Kiểm tra file CSV!");
            return;
        }

        // 3. Khởi tạo tài nguyên
        ThuVienDinhMucSinhHoc thuVien = new ThuVienDinhMucSinhHoc();
        Map<String, BoPhanTichCaThe> boPhanTichTheoCon = new HashMap<>();

        // Đảm bảo thư mục tồn tại
        new File("Data/Output").mkdirs();

        // 4. Bắt đầu xử lý luồng (Pipeline)
        try (PrintWriter txt = new PrintWriter(outputTXT);
             PrintWriter csv = new PrintWriter(outputCSV)) {

            // Header CSV
            csv.println("idConVat,trangThai,ODBA,tyLeNangLuong,t1,t2,doTreModule2");

            txt.println("========================================");
            txt.println("        KET QUA PHAN TICH HANH VI");
            txt.println("========================================");

            for (BangGhiSinhHoc banGhi : danhSach) {
                String id = banGhi.getIdConVat();

                // Nếu chưa có bộ phân tích cho con vật này, tạo mới
                if (!boPhanTichTheoCon.containsKey(id)) {
                    // Mặc định cấu hình loại (có thể load từ file config sau này)
                    Loai loai = new Loai("DONG_VAT_HOANG_DA", 0.5, 2.0, 0.001, 0.002, 0.004);
                    DongVat dongVat = new DongVat(id, 0.0, loai);
                    boPhanTichTheoCon.put(id, new BoPhanTichCaThe(dongVat));
                }

                // Thực hiện pipeline
                BoPhanTichCaThe boPhanTich = boPhanTichTheoCon.get(id);
                boPhanTich.tiepNhanDuLieu(banGhi);
                KetQuaHanhVi ketQua = boPhanTich.thucHienPhanTich(thuVien);

                // Lưu kết quả
                txt.println(ketQua.toString());
                csv.println(ketQua.toCSV());
            }

            System.out.println("Đã xử lý xong. Kiểm tra file tại Data/Output/");

        } catch (Exception e) {
            System.err.println("Lỗi nghiêm trọng trong vòng lặp xử lý: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("===== KET THUC MODULE 2 =====");
    }
}