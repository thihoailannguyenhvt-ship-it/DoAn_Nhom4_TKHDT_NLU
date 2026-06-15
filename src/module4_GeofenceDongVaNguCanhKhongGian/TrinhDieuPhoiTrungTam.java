package module4_GeofenceDongVaNguCanhKhongGian;
import java.util.ArrayList;

public class TrinhDieuPhoiTrungTam implements IQuanLyKhongGian {

	private static TrinhDieuPhoiTrungTam instance;

	private KhoLuuTruKhongGian khoLuuTru;
	private BoAnhXaHeSoK boAnhXa;
	private BoTinhToanBanKinh boTinhToan;
	private BoPhanLoaiNguyCo boPhanLoai;

	private TrinhDieuPhoiTrungTam(KhoLuuTruKhongGian khoLuuTru, BoAnhXaHeSoK boAnhXa, BoTinhToanBanKinh boTinhToan,
			BoPhanLoaiNguyCo boPhanLoai) {

		this.khoLuuTru = khoLuuTru;
		this.boAnhXa = boAnhXa;
		this.boTinhToan = boTinhToan;
		this.boPhanLoai = boPhanLoai;
	}

	public static TrinhDieuPhoiTrungTam getInstance(KhoLuuTruKhongGian khoLuuTru, BoAnhXaHeSoK boAnhXa,
			BoTinhToanBanKinh boTinhToan, BoPhanLoaiNguyCo boPhanLoai) {

		if (instance == null) {
			instance = new TrinhDieuPhoiTrungTam(khoLuuTru, boAnhXa, boTinhToan, boPhanLoai);
		}

		return instance;
	}

	public double tinhBanKinhR(double k, double odba) {
		return boTinhToan.tinhBanKinh(k, odba);
	}

	public void capNhatTamGeofence(GeofenceDong geofence, Diem viTriMoi) {

		geofence.capNhatTam(viTriMoi);
	}

	public ArrayList<VungKhongGian> truyVanKhongGian() {
		return khoLuuTru.layDanhSachVung();
	}

	public double quetGiaoThoa(GeofenceDong geofence, VungKhongGian vung) {

		return vung.tinhToanGiaoThoa(geofence);
	}

	@Override
	public void themVung(String maVung, String loai, Diem diem, double banKinh) {

		VungTinh vungMoi = new VungTinh(maVung, loai, "tron", 0, diem);

		khoLuuTru.themVung(vungMoi);
	}

	@Override
	public void xoaVung(String maVung) {

		khoLuuTru.layDanhSachVung().removeIf(v -> {

			if (v instanceof VungTinh) {
				return ((VungTinh) v).getMaVung().equals(maVung);
			}

			return false;
		});
	}

	@Override
	public void capNhatVung(String maVung, Diem diem) {

		ArrayList<VungKhongGian> danhSach = khoLuuTru.layDanhSachVung();

		for (VungKhongGian v : danhSach) {

			if (v instanceof VungTinh) {

				VungTinh vt = (VungTinh) v;

				if (vt.getMaVung().equals(maVung)) {

					vt.setDiem(diem);
					break;
				}
			}
		}
	}
}
