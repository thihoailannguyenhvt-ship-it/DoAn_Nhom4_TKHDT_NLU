package module2_PhanTichHanhVi;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DocKetQuaModule1 {

    public List<BangGhiSinhHoc> docFile(String path) {
        List<BangGhiSinhHoc> danhSach = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            br.readLine(); 

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue; 

                try {
                    String[] parts = line.split(",");
                    
                    
                    String id = parts[0].trim();
                    double viDo = Double.parseDouble(parts[1].trim());
                    double kinhDo = Double.parseDouble(parts[2].trim());
                    long thoiGian = Long.parseLong(parts[6].trim()); 

                   
                    double[] x = parseArray(parts[3]); 
                    double[] y = parseArray(parts[4]);
                    double[] z = parseArray(parts[5]);

                    BangGhiGiaToc giaToc = new BangGhiGiaToc(thoiGian, x, y, z);
                    
                    danhSach.add(new BangGhiSinhHoc(id, viDo, kinhDo, thoiGian, giaToc));

                } catch (Exception e) {
                    System.err.println("Lỗi dòng: " + line + " | Lỗi: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Không tìm thấy file: " + e.getMessage());
        }
        return danhSach;
    }

    private double[] parseArray(String data) {
       
        String cleanData = data.replace("\"", "").trim();
        String[] parts = cleanData.split("\\|"); 
        
        double[] result = new double[parts.length];
        for (int i = 0; i < parts.length; i++) {
            result[i] = Double.parseDouble(parts[i].trim());
        }
        return result;
    }
}