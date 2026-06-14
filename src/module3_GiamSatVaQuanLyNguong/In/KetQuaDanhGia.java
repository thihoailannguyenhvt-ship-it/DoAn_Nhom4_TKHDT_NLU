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

	// ==========================================
	// 1. CÁC HÀM KHỞI TẠO (CONSTRUCTORS) LINH HOẠT
	// ==========================================

	/**
	 * Constructor đầy đủ 4 tham số. Thường dùng cho Ma trận quyết định hoặc khi cần
	 * chỉ định rõ mức độ nghiêm trọng.
	 */
	public KetQuaDanhGia(TrangThaiThamDinh trangThai, LoaiHanhDong hanhDong, MucDoNghiemTrong mucDoNghiemTrong,
			String thongBao) {
		this.trangThai = trangThai;
		this.hanhDong = hanhDong;
		this.mucDoNghiemTrong = mucDoNghiemTrong;
		this.thongBao = thongBao;
	}

	/**
	 * Constructor 3 tham số: (TrangThaiThamDinh, LoaiHanhDong, String). Khớp 100%
	 * với cách viết trong các lớp Chiến lược (như ảnh bạn chụp lỗi trước đó).
	 */
	public KetQuaDanhGia(TrangThaiThamDinh trangThai, LoaiHanhDong hanhDong, String thongBao) {
		this.trangThai = trangThai;
		this.hanhDong = hanhDong;
		// Tự động suy luận mức độ nghiêm trọng dựa trên trạng thái thẩm định để tránh
		// truyền thừa tham số
		this.mucDoNghiemTrong = (trangThai == TrangThaiThamDinh.DA_XAC_NHAN) ? MucDoNghiemTrong.CANH_BAO
				: MucDoNghiemTrong.BINH_THUONG;
		this.thongBao = thongBao;
	}

	/**
	 * Constructor dự phòng 3 tham số: (LoaiHanhDong, MucDoNghiemTrong, String). Đảm
	 * bảo nếu có Module khác gọi theo dạng này thì hệ thống vẫn bắt được và tự điền
	 * TrangThaiThamDinh.
	 */
	public KetQuaDanhGia(LoaiHanhDong hanhDong, MucDoNghiemTrong mucDoNghiemTrong, String thongBao) {
		this.hanhDong = hanhDong;
		this.mucDoNghiemTrong = mucDoNghiemTrong;
		this.thongBao = thongBao;
		// Tự động suy luận trạng thái dựa trên mức độ nghiêm trọng
		this.trangThai = (mucDoNghiemTrong == MucDoNghiemTrong.BINH_THUONG) ? TrangThaiThamDinh.DANG_THEO_DOI
				: TrangThaiThamDinh.DA_XAC_NHAN;
	}

	// ==========================================
	// 2. CÁC PHƯƠNG THỨC GETTER (Khớp với BoXuLyNguong)
	// ==========================================

	public TrangThaiThamDinh getTrangThai() {
		return this.trangThai;
	}

	public LoaiHanhDong getHanhDong() {
		return this.hanhDong;
	}

	public MucDoNghiemTrong getMucDoNghiemTrong() {
		return this.mucDoNghiemTrong;
	}

	public String getThongBao() {
		return this.thongBao;
	}

	public static KetQuaDanhGia chuyenMaTran(String thongBao) {
		return new KetQuaDanhGia(TrangThaiThamDinh.DANG_THEO_DOI, LoaiHanhDong.CHUYEN_MA_TRAN_RUI_RO,
				MucDoNghiemTrong.BINH_THUONG, thongBao);
	}

	/**
	 * PHÁT LỆNH KHẨN CẤP: Khớp chính xác với lệnh gọi của bạn khi đủ ngưỡng 15
	 * phút.
	 */
	public static KetQuaDanhGia guiThangM5(MucDoNghiemTrong mucDo, String thongBao) {
		return new KetQuaDanhGia(TrangThaiThamDinh.DA_XAC_NHAN, LoaiHanhDong.GUI_THANG_M5, mucDo, thongBao);
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
				+ mucDoNghiemTrong + ", thongBao='" + thongBao + '\'' + '}';
	}
}