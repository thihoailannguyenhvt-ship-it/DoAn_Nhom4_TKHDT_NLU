package module4_GeofenceDongVaNguCanhKhongGian;

import java.util.ArrayList;
import java.util.List;

/**
 * Lớp kho lưu trữ không gian đóng vai trò quản lý toàn bộ thực thể tĩnh (Khu bảo tồn, Trạm...)
 * và trạng thái thực thể động (Geofence của từng con vật) trực tiếp trên bộ nhớ RAM.
 */
public class KhoLuuTruKhongGian implements IQuanLyKhongGian {
    
    // === [BỔ SUNG THÊM] Biến static duy nhất để giữ thực thể kho trên RAM ===
    private static KhoLuuTruKhongGian instance;

    // Sử dụng Interface List thay cho ArrayList ở khai báo thuộc tính để tuân thủ tính đa hình
    private List<VungKhongGian> danhSachVung;
    private List<GeofenceDong> danhSachGeofence;

    /**
     * Hàm khởi tạo có tham số đầy đủ (GIỮ NGUYÊN CỦA BẠN)
     */
    public KhoLuuTruKhongGian(List<VungKhongGian> danhSachVung, List<GeofenceDong> danhSachGeofence) {
        this.danhSachVung = danhSachVung != null ? danhSachVung : new ArrayList<>();
        this.danhSachGeofence = danhSachGeofence != null ? danhSachGeofence : new ArrayList<>();
    }

    /**
     * SỬA ĐỔI: Chuyển từ public thành private để ngăn việc tạo nhiều kho trống con
     */
    private KhoLuuTruKhongGian() {
        this.danhSachVung = new ArrayList<>();
        this.danhSachGeofence = new ArrayList<>();
    }

    /**
     * === [BỔ SUNG THÊM] Hàm toàn cục để lấy ra kho lưu trữ duy nhất trên RAM ===
     */
    public static synchronized KhoLuuTruKhongGian getInstance() {
        if (instance == null) {
            instance = new KhoLuuTruKhongGian();
        }
        return instance;
    }

    // =========================================================================
    // HIỆN THỰC HÓA CÁC PHƯƠNG THỨC TỪ INTERFACE IQuanLyKhongGian (QUẢN LÝ VÙNG TĨNH)
    // =========================================================================

    @Override
    public void themVung(VungKhongGian vung) {
        if (vung != null) {
            this.danhSachVung.add(vung);
        }
    }

    @Override
    public void xoaVung(String maVung) {
        // Sử dụng Lambda Expression để xóa vùng có mã khớp với tham số truyền vào
        this.danhSachVung.removeIf(vung -> vung.getIdVung().equals(maVung));
    }

    @Override
    public void capNhatVung(VungKhongGian vungMoi) {
        if (vungMoi == null) return;
        for (int i = 0; i < danhSachVung.size(); i++) {
            if (danhSachVung.get(i).getIdVung().equals(vungMoi.getIdVung())) {
                danhSachVung.set(i, vungMoi); // Thay thế vùng cũ bằng dữ liệu vùng mới
                return;
            }
        }
    }

    @Override
    public List<VungKhongGian> layTatCaCacVung() {
        return this.danhSachVung;
    }

    @Override
    public VungKhongGian timKiemVung(String maVung) {
        for (VungKhongGian vung : danhSachVung) {
            if (vung.getIdVung().equals(maVung)) {
                return vung;
            }
        }
        return null; // Trả về null nếu không tìm thấy vùng tĩnh nào tương ứng
    }

    // =========================================================================
    // CÔNG THỨC QUÉT VA CHẠM VÀ TRUY VẤN KHÔNG GIAN THỜI GIAN THỰC
    // =========================================================================
    
    @Override
    public List<VungKhongGian> timVungCoGiaoThoa(GeofenceDong geofence, double thoiGianTreGiay) {
        List<VungKhongGian> ketQua = new ArrayList<>();
        
        // 1. Trích xuất các tham số động học không gian đã được chuẩn hóa ra hệ đơn vị ĐỘ (Degrees)
        Diem tamHienTai = geofence.getTamHienTai();
        double banKinhXetDuyetDo = geofence.getBanKinhRInDegrees(thoiGianTreGiay);

        // 2. Duyệt qua danh sách vùng tĩnh để thực hiện phép toán hình học đa hình
        for (VungKhongGian vungTinh : danhSachVung) {
            // LƯU Ý: đổi tên hàm "tinhToanGiaoThoa" bên dưới cho khớp với tên hàm tính toán
            // hình học diện tích/khoảng cách hiện tại đang có sẵn trong lớp VungKhongGian của bạn nhé.
            double tyLeGiaoThoa = vungTinh.tinhToanGiaoThoa(tamHienTai, banKinhXetDuyetDo);
            
            if (tyLeGiaoThoa > 0) {
                ketQua.add(vungTinh); // Ghi nhận vùng tĩnh đang bị xâm lấn hoặc đe dọa rủi ro
            }
        }
        return ketQua;
    }

    // =========================================================================
    // CÁC HÀM QUẢN LÝ DANH SÁCH GEOFENCE ĐỘNG TRÊN RAM (PHỤC VỤ TRÌNH ĐIỀU PHỐI)
    // =========================================================================

    public List<GeofenceDong> layDanhSachGeofence() {
        return this.danhSachGeofence;
    }

    public void themGeofence(GeofenceDong geofence) {
        if (geofence != null) {
            this.danhSachGeofence.add(geofence);
        }
    }

    /**
     * Tìm kiếm nhanh thực thể Geofence động của một con vật dựa trên mã ID.
     * Hàm này giúp Trình điều phối trung tâm tái sử dụng vùng không gian cũ trên RAM mà không phải tạo mới liên tục.
     */
    public GeofenceDong timGeofenceTheoId(String idConVat) {
        for (GeofenceDong gf : danhSachGeofence) {
            if (gf.getIdConVat().equals(idConVat)) {
                return gf;
            }
        }
        return null;
    }
}