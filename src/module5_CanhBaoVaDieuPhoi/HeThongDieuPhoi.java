package module5_CanhBaoVaDieuPhoi;

import java.util.List;

public class HeThongDieuPhoi {
	private BoLocKiemLamGanNhat boLocKiemLam;

    // Constructor khởi tạo hệ thống nghiệp vụ và thiết lập bộ lọc
    public HeThongDieuPhoi() {
        this.boLocKiemLam = new BoLocKiemLamGanNhat();
    }

    /**
     * Hàm nghiệp vụ lõi: Nhận tọa độ Diem của sự cố và danh sách nhân sự hiện có,
     * sau đó ủy quyền cho lớp BoLockKiemLamGanNhatClass thực hiện thuật toán tìm kiếm.
     */
    public KiemLam timNhanVienGanNhat(Diem toaDo, List<KiemLam> danhSach) {
        System.out.println("[Model - Nghiệp vụ] Hệ thống điều phối đang gọi bộ lọc tính toán không gian...");
        return this.boLocKiemLam.timKiemLamGanNhat(toaDo, danhSach);
    }

    /**
     * Hàm nghiệp vụ lõi: Quản lý tổng thể tiến trình và trạng thái các lệnh cứu hộ.
     */
    public void quanLyDieuPhoi() {
        System.out.println("[Model - Nghiệp vụ] Đồng bộ tiến độ thực địa và trạng thái lệnh cứu hộ về trung tâm.");
    }

    // --- Hệ thống Getter và Setter để quản trị linh hoạt ---
    public BoLocKiemLamGanNhat getBoLockKiemLam() {
        return boLocKiemLam;
    }

    public void setBoLockKiemLam(BoLocKiemLamGanNhat boLockKiemLam) {
        this.boLocKiemLam = boLockKiemLam;
    }
}
