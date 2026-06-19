package module4_GeofenceDongVaNguCanhKhongGian;

import java.io.*;
import java.util.*;
import dinhDanh.NhanTrangThai;

public class Test {

	// Giữ nguyên cấu trúc đọc dữ liệu
	static class DuLieuM1 {
		String idConVat;
		double viDo, kinhDo;

		DuLieuM1(String id, double v, double k) {
			this.idConVat = id;
			this.viDo = v;
			this.kinhDo = k;
		}
	}

	static class DuLieuM2 {
		String idConVat, trangThai;
		double odba, tyLe;

		DuLieuM2(String id, String tt, double o, double tl) {
			this.idConVat = id;
			this.trangThai = tt;
			this.odba = o;
			this.tyLe = tl;
		}
	}

	public static void main(String[] args) {
		
		KhoLuuTruKhongGian kho = KhoLuuTruKhongGian.getInstance();
		BoAnhXaHeSoK boAnhXa = new BoAnhXaHeSoK();
		BoTinhToanBanKinh boTinhToan = new BoTinhToanBanKinh();
		BoPhanLoaiNguyCo boPhanLoai = new BoPhanLoaiNguyCo();

		
		TrinhDieuPhoiTrungTam trungTam = TrinhDieuPhoiTrungTam.getInstance(kho, boAnhXa, boTinhToan, boPhanLoai);

		
		trungTam.khoiTaoHeThong("Data\\Input\\vung_tinh_config.txt");

		Map<String, Queue<DuLieuM1>> duLieuModule1 = docModule1("Data//Output//KetQuaModule1.csv");
		List<DuLieuM2> duLieuModule2 = docModule2("Data//Output//KetQuaModule2.csv");

		
		try (PrintWriter txt = new PrintWriter("Data//Output//KetQuaModule4.txt");
				PrintWriter csv = new PrintWriter("Data//Output//KetQuaModule4.csv")) {
			for (DuLieuM2 m2 : duLieuModule2) {
				Queue<DuLieuM1> hangDoi = duLieuModule1.get(m2.idConVat);
				if (hangDoi == null || hangDoi.isEmpty())
					continue;
				DuLieuM1 m1 = hangDoi.poll();

				
				trungTam.xuLySuKien(m2.idConVat, new Diem(m1.kinhDo, m1.viDo), m2.odba,
						NhanTrangThai.valueOf(m2.trangThai), 0.0);

				
				GeofenceDong g = kho.timGeofenceTheoId(m2.idConVat);

				
				double rDegrees = g.getBanKinhRInDegrees(0.0);
				List<VungKhongGian> danhSachVungGiaoThoa = trungTam.timVungCoGiaoThoa(g, 0.0);

				double maxPt = 0;
				VungKhongGian bestVung = null;

				for (VungKhongGian v : danhSachVungGiaoThoa) {

					double pt = v.tinhToanGiaoThoa(new Diem(m1.kinhDo, m1.viDo), rDegrees);
					if (pt > maxPt) {
						maxPt = pt;
						bestVung = v;
					}
				}

				String idVung = (bestVung != null) ? bestVung.getIdVung() : "KHONG_GIAO_THOA";
				String loaiVung = (bestVung != null) ? bestVung.getLoaiVung() : "KHONG_XAC_DINH";

				
				MucDoNguyCo mucDo = boPhanLoai.phanLoai(maxPt, idVung, m2.trangThai);

				// Ghi kết quả
				txt.println("========================================");
				txt.printf("%-24s: %s%n", "ID con vat", m2.idConVat);
				txt.printf("%-24s: (%f, %f)%n", "Vi tri", m1.viDo, m1.kinhDo);
				txt.printf("%-24s: %s%n", "ID vung", idVung);
				txt.printf("%-24s: %s%n", "Loai Vung", loaiVung);

				txt.printf("%-24s: %.2f%%%n", "Phan tram giao thoa", maxPt);
				txt.printf("%-24s: %s%n", "Nguy co khong gian", mucDo.toString());
				txt.println("----------------------------------------");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	private static Map<String, Queue<DuLieuM1>> docModule1(String path) {
		Map<String, Queue<DuLieuM1>> map = new HashMap<>();
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			br.readLine();
			String line;
			while ((line = br.readLine()) != null) {
				if (line.trim().isEmpty())
					continue;
				List<String> p = tachCSV(line, line.contains(";") ? ';' : ',');
				String id = p.get(0).trim();
				map.putIfAbsent(id, new LinkedList<>());
				map.get(id).add(
						new DuLieuM1(id, Double.parseDouble(p.get(1).trim()), Double.parseDouble(p.get(2).trim())));
			}
		} catch (Exception e) {
			System.out.println("Loi M1: " + e.getMessage());
		}
		return map;
	}

	private static List<DuLieuM2> docModule2(String path) {
		List<DuLieuM2> ds = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			br.readLine();
			String line;
			while ((line = br.readLine()) != null) {
				if (line.trim().isEmpty())
					continue;
				List<String> p = tachCSV(line, line.contains(";") ? ';' : ',');
				ds.add(new DuLieuM2(p.get(0).trim(), p.get(1).trim(), Double.parseDouble(p.get(2).trim()),
						Double.parseDouble(p.get(3).trim())));
			}
		} catch (Exception e) {
			System.out.println("Loi M2: " + e.getMessage());
		}
		return ds;
	}

	private static List<String> tachCSV(String line, char delimiter) {
		List<String> kq = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		boolean inQ = false;
		for (char c : line.toCharArray()) {
			if (c == '"')
				inQ = !inQ;
			else if (c == delimiter && !inQ) {
				kq.add(sb.toString());
				sb.setLength(0);
			} else
				sb.append(c);
		}
		kq.add(sb.toString());
		return kq;
	}
}