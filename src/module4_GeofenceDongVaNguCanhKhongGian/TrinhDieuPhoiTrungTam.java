package module4_GeofenceDongVaNguCanhKhongGian;

import java.util.List;
import dinhDanh.NhanTrangThai;


public class TrinhDieuPhoiTrungTam implements IQuanLyKhongGian {

    private static TrinhDieuPhoiTrungTam instance;

    private final KhoLuuTruKhongGian khoLuuTru;
    private final BoAnhXaHeSoK boAnhXa;
    private final BoTinhToanBanKinh boTinhToan;
    private final BoPhanLoaiNguyCo boPhanLoai;

    private TrinhDieuPhoiTrungTam(KhoLuuTruKhongGian khoLuuTru, BoAnhXaHeSoK boAnhXa, 
                                 BoTinhToanBanKinh boTinhToan, BoPhanLoaiNguyCo boPhanLoai) {
        this.khoLuuTru = khoLuuTru;
        this.boAnhXa = boAnhXa;
        this.boTinhToan = boTinhToan;
        this.boPhanLoai = boPhanLoai;
    }

    public static TrinhDieuPhoiTrungTam getInstance(KhoLuuTruKhongGian khoLuuTru, BoAnhXaHeSoK boAnhXa,
                                                   BoTinhToanBanKinh boTinhToan, BoPhanLoaiNguyCo boPhanLoai) {
        if (instance == null) {
            instance = new TrinhDieuPhoiTrungTam(khoLuuTru, boAnhXa, boTinhToan, boPhanLoai);
        }
        return instance;
    }

 
    public void khoiTaoHeThong(String duongDanFile) {
      
        TxtDataInitializer.napCauHinhVungTinhTuTxt(duongDanFile);
        
        System.out.println(">>> [HỆ THỐNG] Khởi tạo dữ liệu từ: " + duongDanFile + " hoàn tất.");
    }
 
    public void xuLySuKien(String idConVat, Diem viTri, double odba, NhanTrangThai trangThai, double treGiay) {
       
        GeofenceDong geofence = khoLuuTru.timGeofenceTheoId(idConVat);
        if (geofence == null) {
            double rBase = boTinhToan.getRBaseInDegrees() * 111320.0;
            geofence = new GeofenceDong(idConVat, rBase, 1.0);
            khoLuuTru.themGeofence(geofence);
        }

        geofence.capNhatTam(viTri);
        geofence.setOdbaHienTai(odba);
        
        
        geofence.capNhatHeSoK(this.boAnhXa.layHeSoK(trangThai));

        
        List<VungKhongGian> vungGiaoThoa = timVungCoGiaoThoa(geofence, treGiay);

       
        if (boPhanLoai != null && !vungGiaoThoa.isEmpty()) {
          
            boPhanLoai.phanLoaiVaCanhBao(
                idConVat, 
                vungGiaoThoa, 
                trangThai, 
                geofence.getTamHienTai(),      
                geofence.getBanKinhRInDegrees(treGiay)
            );
        }
    }
    

    @Override
    public void themVung(VungKhongGian vung) { khoLuuTru.themVung(vung); }

    @Override
    public void xoaVung(String maVung) { khoLuuTru.xoaVung(maVung); }

    @Override
    public void capNhatVung(VungKhongGian vung) { khoLuuTru.capNhatVung(vung); }

    @Override
    public List<VungKhongGian> layTatCaCacVung() { return khoLuuTru.layTatCaCacVung(); }

    @Override
    public VungKhongGian timKiemVung(String maVung) { return khoLuuTru.timKiemVung(maVung); }

    @Override
    public List<VungKhongGian> timVungCoGiaoThoa(GeofenceDong geofence, double thoiGianTreGiay) {
        return khoLuuTru.timVungCoGiaoThoa(geofence, thoiGianTreGiay);
    }
}