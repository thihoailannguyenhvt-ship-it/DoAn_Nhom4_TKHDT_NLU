package module1_DongBoHoa;

import dinhDanh.TrangThaiDuLieu;

import java.io.*;
import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;


public class MainTest {

    private static final String DUONG_DAN_INPUT  = "Data\\Input\\Wildlife_Professional.csv";
    private static final String DUONG_DAN_OUTPUT_TXT = "Data\\Output\\KetQuaModule1.txt";
    private static final String DUONG_DAN_OUTPUT_CSV = "Data\\Output\\KetQuaModule1.csv";
    private static final String KY_TU_PHAN_CACH  = ";";

    private static int tongBanGhi     = 0;
    private static int thucTe         = 0;
    private static int noiSuy         = 0;
    private static int loi            = 0;
    private static int khoiDongLanh   = 0;

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════╗");
        System.out.println("║        MODULE 1 - ĐỒNG BỘ HÓA DỮ LIỆU              ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");

        BoChuyenDoiDuLieu   boChuyenDoi     = new BoChuyenDoiDuLieu("CSV", KY_TU_PHAN_CACH);

        QuanLyCauHinh.napCauHinh("Data/Input/dongvat.txt");
        Map<String, BoNoiSuyGPS>   registry        = new HashMap<>();
        // FIX BUG 3: QuanLyTrangThai per-species thay vì global
        Map<String, QuanLyTrangThai> trangThaiMap  = new HashMap<>();

        String[] danhSachLoai = {"CHEO_CHEO", "SON_DUONG", "MANG_LON", "NAI_RUNG", "BO_TOT", "LON_RUNG", "KHI_DUOI_LON"};

        for (String loai : danhSachLoai) {
            QuanLyCauHinh.ThongSo ts = QuanLyCauHinh.get(loai);
            GiaLapGPS gps = new GiaLapGPS(loai, ts.theta, ts.sigma);
            registry.put(loai, new BoNoiSuyGPS(gps));
            trangThaiMap.put(loai, new QuanLyTrangThai());
        }

        List<BanGhiSinhHoc> danhSachKetQua = new ArrayList<>();
        List<String>        danhSachLoi    = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(DUONG_DAN_INPUT), "UTF-8"))) {
            String dong;
            boolean boQuaHeader = false;
            while ((dong = reader.readLine()) != null) {
                dong = dong.trim();
                if (dong.isEmpty() || dong.startsWith("\u0000") || dong.startsWith("ÐÏ")) continue;
                if (!boQuaHeader && dong.contains("event-id")) { boQuaHeader = true; continue; }
                if (!dong.contains(KY_TU_PHAN_CACH)) continue;

                tongBanGhi++;
                BanGhiSinhHoc banGhi = xuLyMotDong(dong, boChuyenDoi, trangThaiMap, registry, danhSachLoi);
                if (banGhi != null) danhSachKetQua.add(banGhi);
            }
        } catch (IOException e) {
            System.err.println("[CRITICAL] Lỗi đọc file: " + e.getMessage());
            return;
        }

        xuatFileTXT(danhSachKetQua, danhSachLoi);
        xuatFileCSV(danhSachKetQua);
        inThongKe(danhSachKetQua.size());
    }

    private static BanGhiSinhHoc xuLyMotDong(
            String rawLine, BoChuyenDoiDuLieu boChuyenDoi,
            Map<String, QuanLyTrangThai> trangThaiMap,
            Map<String, BoNoiSuyGPS> registry, List<String> danhSachLoi) {
        try {
            BanGhiGiaTocBD giaToc = boChuyenDoi.chuyenDoiGiaToc(rawLine);
            if (!BoKiemTraDuLieu.laDuLieuGiaTocHopLe(giaToc.getTrucX(), giaToc.getTrucY(), giaToc.getTrucZ())) {
                loi++; return null;
            }

            // FIX BUG 3: Lấy QuanLyTrangThai theo từng loài
            String tenLoai = trichXuatTenLoai(giaToc.getIdConVat());
            QuanLyTrangThai quanLyTrangThai = trangThaiMap.getOrDefault(
                    tenLoai, trangThaiMap.get("BO_TOT"));

            ToaDo toaDo;
            TrangThaiDuLieu trangThai;

            if (boChuyenDoi.coGPS(rawLine)) {
                toaDo    = boChuyenDoi.chuyenDoiToaDoTuDongCSV(rawLine);
                trangThai = TrangThaiDuLieu.THUC_TE;
                thucTe++;
            } else {
                if (!quanLyTrangThai.daCoDuLieu()) {
                    
                    toaDo    = new ToaDo(11.422179, 107.428679);
                    trangThai = TrangThaiDuLieu.KHOI_DONG_LANH;
                    khoiDongLanh++;
                } else {
                	
                    BoNoiSuyGPS boNoiSuy = registry.getOrDefault(tenLoai, registry.get("BO_TOT"));
                    toaDo    = boNoiSuy.noiSuyToaDoTheoThoiGian(giaToc.getMocThoiGianSuKien());
                    trangThai = TrangThaiDuLieu.GIA_LAP;
                    noiSuy++;
                }
            }

            quanLyTrangThai.luuVet(toaDo, giaToc.getMocThoiGianSuKien());
            long time = QuanLyThoiGian.layThoiGianHeThongHienTai();
            return new BanGhiSinhHoc(giaToc.getIdConVat(), toaDo, giaToc,
                    giaToc.getMocThoiGianSuKien(), time, trangThai, time);
        } catch (Exception e) {
            loi++; return null;
        }
    }

    private static String trichXuatTenLoai(String id) {
        try {
            String ten = id.split("-")[1];
            return ten.replaceAll("([a-z])([A-Z]+)", "$1_$2").toUpperCase();
        } catch (Exception e) { return "BO_TOT"; }
    }

    private static void xuatFileTXT(List<BanGhiSinhHoc> danhSach, List<String> danhSachLoi) {
        new File(DUONG_DAN_OUTPUT_TXT).getParentFile().mkdirs();
        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(DUONG_DAN_OUTPUT_TXT), StandardCharsets.UTF_8))) {
            for (BanGhiSinhHoc bg : danhSach) pw.println(bg.toString());
            System.out.println(" Đã xuất TXT: " + DUONG_DAN_OUTPUT_TXT);
        } catch (IOException e) { System.err.println("[ERROR] Lỗi xuất TXT: " + e.getMessage()); }
    }

    private static void xuatFileCSV(List<BanGhiSinhHoc> danhSach) {
        new File(DUONG_DAN_OUTPUT_CSV).getParentFile().mkdirs();
        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(DUONG_DAN_OUTPUT_CSV), StandardCharsets.UTF_8))) {
            pw.println(BanGhiSinhHoc.CSV_HEADER);
            for (BanGhiSinhHoc bg : danhSach) pw.println(bg.toCSV());
            System.out.println("[OK] Đã xuất CSV: " + DUONG_DAN_OUTPUT_CSV);
        } catch (IOException e) { System.err.println("[ERROR] Lỗi xuất CSV: " + e.getMessage()); }
    }

    private static void inThongKe(int thanhCong) {
        System.out.println("          KẾT QUẢ TỔNG HỢP       ");
        System.out.printf( " Tổng dòng đọc    : %-11d%n", tongBanGhi);
        System.out.printf( " Thành công       : %-11d%n", thanhCong);
        System.out.printf( " Thực tế GPS      : %-11d%n", thucTe);
        System.out.printf( " Nội suy          : %-11d%n", noiSuy);
        System.out.printf( " Khởi động lạnh   : %-11d%n", khoiDongLanh);
        System.out.printf( " Lỗi bỏ qua       : %-11d%n", loi);
    }
}   