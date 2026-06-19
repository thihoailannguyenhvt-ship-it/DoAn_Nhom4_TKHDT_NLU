package module2_PhanTichHanhVi;

import java.util.LinkedList;
import java.util.Queue;

import dinhDanh.NhanTrangThai;

public class QuyTacDinhBay extends QuyTac {

    public QuyTacDinhBay(int mucDoUuTien) {
        super(mucDoUuTien);
    }

    @Override
    protected boolean duDieuKien(CuaSoTruot cuaSo) {
        return cuaSo.layDanhSachHienTai().size() >= 2;
    }

    @Override
    protected boolean kiemTraChiTiet(DongVat dv, CuaSoTruot cuaSo, ThuVienDinhMucSinhHoc dinhMuc) {
        Queue<BangGhiSinhHoc> buffer = cuaSo.layDanhSachHienTai();
        BoTinhToanSinhHoc boTinhToan = new BoTinhToanSinhHoc();

        // 1. Tính ODBA trung bình (Mức độ vận động của con vật)
        double odbaTB = boTinhToan.tinhODBA(buffer);

        // 2. Tối ưu: Lấy First và Last an toàn
        // Lưu ý: Nếu buffer không phải LinkedList, hãy dùng iterator thay vì ép kiểu (casting)
        BangGhiSinhHoc first = ((LinkedList<BangGhiSinhHoc>) buffer).peekFirst();
        BangGhiSinhHoc last = ((LinkedList<BangGhiSinhHoc>) buffer).peekLast();

        // Sử dụng hàm đã cập nhật: trực tiếp truyền đối tượng, không cần String
        double khoangCach = boTinhToan.tinhKhoangCach(first, last);

      
        // Bị dính bẫy = Vùng vẫy mạnh (ODBA cao) + Không di chuyển (Khoảng cách GPS nhỏ)
        return odbaTB > dinhMuc.getNguongBienThienDinhBay() 
            && khoangCach < dinhMuc.getNguongKhoangCachDinhBay();
    }

    @Override
    protected NhanTrangThai getTrangThai() {
        return NhanTrangThai.DINH_BAY;
    }

    @Override
    public String getLoaiCuaSoCanThiet() {
        return "DINH_BAY"; 
    }
}