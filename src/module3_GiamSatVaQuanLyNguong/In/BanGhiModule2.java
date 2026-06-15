package module3_GiamSatVaQuanLyNguong.In;

import dinhDanh.NhanTrangThai;

/**
 * Bản ghi đầu ra Module 2 – chỉ chứa phần Module 3 cần dùng.
 *
 * Module 3 chỉ cần: idConVat → định danh cá thể trangThai → nhãn hành vi để
 * phân tích
 *
 * Thời gian không cần thiết vì Module 1 đã lọc và xác thực toàn bộ dữ liệu
 * trước khi đến Module 2 và Module 3.
 */
public class BanGhiModule2 {

	private final String idConVat;
	private final NhanTrangThai trangThai;

	public BanGhiModule2(String idConVat, NhanTrangThai trangThai) {
		this.idConVat = idConVat;
		this.trangThai = trangThai;
	}

	public String layIdConVat() {
		return idConVat;
	}

	public NhanTrangThai layTrangThai() {
		return trangThai;
	}

	@Override
	public String toString() {
		return String.format("BanGhiM2{id='%s', trangThai=%s}", idConVat, trangThai);
	}
}
