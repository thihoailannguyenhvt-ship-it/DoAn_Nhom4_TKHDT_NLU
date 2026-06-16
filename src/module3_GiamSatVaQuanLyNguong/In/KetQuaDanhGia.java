package module3_GiamSatVaQuanLyNguong.In;

import dinhDanh.TrangThaiThamDinh;
import dinhDanh.LoaiHanhDong;
import dinhDanh.MucDoNghiemTrong;

/**
 * Class KetQuaDanhGia: Đối tượng đóng gói kết quả sau khi thẩm định. Cung cấp
 * đầy đủ các Constructor và Getter phục vụ cho toàn bộ Module 3.
 */
public class KetQuaDanhGia {

	private final TrangThaiThamDinh trangThai;
	private final LoaiHanhDong hanhDong;
	private final MucDoNghiemTrong mucDoNghiemTrong;
	private final String thongBao;
	private String thoiGianSuKien;
	
	// CÁC TRƯỜNG MỚI CHO DỮ LIỆU KHÔNG GIAN
	private final String idVung;
	private final String loaiVung;
	private final double phanTramGiaoThoa;

	// ==========================================
	// 1. CÁC HÀM KHỞI TẠO (CONSTRUCTORS) LINH HOẠT
	// ==========================================

	/**
	 * Constructor đầy đủ 4 tham số (Phiên bản cũ).
	 * Giữ lại để tránh lỗi code cũ. Các trường mới sẽ để mặc định.
	 */
	public KetQuaDanhGia(TrangThaiThamDinh trangThai, LoaiHanhDong hanhDong, MucDoNghiemTrong mucDoNghiemTrong,
			String thongBao) {
		this(trangThai, hanhDong, mucDoNghiemTrong, thongBao, null, null, 0.0);
	}

	/**
	 * Constructor mở rộng: Hỗ trợ truyền đầy đủ thông tin không gian.
	 */
	public KetQuaDanhGia(TrangThaiThamDinh trangThai, LoaiHanhDong hanhDong, MucDoNghiemTrong mucDoNghiemTrong,
			String thongBao, String idVung, String loaiVung, double phanTramGiaoThoa) {
		this.trangThai = trangThai;
		this.hanhDong = hanhDong;
		this.mucDoNghiemTrong = mucDoNghiemTrong;
		this.thongBao = thongBao;
		this.idVung = idVung;
		this.loaiVung = loaiVung;
		this.phanTramGiaoThoa = phanTramGiaoThoa;
	}

	/**
	 * Constructor 3 tham số: (TrangThaiThamDinh, LoaiHanhDong, String). Khớp 100%
	 * với cách viết trong các lớp Chiến lược.
	 */
	public KetQuaDanhGia(TrangThaiThamDinh trangThai, LoaiHanhDong hanhDong, String thongBao) {
		this(trangThai, hanhDong, thongBao, null, null, 0.0);
	}

	// Constructor phụ hỗ trợ cho hàm 3 tham số
	private KetQuaDanhGia(TrangThaiThamDinh trangThai, LoaiHanhDong hanhDong, String thongBao, String idVung, String loaiVung, double phanTram) {
		this.trangThai = trangThai;
		this.hanhDong = hanhDong;
		this.mucDoNghiemTrong = (trangThai == TrangThaiThamDinh.DA_XAC_NHAN) ? MucDoNghiemTrong.CANH_BAO
				: MucDoNghiemTrong.BINH_THUONG;
		this.thongBao = thongBao;
		this.idVung = idVung;
		this.loaiVung = loaiVung;
		this.phanTramGiaoThoa = phanTram;
	}

	/**
	 * Constructor dự phòng 3 tham số: (LoaiHanhDong, MucDoNghiemTrong, String).
	 */
	public KetQuaDanhGia(LoaiHanhDong hanhDong, MucDoNghiemTrong mucDoNghiemTrong, String thongBao) {
		this.hanhDong = hanhDong;
		this.mucDoNghiemTrong = mucDoNghiemTrong;
		this.thongBao = thongBao;
		this.trangThai = (mucDoNghiemTrong == MucDoNghiemTrong.BINH_THUONG) ? TrangThaiThamDinh.DANG_THEO_DOI
				: TrangThaiThamDinh.DA_XAC_NHAN;
		this.idVung = null;
		this.loaiVung = null;
		this.phanTramGiaoThoa = 0.0;
	}

	// ==========================================
	// 2. CÁC PHƯƠNG THỨC GETTER (Khớp với BoXuLyNguong)
	// ==========================================

	public TrangThaiThamDinh getTrangThai() { return this.trangThai; }
	public LoaiHanhDong getHanhDong() { return this.hanhDong; }
	public MucDoNghiemTrong getMucDoNghiemTrong() { return this.mucDoNghiemTrong; }
	public String getThongBao() { return this.thongBao; }
	
	// Getter cho các trường dữ liệu không gian mới
	public String layIdVung() { return idVung; }
	public String layLoaiVung() { return loaiVung; }
	public double layPhanTramGiaoThoa() { return phanTramGiaoThoa; }

	public void capNhatThoiGianSuKien(String thoiGian) {
		this.thoiGianSuKien = thoiGian;
	}

	public String layThoiGianSuKien() {
		return this.thoiGianSuKien;
	}

	public static KetQuaDanhGia chuyenMaTran(String thongBao) {
		return new KetQuaDanhGia(TrangThaiThamDinh.DANG_THEO_DOI, LoaiHanhDong.CHUYEN_MA_TRAN_RUI_RO,
				MucDoNghiemTrong.BINH_THUONG, thongBao);
	}

	/**
	 * PHÁT LỆNH KHẨN CẤP: Đã bổ sung tham số không gian để Module 5 nhận đủ thông tin.
	 */
	public static KetQuaDanhGia guiThangM5(MucDoNghiemTrong mucDo, String thongBao, String idVung, String loaiVung, double phanTram) {
		return new KetQuaDanhGia(TrangThaiThamDinh.DA_XAC_NHAN, LoaiHanhDong.GUI_THANG_M5, mucDo, thongBao, idVung, loaiVung, phanTram);
	}

	/**
	 * Hàm tạo nhanh trạng thái đang theo dõi (Dùng cho fallback hoặc chiến lược mặc
	 * định)
	 */
	public static KetQuaDanhGia dangTheoDoi(String ghiChu) {
		return new KetQuaDanhGia(TrangThaiThamDinh.DANG_THEO_DOI, LoaiHanhDong.DANG_THEO_DOI,
				MucDoNghiemTrong.BINH_THUONG, ghiChu);
	}

	@Override
	public String toString() {
		return "KetQuaDanhGia{" + "trangThai=" + trangThai + ", hanhDong=" + hanhDong + ", mucDoNghiemTrong="
				+ mucDoNghiemTrong + ", thongBao='" + thongBao + '\'' + 
				", idVung='" + idVung + '\'' + ", loaiVung='" + loaiVung + '\'' + 
				", phanTram=" + phanTramGiaoThoa + '}';
	}
}