package module4_GeofenceDongVaNguCanhKhongGian;

import java.util.List;
import dinhDanh.NhanTrangThai;

public class BoPhanLoaiNguyCo implements INguyCo {

	@Override
	public MucDoNguyCo danhGia(double phanTram) {
		if (phanTram >= 80)
			return MucDoNguyCo.NGUYCAP;
		if (phanTram >= 50)
			return MucDoNguyCo.CANHBAO;
		if (phanTram >= 20)
			return MucDoNguyCo.TIENCANHBAO;
		return MucDoNguyCo.ANTOAN;
	}

	/**
	 * CẦU NỐI MỚI: Xử lý danh sách các vùng giao thoa và đưa ra phản hồi.
	 */
	public void phanLoaiVaCanhBao(String idConVat, List<VungKhongGian> danhSachVung, NhanTrangThai trangThai,
			Diem tamGeofence, double banKinhGeofenceDo) {
		for (VungKhongGian vung : danhSachVung) {
			// Giả sử: Lấy tỷ lệ giao thoa từ vùng (Nếu chưa có, bạn có thể truyền vào 0.0
			// hoặc tính toán lại)
			double tyLeThuc = vung.tinhToanGiaoThoa(tamGeofence, banKinhGeofenceDo);
			System.out.println(">>> [DEBUG] Vùng: " + vung.getIdVung() + " | Tỷ lệ thực: " + tyLeThuc);
			// Gọi logic phân loại với TỶ LỆ THỰC
			MucDoNguyCo mucDo = this.phanLoai(tyLeThuc, vung.getLoaiVung(), trangThai.toString());
			// --- THÊM DÒNG LOG NÀY ---
	        System.out.println(">>> [VẠCH TRẦN] Vùng: " + vung.getIdVung() + " | Input: " + tyLeThuc + " -> Output MucDo: " + mucDo);
			// Xử lý phản hồi
			if (mucDo != MucDoNguyCo.ANTOAN) {
				System.out.println(
						"[CẢNH BÁO] Con vật " + idConVat + " ở mức " + mucDo + " tại vùng: " + vung.getIdVung());
			}
		}
	}

	/**
	 * Logic phân loại rủi ro theo ngữ cảnh (Giữ nguyên logic của bạn)
	 */
	public MucDoNguyCo phanLoai(double phanTram, String loaiVung, String hanhVi) {
	    MucDoNguyCo mucDo = danhGia(phanTram);

	    // --- CHỐT CHẶN Ở ĐÂY ---
	    // Nếu đã an toàn, KHÔNG ĐƯỢC PHÉP nâng cấp mức độ
	    if (mucDo == MucDoNguyCo.ANTOAN) {
	        return MucDoNguyCo.ANTOAN;
	    }

	    // Nếu mucDo > ANTOAN, lúc này mới được phép nâng cấp theo ngữ cảnh
	    if ("KHU_DAN_CU".equals(loaiVung) || "TRAM_QUAN_SAT".equals(loaiVung)) {
	        mucDo = nangCapMucDo(mucDo);
	    }

	    if ("DINH_BAY".equals(hanhVi) || "HOANG_LOAN".equals(hanhVi)) {
	        mucDo = nangCapMucDo(mucDo);
	    }

	    return mucDo;
	}
	private MucDoNguyCo nangCapMucDo(MucDoNguyCo hienTai) {
		switch (hienTai) {
		case ANTOAN:
			return MucDoNguyCo.TIENCANHBAO;
		case TIENCANHBAO:
			return MucDoNguyCo.CANHBAO;
		case CANHBAO:
			return MucDoNguyCo.NGUYCAP;
		default:
			return MucDoNguyCo.NGUYCAP;
		}
	}
}
