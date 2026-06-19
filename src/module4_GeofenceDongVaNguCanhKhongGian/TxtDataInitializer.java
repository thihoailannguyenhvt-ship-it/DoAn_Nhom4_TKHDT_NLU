package module4_GeofenceDongVaNguCanhKhongGian;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TxtDataInitializer {

    
    public static void napCauHinhVungTinhTuTxt(String duongDanFileTxt) {
        KhoLuuTruKhongGian khoRAM = KhoLuuTruKhongGian.getInstance();
        String line = "";
        
       
        String splitDelimiter = "\\|"; 

        try (BufferedReader br = new BufferedReader(new FileReader(duongDanFileTxt))) {
            
            System.out.println(">>> [HỆ THỐNG] Đang nạp cấu hình vùng tĩnh từ file TXT thuần...");

            while ((line = br.readLine()) != null) {
              
                if (line.trim().isEmpty()) continue;

                
                String[] data = line.split(splitDelimiter);
                if (data.length < 6) continue;

                String loaiVung = data[0];
                String idVung = data[1];
                String tenVung = data[2];
                double kinhDoTam = Double.parseDouble(data[3]);
                double viDoTam = Double.parseDouble(data[4]);
                double banKinhMet = Double.parseDouble(data[5]);

                
                List<Diem> danhSachDinh = new ArrayList<>();
                if (data.length == 7 && !data[6].trim().isEmpty()) {
                    String chuoiToaDoRaw = data[6]; 
                    
                    
                    String[] mangCacDinh = chuoiToaDoRaw.split(";");
                    for (String dinhRaw : mangCacDinh) {
                        
                        String[] kinhVi = dinhRaw.split(",");
                        if (kinhVi.length == 2) {
                            double lon = Double.parseDouble(kinhVi[0]);
                            double lat = Double.parseDouble(kinhVi[1]);
                            danhSachDinh.add(new Diem(lon, lat));
                        }
                    }
                }

                VungKhongGian vungMoi = null;
                Diem tam = new Diem(kinhDoTam, viDoTam);

                switch (loaiVung) {
                    case "TRAM_QUAN_SAT":
                        vungMoi = VungFactory.taoTramQuanSat(idVung, tenVung, tam, banKinhMet);
                        break;
                    case "KHU_DAN_CU":
                        Diem tamKhuDanCu = danhSachDinh.isEmpty() ? tam : danhSachDinh.get(0);
                        vungMoi = VungFactory.taoKhuDanCu(idVung, tenVung, tamKhuDanCu, danhSachDinh);
                        break;
                    case "TUYEN_DUONG_DU_LICH":
                        vungMoi = VungFactory.taoTuyenDuongDuLich(idVung, tenVung, danhSachDinh, banKinhMet);
                        break;
                }

               
                if (vungMoi != null) {
                    khoRAM.themVung(vungMoi);
                    System.out.println("   + Đã nạp thành công lên RAM vùng: " + vungMoi.getTenVung());
                }
            }
            System.out.println(">>> [HỆ THỐNG] Toàn bộ vùng tĩnh từ file TXT đã sẵn sàng trên RAM!\n");

        } catch (IOException e) {
            System.err.println("Lỗi khi mở hoặc đọc file TXT: " + e.getMessage());
        }
    }
}