package module2_PhanTichHanhVi;

import java.util.HashMap;
import java.util.Map;

public class ThuVienDinhMucSinhHoc {

	// Sử dụng hằng số cho các giá trị mặc định để dễ quản lý
	private static final double DEFAULT_TRANG_THAI_NGHI = 0.092;
	private static final double DEFAULT_HOAT_DONG_MIN = 0.1;
	private static final double DEFAULT_HOAT_DONG_MAX = 0.6;
	private static final double DEFAULT_CANG_THANG = 0.4;
	private static final double DEFAULT_KIET_SUC = 0.4;
	private static final double DEFAULT_BIEN_THIEN_DINH_BAY = 0.002;
	private static final double DEFAULT_KHOANG_CACH_DINH_BAY = 0.0008;

	private double trangThaiNghiNgoiBTH = DEFAULT_TRANG_THAI_NGHI;
	private double mucHoatDongToiThieu = DEFAULT_HOAT_DONG_MIN;
	private double mucHoatDongToiDa = DEFAULT_HOAT_DONG_MAX;
	private double nguongCangThang = DEFAULT_CANG_THANG;
	private double nguongKietSuc = DEFAULT_KIET_SUC;
	private double nguongBienThienDinhBay = DEFAULT_BIEN_THIEN_DINH_BAY;
	private double nguongKhoangCachDinhBay = DEFAULT_KHOANG_CACH_DINH_BAY;

	private Map<String, Double> dsDinhMucLoai;

	public ThuVienDinhMucSinhHoc() {
		dsDinhMucLoai = new HashMap<>();
		dsDinhMucLoai.put("DONG_VAT_HOANG_DA", 0.5);
		dsDinhMucLoai.put("CHIM", 0.5);
		dsDinhMucLoai.put("VOI", 1.2);
		dsDinhMucLoai.put("HO", 0.8);
	}

	public double layDinhMuc(String idLoai) {
		return dsDinhMucLoai.getOrDefault(idLoai, 1.0);
	}

	public boolean capNhatDinhMuc(String idLoai, double cacThamSo) {
		if (idLoai == null || idLoai.isEmpty())
			return false;
		dsDinhMucLoai.put(idLoai, cacThamSo);
		return true;
	}

	public boolean kiemTraTinhHopLe() {
		return trangThaiNghiNgoiBTH >= 0 && mucHoatDongToiThieu >= 0 && mucHoatDongToiDa > mucHoatDongToiThieu
				&& nguongCangThang >= 0 && nguongKietSuc >= 0;
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
}