package module3_GiamSatVaQuanLyNguong;

import module3_GiamSatVaQuanLyNguong.In.*;
import module3_GiamSatVaQuanLyNguong.NhanHanhVi.BoXuLyNguong;
import module3_GiamSatVaQuanLyNguong.NhanVung.BoXuLyNguongVung;
import module3_GiamSatVaQuanLyNguong.CongHuongRuiRo.MaTranRuiRo;
import module3_GiamSatVaQuanLyNguong.Out.BoXuatKetQua;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Lớp Test chạy thử nghiệm toàn bộ Pipeline của Module 3.
 * Nhiệm vụ: Đọc kết quả từ M1, M2, M4 -> Ghép dữ liệu (Join) -> Đánh giá qua Bộ Điều Phối -> Xuất file.
 */
public class TestMainModule3 {

    public static void main(String[] args) throws IOException {
        
        // =====================================================================
        // CHUẨN BỊ: ĐƯỜNG DẪN VÀ KHỞI TẠO THƯ MỤC
        // =====================================================================
        // Lấy đường dẫn gốc của thư mục dự án hiện tại
        String rootPath = System.getProperty("user.dir"); 

        // Gom cấu hình thư mục Data/Output tập trung, tránh khai báo rải rác
        String thuMucData = rootPath + File.separator + "Data" + File.separator + "Output";
        
        // Định nghĩa chính xác đường dẫn đến các file kết quả của các Module trước
        String pathM1  = thuMucData + File.separator + "KetQuaModule1.txt";
        String pathM2  = thuMucData + File.separator + "KetQuaModule2.txt";
        String pathM4  = thuMucData + File.separator + "KetQuaModule4.txt";
        String pathOut = thuMucData + File.separator + "KetQuaModule3"; // Tiền tố file đầu ra của M3

        System.out.println("====== START PIPELINE MODULE 3 ======");

        // Đảm bảo chắc chắn thư mục chứa dữ liệu Output tồn tại trước khi đọc/ghi file
        File directory = new File(thuMucData);
        if (!directory.exists()) {
            directory.mkdirs(); 
            System.out.println("-> Da tao moi thu muc: " + thuMucData);
        }

        // =====================================================================
        // BƯỚC 1 & 2: TẢI DỮ LIỆU CÁC MODULE VÀO BỘ NHỚ (RAM)
        // =====================================================================
        // Khởi tạo các bộ đọc file tương ứng
        BoDocKetQuaModule1 boDocM1 = new BoDocKetQuaModule1();
        BoDocKetQuaModule2 boDocM2 = new BoDocKetQuaModule2();
        BoDocKetQuaModule4 boDocM4 = new BoDocKetQuaModule4(); // Đã được cập nhật để đọc dạng khối log

        // Tiến hành parse dữ liệu từ file vật lý thành danh sách Object
        List<BanGhiModule1> dsM1 = boDocM1.docFile(pathM1);
        List<BanGhiModule2> dsM2 = boDocM2.docFile(pathM2);
        List<BanGhiModule4> dsM4 = boDocM4.docFile(pathM4);

        // Chuyển danh sách M1 và M2 sang dạng cấu trúc Map (Key là ID con vật) 
        // Mục đích: Để khi duyệt M4, ta có thể tìm kiếm M1, M2 với độ phức tạp O(1) thay vì chạy vòng lặp lồng nhau
        Map<String, BanGhiModule1> mapM1 = new HashMap<>();
        for (BanGhiModule1 b : dsM1) mapM1.put(b.layIdConVat(), b);

        Map<String, BanGhiModule2> mapM2 = new HashMap<>();
        for (BanGhiModule2 b : dsM2) mapM2.put(b.layIdConVat(), b);

        System.out.println("[1] Đã tải dữ liệu M1, M2 vào Map bộ nhớ.");
        System.out.println("[2] Đã tải dữ liệu M4 vào danh sách bộ nhớ.");

        // =====================================================================
        // BƯỚC 3: GHÉP DỮ LIỆU (JOIN TRÊN KHÓA CHÍNH: ID CON VẬT)
        // =====================================================================
        List<BanGhiSinhHoc> danhSachBanGhiHopNhat = new ArrayList<>();
        
        System.out.println("DEBUG: Dang bat dau join dữ liệu...");
        System.out.println("DEBUG: -> Tong số bản ghi từ Module 4: " + dsM4.size());
        System.out.println("DEBUG: -> Kích thước bộ đệm Map M1 : " + mapM1.size());
        System.out.println("DEBUG: -> Kích thước bộ đệm Map M2 : " + mapM2.size());

        // CHỈ DÙNG 1 VÒNG LẶP DUY NHẤT: Lấy danh sách M4 làm gốc để tiến hành bắt cặp (Join)
        for (BanGhiModule4 bg4 : dsM4) {
            String id = bg4.layIdConVat();
            BanGhiModule1 m1 = mapM1.get(id);
            BanGhiModule2 m2 = mapM2.get(id);

            // Trường hợp 1: Dữ liệu toàn vẹn (Tìm thấy đủ thông tin đi kèm ở cả M1 lẫn M2)
            if (m1 != null && m2 != null) {
                // Tạo gói tin tổng hợp BanGhiSinhHoc - tích hợp dữ liệu không gian (M4) + hành vi (M2) + timestamp gốc (M1)
            	BanGhiSinhHoc goiTin = new BanGhiSinhHoc(
                        id,
                        m2.layTrangThai(),
                        bg4.layNhanVung(),
                        bg4.layViDo(),
                        bg4.layKinhDo(),
                        m1.layThoiGianSuKien(),
                        bg4.layIdVung(),
                        bg4.layLoaiVung(),
                        bg4.layPhanTramGiaoThoa()
                );
                danhSachBanGhiHopNhat.add(goiTin);
            } 
            // Trường hợp 2: Lỗi bất đồng bộ dữ liệu giữa các module (Thiếu m1 hoặc m2)
            else {
                System.out.println("DEBUG: Khong join duoc ID: " + id + 
                                   " | M1 co khong? " + (m1 != null) + 
                                   " | M2 co khong? " + (m2 != null));
            }
        }
        System.out.println("[3] Đã ghép thành công " + danhSachBanGhiHopNhat.size() + " bản ghi toàn vẹn.");

        // =====================================================================
        // BƯỚC 4: CHẠY BỘ ĐIỀU PHỐI TRUNG TÂM (ĐÁNH GIÁ RỦI RO)
        // =====================================================================
        // Khởi tạo các lõi nghiệp vụ cốt lõi của Module 3
        BoXuLyNguong boXuLyHanhVi   = new BoXuLyNguong();
        BoXuLyNguongVung boXuLyVung = new BoXuLyNguongVung();
        MaTranRuiRo maTran          = new MaTranRuiRo();
        
        // Đưa các lõi vào Bộ điều phối trung tâm (Áp dụng cấu trúc Facade mẫu thiết kế)
        BoDieuPhoiTrungTam dieuPhoi = new BoDieuPhoiTrungTam(boXuLyHanhVi, boXuLyVung, maTran);

        // Duyệt từng gói tin đã hợp nhất để đưa qua ma trận bộ lọc rủi ro
        List<KetQuaDanhGia> danhSachKetQua = new ArrayList<>();
        for (BanGhiSinhHoc g : danhSachBanGhiHopNhat) {
            danhSachKetQua.add(dieuPhoi.xuLyGoiTin(g));
        }
        System.out.println("[4] Đã hoàn thành xử lý luồng đánh giá rủi ro trung tâm.");

        // =====================================================================
        // BƯỚC 5: XUẤT KẾT QUẢ CUỐI CÙNG RA FILE
        // =====================================================================
        BoXuatKetQua boXuat = new BoXuatKetQua(pathOut);
        
        // Ghi đồng thời dữ liệu đầu vào và kết luận rủi ro ra file lưu trữ của Module 3
        boXuat.xuatTatCa(danhSachBanGhiHopNhat, danhSachKetQua);
        
        System.out.println("[5] HOÀN THÀNH PIPELINE. Dữ liệu đầu ra tại: " + pathOut);
        System.out.println("====== END PIPELINE MODULE 3 ======");
    }
}