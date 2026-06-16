package module3_GiamSatVaQuanLyNguong;

import dinhDanh.LoaiHanhDong;
import module3_GiamSatVaQuanLyNguong.CongHuongRuiRo.MaTranRuiRo;
import module3_GiamSatVaQuanLyNguong.In.BanGhiSinhHoc;
import module3_GiamSatVaQuanLyNguong.In.KetQuaDanhGia;
import module3_GiamSatVaQuanLyNguong.NhanHanhVi.BoXuLyNguong;
import module3_GiamSatVaQuanLyNguong.NhanVung.BoXuLyNguongVung;

/**
 * Bộ điều phối trung tâm – Facade của Module 3.
 *
 * Phối hợp 3 thành phần theo thứ tự ưu tiên: [A] BoXuLyNguong – xét độc lập
 * nhãn hành vi [B] BoXuLyNguongVung – xét độc lập nhãn không gian [C]
 * MaTranRuiRo – kết hợp M2 × M4 khi A và B chưa đủ thuyết phục
 */
public class BoDieuPhoiTrungTam {

	// [1][2] Dùng BoXuLyNguong thay vì BoDanhGiaHanhVi trực tiếp
	// để tận dụng đầy đủ: EnumMap chiến lược, quản lý hồ sơ, dọn RAM
	private final BoXuLyNguong boLuongHanhVi;
	private final BoXuLyNguongVung boLuongKhongGian;
	private final MaTranRuiRo maTranRuiRo;

	public BoDieuPhoiTrungTam(BoXuLyNguong hanhVi, BoXuLyNguongVung khongGian, MaTranRuiRo maTran) {
		this.boLuongHanhVi = hanhVi;
		this.boLuongKhongGian = khongGian;
		this.maTranRuiRo = maTran;
	}

	/**
	 * Xử lý một gói tin và trả về kết quả đánh giá.
	 *
	 * Cả 2 luồng [A] và [B] LUÔN chạy đủ – không được ngắt giữa chừng. Điều này đảm
	 * bảo cửa sổ thời gian của cả hành vi lẫn vùng đều được cập nhật liên tục,
	 * không bị bỏ sót bản ghi.
	 */
	public KetQuaDanhGia xuLyGoiTin(BanGhiSinhHoc goiTin) {

		// BƯỚC 1: Chạy cả 2 luồng độc lập – BẮT BUỘC không được ngắt giữa chừng
		// [A] Luồng hành vi: SUY_KIET phát ngay / DINH_BAY kiểm tra tính liên tục
		KetQuaDanhGia kqHanhVi = boLuongHanhVi.xuLy(goiTin);

		// [B] Luồng không gian: NGUY_CAP phát ngay / chuỗi leo thang vùng
		KetQuaDanhGia kqKhongGian = boLuongKhongGian.danhGia(goiTin);

		// BƯỚC 2: Kiểm tra điều kiện đủ thuyết phục để gửi thẳng M5
		boolean hanhViGuiM5 = (kqHanhVi.getHanhDong() == LoaiHanhDong.GUI_THANG_M5);
		boolean khongGianGuiM5 = (kqKhongGian.getHanhDong() == LoaiHanhDong.GUI_THANG_M5);

		if (hanhViGuiM5 || khongGianGuiM5) {
			// Nếu cả 2 cùng kích hoạt → chọn mức độ nghiêm trọng cao hơn
			if (hanhViGuiM5 && khongGianGuiM5) {
				KetQuaDanhGia kqGoc = (kqHanhVi.getMucDoNghiemTrong().ordinal() >= kqKhongGian.getMucDoNghiemTrong()
						.ordinal()) ? kqHanhVi : kqKhongGian;

				// BỔ SUNG: Khởi tạo thực thể mới từ kết quả gốc để bảo vệ tính toàn vẹn dữ liệu
				KetQuaDanhGia kqKetThuc = new KetQuaDanhGia(kqGoc.getTrangThai(), kqGoc.getHanhDong(),
						kqGoc.getMucDoNghiemTrong(), kqGoc.getThongBao());
				// BỔ SUNG: Sử dụng chính xác hàm capNhatThoiGianSuKien theo class KetQuaDanhGia
				// của bạn
				kqKetThuc.capNhatThoiGianSuKien(goiTin.layThoiGianSuKien());
				return kqKetThuc;
			}
			KetQuaDanhGia kqGoc = hanhViGuiM5 ? kqHanhVi : kqKhongGian;

			// BỔ SUNG: Khởi tạo thực thể mới từ kết quả gốc để bảo vệ tính toàn vẹn dữ liệu
			KetQuaDanhGia kqKetThuc = new KetQuaDanhGia(kqGoc.getTrangThai(), kqGoc.getHanhDong(),
					kqGoc.getMucDoNghiemTrong(), kqGoc.getThongBao());
			// BỔ SUNG: Sử dụng chính xác hàm capNhatThoiGianSuKien theo class KetQuaDanhGia
			// của bạn
			kqKetThuc.capNhatThoiGianSuKien(goiTin.layThoiGianSuKien());
			return kqKetThuc;
		}

		// BƯỚC 3: Cả 2 chưa đủ thuyết phục → chuyển ma trận rủi ro [C]
		// Bao gồm: nhãn không đồng nhất, vùng dao động, chưa đủ T tích lũy
		KetQuaDanhGia kqMaTranGoc = maTranRuiRo.traCuu(goiTin.layNhanTrangThai(), goiTin.layTinhTrangVung());

		// BỔ SUNG: Khởi tạo thực thể mới từ kết quả ma trận để đóng dấu thời gian riêng
		// biệt cho bản ghi này
		KetQuaDanhGia kqMaTranKetThuc = new KetQuaDanhGia(kqMaTranGoc.getTrangThai(), kqMaTranGoc.getHanhDong(),
				kqMaTranGoc.getMucDoNghiemTrong(), kqMaTranGoc.getThongBao());
		// BỔ SUNG: Sử dụng chính xác hàm capNhatThoiGianSuKien theo class KetQuaDanhGia
		// của bạn
		kqMaTranKetThuc.capNhatThoiGianSuKien(goiTin.layThoiGianSuKien());
		return kqMaTranKetThuc;
	}
}