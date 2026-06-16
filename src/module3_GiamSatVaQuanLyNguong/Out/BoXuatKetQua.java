package module3_GiamSatVaQuanLyNguong.Out;

import dinhDanh.*;

import module3_GiamSatVaQuanLyNguong.In.BanGhiSinhHoc;
import module3_GiamSatVaQuanLyNguong.In.KetQuaDanhGia;
import module3_GiamSatVaQuanLyNguong.In.MenhLenhThucThi;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Bộ xuất kết quả Module 3 → file TXT và CSV cho Module 5 đọc vào.
 *
 * Luồng xử lý: BanGhiSinhHoc + KetQuaDanhGia → [tạo] MenhLenhThucThi (nếu
 * GUI_THANG_M5) → ghi TXT + CSV
 *
 * Chỉ xuất các bản ghi có LoaiHanhDong == GUI_THANG_M5. DANG_THEO_DOI và
 * CHUYEN_MA_TRAN_RUI_RO bị lọc bỏ ở mọi điểm gọi.
 */
public class BoXuatKetQua {

	// ── Hằng số ────────────────────────────────────────────────────────────
	private static final String PHAN_CACH_CSV = ",";
	// Đã cập nhật Header CSV để chứa thêm dữ liệu vùng
	private static final String HEADER_CSV = "maSoDongVat,mucDoNghiemTrong,viDo,kinhDo,thoiGianSuKien,thoiGianGhiNhan,lyDo,idVung,loaiVung,phanTramGiaoThoa";

	private static final DateTimeFormatter DINH_DANG_NGAY = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
			.withZone(ZoneId.systemDefault());

	// ── Trạng thái ─────────────────────────────────────────────────────────
	private final String duongDanGoc;
	private BufferedWriter writerTxt;
	private BufferedWriter writerCsv;
	private int soBanGhi = 0;

	// ── Constructor ────────────────────────────────────────────────────────

	/**
	 * @param duongDanGoc Đường dẫn gốc không có đuôi. Sẽ tạo duongDanGoc.txt và
	 * duongDanGoc.csv
	 */
	public BoXuatKetQua(String duongDanGoc) {
		this.duongDanGoc = duongDanGoc;
	}

	// ── API streaming ──────────────────────────────────────────────────────

	/** Mở cả 2 file. Gọi 1 lần trước vòng lặp. */
	public void moFile() throws IOException {
		writerTxt = new BufferedWriter(new FileWriter(duongDanGoc + ".txt", false));
		writerCsv = new BufferedWriter(new FileWriter(duongDanGoc + ".csv", false));

		String tieuDe = "KET QUA MODULE 3 - GIAM SAT VA QUAN LY NGUONG";
		String ngayXuat = DINH_DANG_NGAY.format(Instant.now());

		writerTxt.write(taoDuongKe('=', 70));
		writerTxt.newLine();
		writerTxt.write(tieuDe);
		writerTxt.newLine();
		writerTxt.write("Xuat luc: " + ngayXuat);
		writerTxt.newLine();
		writerTxt.write(taoDuongKe('=', 70));
		writerTxt.newLine();

		writerCsv.write(HEADER_CSV);
		writerCsv.newLine();

		soBanGhi = 0;
		System.out.printf("[BoXuatKetQua] Da mo file: %s.txt | %s.csv%n", duongDanGoc, duongDanGoc);
	}

	/**
	 * Ghi 1 bản ghi kết quả – CHỈ ghi nếu là GUI_THANG_M5. Nhất quán với
	 * xuatTatCa(): bộ lọc nằm ở đây, không phụ thuộc caller.
	 *
	 * [5] MenhLenhThucThi được tạo ra tại đây từ BanGhiSinhHoc + KetQuaDanhGia,
	 * đóng gói đầy đủ thông tin trước khi ghi ra file cho Module 5.
	 */
	public void ghiBanGhi(BanGhiSinhHoc goiTin, KetQuaDanhGia ketQua) throws IOException {
		// Bộ lọc nhất quán: chỉ ghi GUI_THANG_M5
		if (ketQua.getHanhDong() != LoaiHanhDong.GUI_THANG_M5)
			return;

		long thoiGianGhiNhan = System.currentTimeMillis();

		// SỬA ĐỔI ĐỒNG BỘ: Lấy mốc thời gian linh hoạt (chấp nhận cả String/Long từ
		// ketQua)
		Object thoiGianGoc = ketQua.layThoiGianSuKien();

		// CHUYỂN ĐỔI PHÙ HỢP KIỂU STRING CỦA MENHLENHTHUCTHI:
		String strSuKienInput = dinhDangThoiGianHienThi(thoiGianGoc != null ? thoiGianGoc : goiTin.layThoiGianSuKien());
		String strGhiNhanInput = dinhDangThoiGianHienThi(thoiGianGhiNhan);

		// [5] Tạo MenhLenhThucThi – đã truyền thêm 3 thông số vùng mới
		MenhLenhThucThi lenh = new MenhLenhThucThi(
				goiTin.layIdCaThe(), 
				ketQua.getMucDoNghiemTrong(),
				goiTin.layKinhDo(), 
				goiTin.layViDo(), 
				strSuKienInput, 
				strGhiNhanInput, 
				ketQua.getThongBao(),
				ketQua.layIdVung(),        // Truyền ID Vùng
				ketQua.layLoaiVung(),      // Truyền Loại Vùng
				ketQua.layPhanTramGiaoThoa() // Truyền % giao thoa
		);

		// ĐỒNG BỘ THỜI GIAN ĐẸP: Đổi số long sang dạng chuỗi yyyy-MM-dd HH:mm:ss trước
		// khi ghi file
		String strSuKien = dinhDangThoiGianHienThi(lenh.getMocThoiGianSuKien());
		String strGhiNhan = dinhDangThoiGianHienThi(lenh.getMocThoiGianGhiNhan());

		// ── Ghi TXT ──────────────────────────────────────────────────────
		// Đã bổ sung in thêm thông tin vùng và % giao thoa
		writerTxt.write(String.format(
				"BanGhiKetQua[maSoDongVat=%s, mucDoNghiemTrong=%s, viDo=%.6f, kinhDo=%.6f, thoiGianSuKien=%s, thoiGianGhiNhan=%s, lyDo=%s, idVung=%s, loaiVung=%s, %%GiaoThoa=%.2f%%]",
				lenh.getMaSoDongVat(), lenh.getMucDoNghiemTrong(), lenh.getViDo(), lenh.getKinhDo(), strSuKien,
				strGhiNhan, lenh.getLyDo(), lenh.getIdVung(), lenh.getLoaiVung(), lenh.getPhanTramGiaoThoa()));
		writerTxt.newLine();

		// ── Ghi CSV ──────────────────────────────────────────────────────
		// Bổ sung ghi thêm 3 cột mới vào file CSV
		writerCsv.write(
				String.join(PHAN_CACH_CSV, 
						thoatCSV(lenh.getMaSoDongVat()), 
						thoatCSV(lenh.getMucDoNghiemTrong().name()),
						String.format("%.6f", lenh.getViDo()), 
						String.format("%.6f", lenh.getKinhDo()),
						thoatCSV(strSuKien), 
						thoatCSV(strGhiNhan), 
						thoatCSV(lenh.getLyDo()),
						thoatCSV(lenh.getIdVung()),
						thoatCSV(lenh.getLoaiVung()),
						String.valueOf(lenh.getPhanTramGiaoThoa())));
		writerCsv.newLine();

		soBanGhi++;
	}

	/** Đóng file và ghi tổng kết. */
	public void dongFile() {
		try {
			if (writerTxt != null) {
				writerTxt.write(taoDuongKe('=', 70));
				writerTxt.newLine();
				writerTxt.write("TONG SO BAN GHI XUAT: " + soBanGhi);
				writerTxt.newLine();
				writerTxt.write(taoDuongKe('=', 70));
				writerTxt.newLine();
				writerTxt.close();
			}
			if (writerCsv != null)
				writerCsv.close();
			System.out.printf("[BoXuatKetQua] Da dong file. Tong so ban ghi: %d%n", soBanGhi);
		} catch (IOException e) {
			System.err.println("[BoXuatKetQua] Loi khi dong file: " + e.getMessage());
		}
	}

	// ── API batch ──────────────────────────────────────────────────────────

	/** Xuất toàn bộ danh sách 1 lần. Bộ lọc GUI_THANG_M5 nằm trong ghiBanGhi(). */
	public void xuatTatCa(List<BanGhiSinhHoc> danhSachGoiTin, List<KetQuaDanhGia> danhSachKetQua) throws IOException {
		if (danhSachGoiTin.size() != danhSachKetQua.size()) {
			throw new IllegalArgumentException("Kich thuoc 2 danh sach khong khop: goiTin=" + danhSachGoiTin.size()
					+ ", ketQua=" + danhSachKetQua.size());
		}
		try {
			moFile();
			for (int i = 0; i < danhSachGoiTin.size(); i++) {
				ghiBanGhi(danhSachGoiTin.get(i), danhSachKetQua.get(i));
			}
		} finally {
			dongFile();
		}
	}

	// ── Tiện ích ──────────────────────────────────────────────────────────

	private String taoDuongKe(char kyTu, int soLan) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < soLan; i++)
			sb.append(kyTu);
		return sb.toString();
	}

	private String thoatCSV(String giaTri) {
		if (giaTri == null)
			return "";
		if (giaTri.contains(",") || giaTri.contains("\"") || giaTri.contains("\n")) {
			return "\"" + giaTri.replace("\"", "\"\"") + "\"";
		}
		return giaTri;
	}

	/**
	 * HÀM TIỆN ÍCH BẢO VỆ: Nhận diện linh hoạt kiểu dữ liệu thời gian. Nếu
	 * MenhLenhThucThi trả về số long (mili-giây) -> tự động chuyển đổi sang ngày
	 * tháng đẹp. Nếu hệ thống đã cập nhật trả về String sẵn -> giữ nguyên để in ra.
	 */
	private String dinhDangThoiGianHienThi(Object timeObj) {
		if (timeObj == null)
			return "1970-01-01 00:00:00";

		if (timeObj instanceof Long) {
			return DINH_DANG_NGAY.format(Instant.ofEpochMilli((Long) timeObj));
		}

		String str = timeObj.toString().trim();
		if (str.matches("\\d+")) {
			try {
				return DINH_DANG_NGAY.format(Instant.ofEpochMilli(Long.parseLong(str)));
			} catch (Exception e) {
				return "1970-01-01 00:00:00";
			}
		}
		return str;
	}

	public int laySoBanGhi() {
		return soBanGhi;
	}
}