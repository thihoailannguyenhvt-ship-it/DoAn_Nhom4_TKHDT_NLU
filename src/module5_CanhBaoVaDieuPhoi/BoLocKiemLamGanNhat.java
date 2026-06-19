package module5_CanhBaoVaDieuPhoi;

import java.util.List;

public class BoLocKiemLamGanNhat {
	
	// Thuộc tính mô phỏng cấu hình thuật toán bộ lọc (ví dụ: bán kính quét mặc định)
    private double banKinhQuetMacDinh;

    public BoLocKiemLamGanNhat() {
        this.banKinhQuetMacDinh = 10.0; // km
    }

    /**
     * Thuật toán tìm kiếm kiểm lâm viên tối ưu nhất dựa trên tọa độ sự cố
     * Ưu tiên 1: Nhân viên đang rảnh (dangRanh == true)
     * Ưu tiên 2: Khoảng cách hình học Euclid ngắn nhất đến tọa độ Diem
     */
    public KiemLam timKiemLamGanNhat(Diem toaDoSuCo, List<KiemLam> danhSachKiemLam) {
        KiemLam kiemLamToiUu = null;
        double khoangCachNhoNhat = Double.MAX_VALUE;

        // Vòng lặp quét danh sách để tìm nhân sự tối ưu
        for (KiemLam kl : danhSachKiemLam) {
            if (kl.isDangRanh()) {
                // Tính toán khoảng cách Euclid giữa tọa độ sự cố và tọa độ hiện tại của kiểm lâm
                double dx = kl.getViTriHienTai().getKinhDo() - toaDoSuCo.getKinhDo();
                double dy = kl.getViTriHienTai().getViDo() - toaDoSuCo.getViDo();
                double khoangCach = Math.sqrt(dx * dx + dy * dy);

                if (khoangCach < khoangCachNhoNhat) {
                    khoangCachNhoNhat = khoangCach;
                    kiemLamToiUu = kl;
                }
            }
        }

        // Trường hợp khẩn cấp nếu không có ai rảnh hoàn toàn, lấy người đầu tiên trong danh sách để điều động tăng cường
        if (kiemLamToiUu == null && !danhSachKiemLam.isEmpty()) {
            kiemLamToiUu = danhSachKiemLam.get(0);
        }

        return kiemLamToiUu;
    }

    public double getBanKinhQuetMacDinh() { return banKinhQuetMacDinh; }
    public void setBanKinhQuetMacDinh(double banKinhQuetMacDinh) { this.banKinhQuetMacDinh = banKinhQuetMacDinh; }
}
