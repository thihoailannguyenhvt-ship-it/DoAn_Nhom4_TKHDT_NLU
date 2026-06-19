package module3_GiamSatVaQuanLyNguong.In;

import dinhDanh.NhanTrangThai;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BoDocKetQuaModule2 {

	private static final String TIEN_TO_TXT = "KetQuaHanhVi[";

	private static final int CSV_COT_ID = 0;
	private static final int CSV_COT_TRANG_THAI = 1;
	private static final int CSV_SO_COT_TOI_THIEU = 2;

	public List<BanGhiModule2> docFile(String duongDanFile) throws IOException {
		if (duongDanFile == null || duongDanFile.trim().isEmpty()) {
			throw new IllegalArgumentException("Duong dan file khong hop le");
		}
		List<BanGhiModule2> ketQua = new ArrayList<>();
		int soDongHopLe = 0;
		int soDongBoQua = 0;
		boolean laCSV = duongDanFile.toLowerCase().endsWith(".csv");

		try (BufferedReader reader = new BufferedReader(new FileReader(duongDanFile))) {

			String dong;
			boolean boQuaHeader = laCSV;

			while ((dong = reader.readLine()) != null) {
				dong = dong.trim();
				if (dong.isEmpty())
					continue;

				if (boQuaHeader) {
					boQuaHeader = false;
					continue;
				}

				if (!laCSV && (dong.startsWith("=") || dong.startsWith("KET QUA") || dong.startsWith("MODULE"))) {
					continue;
				}

				try {
					BanGhiModule2 banGhi = laCSV ? parseDongCSV(dong) : parseDongTXT(dong);

					if (banGhi != null) {
						ketQua.add(banGhi);
						soDongHopLe++;
					}
				} catch (Exception e) {
					soDongBoQua++;
					System.err.printf("[BoDocKetQuaModule2] Bo qua dong loi: %s%n  Ly do: %s%n", dong, e.getMessage());
				}
			}
		}

		System.out.printf("[BoDocKetQuaModule2] '%s' (%s): %d ban ghi hop le, %d dong bo qua%n", duongDanFile,
				laCSV ? "CSV" : "TXT", soDongHopLe, soDongBoQua);

		return ketQua;
	}

	private BanGhiModule2 parseDongTXT(String dong) {
		if (dong == null || dong.trim().isEmpty())
			return null;

		if (dong.startsWith("=") || dong.startsWith("KET QUA"))
			return null;

		if (!dong.startsWith(TIEN_TO_TXT))
			return null;

		int viTriMo = dong.indexOf('[');
		int viTriDong = dong.lastIndexOf(']');
		if (viTriMo < 0 || viTriDong <= viTriMo)
			return null;

		String noiDung = dong.substring(viTriMo + 1, viTriDong);

		String[] truongs = noiDung.split(",");
		if (truongs.length < CSV_SO_COT_TOI_THIEU)
			return null;

		String idConVat = layGiaTriSauDauBang(truongs[CSV_COT_ID]);
		NhanTrangThai trangThai = parseNhan(layGiaTriSauDauBang(truongs[CSV_COT_TRANG_THAI]));

		if (idConVat != null && !idConVat.isEmpty() && trangThai != null) {
			return new BanGhiModule2(idConVat, trangThai);
		}
		return null;
	}

	private String layGiaTriSauDauBang(String truong) {
		if (truong == null)
			return null;
		truong = truong.trim();
		int viTriBang = truong.indexOf('=');
		return (viTriBang >= 0) ? truong.substring(viTriBang + 1).trim() : truong;
	}

	private BanGhiModule2 parseDongCSV(String dong) {

		String[] cots = dong.split(",", -1);
		if (dong.trim().isEmpty()) {
			return null;
		}

		if (cots.length < CSV_SO_COT_TOI_THIEU) {
			throw new IllegalArgumentException(
					String.format("CSV thieu cot – can toi thieu %d, co %d", CSV_SO_COT_TOI_THIEU, cots.length));
		}

		String idConVat = cots[CSV_COT_ID].trim();
		NhanTrangThai trangThai = parseNhan(cots[CSV_COT_TRANG_THAI].trim());

		kiemTraDuTruong(idConVat, trangThai);
		return new BanGhiModule2(idConVat, trangThai);
	}

	private NhanTrangThai parseNhan(String tenNhan) {

		if (tenNhan == null) {
			throw new IllegalArgumentException("Nhan rong");
		}

		tenNhan = tenNhan.trim().toUpperCase();

		switch (tenNhan) {
		case "BINH_THUONG":
			return NhanTrangThai.BINH_THUONG;

		case "DINH_BAY":
			return NhanTrangThai.DINH_BAY;

		case "SUY_KIET":
			return NhanTrangThai.SUY_KIET;

		default:
			throw new IllegalArgumentException("Nhan khong hop le: '" + tenNhan + "'");
		}
	}

	private void kiemTraDuTruong(String idConVat, NhanTrangThai trangThai) {
		if (idConVat == null || idConVat.isEmpty()) {
			throw new IllegalArgumentException("Thieu idConVat");
		}
		if (trangThai == null) {
			throw new IllegalArgumentException("Thieu trangThai");
		}
	}
}