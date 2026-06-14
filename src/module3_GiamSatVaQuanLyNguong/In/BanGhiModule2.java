package module3_GiamSatVaQuanLyNguong.In;

import dinhDanh.NhanTrangThai;

/**
 * Bản ghi đầu ra Module 2 – chỉ chứa phần Module 3 cần dùng.
 *
 * Module 3 chỉ quan tâm: idConVat → định danh cá thể trangThai → nhãn hành vi
 * để phân tích thoiGianSuKien (t1) → T_event: timestamp sự kiện gốc từ cảm biến
 *
 * Thời gian hệ thống lúc Module 3 nhận được bản ghi được lấy bằng
 * System.currentTimeMillis() tại thời điểm xử lý – không đọc từ file.
 *
 * Các trường ODBA, tyLeNangLuong, t2, doTreModule2 bị bỏ qua khi parse.
 *
 * Data class thuần – không chứa logic nghiệp vụ.
 */
public class BanGhiModule2 {

	private final String idConVat;
	private final NhanTrangThai trangThai;
	private final long thoiGianSuKien; // t1 – ms Unix

	public BanGhiModule2(String idConVat, NhanTrangThai trangThai, long thoiGianSuKien) {
		this.idConVat = idConVat;
		this.trangThai = trangThai;
		this.thoiGianSuKien = thoiGianSuKien;
	}

	public String layIdConVat() {
		return idConVat;
	}

	public NhanTrangThai layTrangThai() {
		return trangThai;
	}

	public long layThoiGianSuKien() {
		return thoiGianSuKien;
	}

	@Override
	public String toString() {
		return String.format("BanGhiM2{id='%s', trangThai=%s, t1=%d}", idConVat, trangThai, thoiGianSuKien);
	}
}
