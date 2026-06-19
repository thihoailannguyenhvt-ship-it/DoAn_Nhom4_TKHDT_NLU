package module4_GeofenceDongVaNguCanhKhongGian;

import java.util.List;


public interface IQuanLyKhongGian {
    
   
    void themVung(VungKhongGian vung);
    void xoaVung(String maVung);
    void capNhatVung(VungKhongGian vung);
    List<VungKhongGian> layTatCaCacVung();
    VungKhongGian timKiemVung(String maVung);

  
    List<VungKhongGian> timVungCoGiaoThoa(GeofenceDong geofence, double thoiGianTreGiay);
}