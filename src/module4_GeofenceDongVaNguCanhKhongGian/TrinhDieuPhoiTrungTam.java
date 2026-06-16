package module4_GeofenceDongVaNguCanhKhongGian;

import java.util.List;
import dinhDanh.NhanTrangThai;

/**
 * Trình điều phối trung tâm - Phiên bản Orchestrator đầy đủ.
 * Đảm nhận cả việc khởi tạo hệ thống, điều phối nghiệp vụ và giám sát thời gian thực.
 */
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

 // =========================================================================
    // 1. QUẢN LÝ VÒNG ĐỜI HỆ THỐNG
    // =========================================================================
    
    /**
     * Cập nhật để khớp chính xác với phương thức nạp dữ liệu của bạn
     */
    public void khoiTaoHeThong(String duongDanFile) {
        // Gọi phương thức từ TxtDataInitializer thay vì loadData cũ
        TxtDataInitializer.napCauHinhVungTinhTuTxt(duongDanFile);
        
        System.out.println(">>> [HỆ THỐNG] Khởi tạo dữ liệu từ: " + duongDanFile + " hoàn tất.");
    }
 // =========================================================================
    // 2. ĐIỀU PHỐI NGHIỆP VỤ (Đã sửa lỗi gọi static)
    // =========================================================================
    public void xuLySuKien(String idConVat, Diem viTri, double odba, NhanTrangThai trangThai, double treGiay) {
        // Bước 1: Quản lý Geofence
        GeofenceDong geofence = khoLuuTru.timGeofenceTheoId(idConVat);
        if (geofence == null) {
            double rBase = boTinhToan.getRBaseInDegrees() * 111320.0;
            geofence = new GeofenceDong(idConVat, rBase, 1.0);
            khoLuuTru.themGeofence(geofence);
        }

        geofence.capNhatTam(viTri);
        geofence.setOdbaHienTai(odba);
        
        // SỬA ĐỔI: Dùng biến instance 'this.boAnhXa' thay vì gọi lớp tĩnh
        geofence.capNhatHeSoK(this.boAnhXa.layHeSoK(trangThai));

        // Bước 2: Quét giao thoa
        List<VungKhongGian> vungGiaoThoa = timVungCoGiaoThoa(geofence, treGiay);

        // Bước 3: Phân loại nguy cơ
     // Bước 3: Phân loại nguy cơ
        if (boPhanLoai != null && !vungGiaoThoa.isEmpty()) {
            // Cập nhật lời gọi hàm với 2 tham số mới: geofence.getTam() và geofence.getBanKinhDo()
            boPhanLoai.phanLoaiVaCanhBao(
                idConVat, 
                vungGiaoThoa, 
                trangThai, 
                geofence.getTamHienTai(),      // Truyền tâm thực
                geofence.getBanKinhRInDegrees(treGiay) // Truyền bán kính thực
            );
        }
    }
    

    // =========================================================================
    // 3. HIỆN THỰC IQuanLyKhongGian (Delegate)
    // =========================================================================
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