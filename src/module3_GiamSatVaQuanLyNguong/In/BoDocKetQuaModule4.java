package module3_GiamSatVaQuanLyNguong.In;

import dinhDanh.TinhTrangVung;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class BoDocKetQuaModule4 {

  
    public List<BanGhiModule4> docFile(String duongDanFile) throws IOException {
        List<BanGhiModule4> ketQua = new ArrayList<>();

       
        String id = null;
        String nhanVungStr = null;
        Double viDo = null;
        Double kinhDo = null;
        String idVung = null;          
        String loaiVung = null;        
        Double phanTramGiaoThoa = 0.0; 
      

        try (BufferedReader reader = new BufferedReader(new FileReader(duongDanFile))) {
            String dong;
            
            while ((dong = reader.readLine()) != null) {
                dong = dong.trim();
                
              
                if (dong.isEmpty()) continue;

               
                if (dong.startsWith("ID con vat")) {
                    
                  
                    if (id != null) {
                        ketQua.add(new BanGhiModule4(id, parseNhan(nhanVungStr), viDo, kinhDo, idVung, loaiVung, phanTramGiaoThoa));
                    }
                    
                    
                    id = tachGiaTri(dong);
                    nhanVungStr = null; 
                    viDo = null; 
                    kinhDo = null; 
                    phanTramGiaoThoa = 0.0; 
                    idVung = null; 
                    loaiVung = null;
                } 
                
                
                else if (dong.startsWith("Vi tri")) {
                    String coords = tachGiaTri(dong).replaceAll("[()]", ""); 
                    String[] parts = coords.split(",");
                    viDo = Double.parseDouble(parts[0].trim());
                    kinhDo = Double.parseDouble(parts[1].trim());
                    
                } 
                
                else if (dong.startsWith("ID vung")) {
                    idVung = tachGiaTri(dong);
                }
                else if (dong.startsWith("Loai Vung")) {
                    loaiVung = tachGiaTri(dong);
                }
                else if (dong.startsWith("Phan tram giao thoa")) {
                    // Loại bỏ % và chuyển sang double
                    phanTramGiaoThoa = Double.parseDouble(tachGiaTri(dong).replace("%", ""));
                } 
                else if (dong.startsWith("Nguy co khong gian")) {
                    nhanVungStr = tachGiaTri(dong);
                }
            }
        
            if (id != null) {
                ketQua.add(new BanGhiModule4(id, parseNhan(nhanVungStr), viDo, kinhDo, idVung, loaiVung, phanTramGiaoThoa));
            }
        }
        
        System.out.println("[BoDocKetQuaModule4] Đã đọc xong, tổng số bản ghi: " + ketQua.size());
        return ketQua;
    }

    private String tachGiaTri(String dong) {
        int idx = dong.indexOf(":");
        return (idx != -1) ? dong.substring(idx + 1).trim() : "";
    }

    
    private TinhTrangVung parseNhan(String tenNhan) {
        if (tenNhan == null) return TinhTrangVung.AN_TOAN;
        
        tenNhan = tenNhan.trim().toUpperCase();
        
        switch (tenNhan) {
            case "ANTOAN": 
            case "AN_TOAN": return TinhTrangVung.AN_TOAN;
            case "TIENCANHBAO": 
            case "TIEN_CANH_BAO": return TinhTrangVung.TIEN_CANH_BAO;
            case "CANHBAO": 
            case "CANH_BAO": return TinhTrangVung.CANH_BAO;
            case "NGUYCAP": 
            case "NGUY_CAP": return TinhTrangVung.NGUY_CAP;
            default: return TinhTrangVung.AN_TOAN;
        }
    }
}