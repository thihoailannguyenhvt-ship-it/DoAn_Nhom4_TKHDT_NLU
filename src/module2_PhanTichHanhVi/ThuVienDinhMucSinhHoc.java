package module2_PhanTichHanhVi;

import java.util.HashMap;
import java.util.Map;

// Thu vien luu cac nguong sinh hoc dung cho Module 2
public class ThuVienDinhMucSinhHoc {

    private double trangThaiNghiNgoiBTH = 0.092;

    private double mucHoatDongToiThieu = 0.1;

    private double mucHoatDongToiDa = 0.6;

    private double nguongCangThang = 0.4;

    // Theo tai lieu: duoi 40% thi xem la suy kiet
    private double nguongKietSuc = 0.4;

    // Nguong variance de xac dinh vung vay manh
    private double nguongBienThienDinhBay = 0.002;

    // Nguong khoang cach GPS gan nhu khong doi
    private double nguongKhoangCachDinhBay = 0.0008;

    private Map<String, Double> dsDinhMucLoai;

    public ThuVienDinhMucSinhHoc() {

        dsDinhMucLoai = new HashMap<>();

        dsDinhMucLoai.put("DONG_VAT_HOANG_DA", 0.5);
        dsDinhMucLoai.put("CHIM", 0.5);
        dsDinhMucLoai.put("VOI", 1.2);
        dsDinhMucLoai.put("HO", 0.8);
    }

    public double layDinhMuc(String idLoai) {
        return dsDinhMucLoai.getOrDefault(idLoai, 0.0);
    }

    public boolean capNhatDinhMuc(String idLoai, double cacThamSo) {

        dsDinhMucLoai.put(idLoai, cacThamSo);

        return true;
    }

    public boolean kiemTraTinhHopLe() {

        return trangThaiNghiNgoiBTH >= 0
                && mucHoatDongToiThieu >= 0
                && mucHoatDongToiDa > mucHoatDongToiThieu
                && nguongCangThang >= 0
                && nguongKietSuc >= 0;
    }

    public double getTrangThaiNghiNgoiBTH() {
        return trangThaiNghiNgoiBTH;
    }

    public double getMucHoatDongToiThieu() {
        return mucHoatDongToiThieu;
    }

    public double getMucHoatDongToiDa() {
        return mucHoatDongToiDa;
    }

    public double getNguongCangThang() {
        return nguongCangThang;
    }

    public double getNguongKietSuc() {
        return nguongKietSuc;
    }

    public double getNguongBienThienDinhBay() {
        return nguongBienThienDinhBay;
    }

    public double getNguongKhoangCachDinhBay() {
        return nguongKhoangCachDinhBay;
    }

    public Map<String, Double> getDsDinhMucLoai() {
        return dsDinhMucLoai;
    }
}