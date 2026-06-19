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


public class TestMainModule3 {

    public static void main(String[] args) throws IOException {
       
        String rootPath = System.getProperty("user.dir"); 

      
        String thuMucData = rootPath + File.separator + "Data" + File.separator + "Output";
        
        
        String pathM1  = thuMucData + File.separator + "KetQuaModule1.txt";
        String pathM2  = thuMucData + File.separator + "KetQuaModule2.txt";
        String pathM4  = thuMucData + File.separator + "KetQuaModule4.txt";
        String pathOut = thuMucData + File.separator + "KetQuaModule3"; 

        System.out.println("====== START PIPELINE MODULE 3 ======");

       
        File directory = new File(thuMucData);
        if (!directory.exists()) {
            directory.mkdirs(); 
            System.out.println("-> Da tao moi thu muc: " + thuMucData);
        }

       
        BoDocKetQuaModule1 boDocM1 = new BoDocKetQuaModule1();
        BoDocKetQuaModule2 boDocM2 = new BoDocKetQuaModule2();
        BoDocKetQuaModule4 boDocM4 = new BoDocKetQuaModule4(); 

        
        List<BanGhiModule1> dsM1 = boDocM1.docFile(pathM1);
        List<BanGhiModule2> dsM2 = boDocM2.docFile(pathM2);
        List<BanGhiModule4> dsM4 = boDocM4.docFile(pathM4);

       
        Map<String, BanGhiModule1> mapM1 = new HashMap<>();
        for (BanGhiModule1 b : dsM1) mapM1.put(b.layIdConVat(), b);

        Map<String, BanGhiModule2> mapM2 = new HashMap<>();
        for (BanGhiModule2 b : dsM2) mapM2.put(b.layIdConVat(), b);

        System.out.println("[1] Đã tải dữ liệu M1, M2 vào Map bộ nhớ.");
        System.out.println("[2] Đã tải dữ liệu M4 vào danh sách bộ nhớ.");

        List<BanGhiSinhHoc> danhSachBanGhiHopNhat = new ArrayList<>();
        
        System.out.println("DEBUG: Dang bat dau join dữ liệu...");
        System.out.println("DEBUG: -> Tong số bản ghi từ Module 4: " + dsM4.size());
        System.out.println("DEBUG: -> Kích thước bộ đệm Map M1 : " + mapM1.size());
        System.out.println("DEBUG: -> Kích thước bộ đệm Map M2 : " + mapM2.size());

       
        for (BanGhiModule4 bg4 : dsM4) {
            String id = bg4.layIdConVat();
            BanGhiModule1 m1 = mapM1.get(id);
            BanGhiModule2 m2 = mapM2.get(id);

           
            if (m1 != null && m2 != null) {
                
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
          
            else {
                System.out.println("DEBUG: Khong join duoc ID: " + id + 
                                   " | M1 co khong? " + (m1 != null) + 
                                   " | M2 co khong? " + (m2 != null));
            }
        }
        System.out.println("[3] Đã ghép thành công " + danhSachBanGhiHopNhat.size() + " bản ghi toàn vẹn.");


        BoXuLyNguong boXuLyHanhVi   = new BoXuLyNguong();
        BoXuLyNguongVung boXuLyVung = new BoXuLyNguongVung();
        MaTranRuiRo maTran          = new MaTranRuiRo();
        
       
        BoDieuPhoiTrungTam dieuPhoi = new BoDieuPhoiTrungTam(boXuLyHanhVi, boXuLyVung, maTran);

        List<KetQuaDanhGia> danhSachKetQua = new ArrayList<>();
        for (BanGhiSinhHoc g : danhSachBanGhiHopNhat) {
            danhSachKetQua.add(dieuPhoi.xuLyGoiTin(g));
        }
        System.out.println("[4] Đã hoàn thành xử lý luồng đánh giá rủi ro trung tâm.");

      
        BoXuatKetQua boXuat = new BoXuatKetQua(pathOut);
        
     
        boXuat.xuatTatCa(danhSachBanGhiHopNhat, danhSachKetQua);
        
        System.out.println("[5] HOÀN THÀNH PIPELINE. Dữ liệu đầu ra tại: " + pathOut);
        System.out.println("====== END PIPELINE MODULE 3 ======");
    }
}
