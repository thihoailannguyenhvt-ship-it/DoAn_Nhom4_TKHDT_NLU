package module2_PhanTichHanhVi;

import dinhDanh.*;
import java.util.HashMap;
import java.util.Map;

/**
 * BoPhanTichCaThe đóng vai trò là "Nhạc trưởng" (Orchestrator). Nó quản lý dữ
 * liệu đầu vào, vận hành các cửa sổ trượt (windows), và điều phối việc đánh giá
 * hành vi qua BoXuLyQuyTac.
 */
public class BoPhanTichCaThe implements IPhanTichHanhVi {

	private DongVat idDvat; // Đối tượng động vật cần phân tích
	private BoTinhToanSinhHoc boTinhToan; // Công cụ tính toán ODBA, GPS, Năng lượng
	private BoXuLyQuyTac boXuLyQuyTac; // Bộ điều phối quy tắc (rules engine)
	private BangGhiSinhHoc duLieuMoiNhat; // Lưu lại bản ghi cuối cùng để làm mốc thời gian

	// Sử dụng Map để quản lý tập trung, giúp dễ dàng mở rộng thêm cửa sổ mới mà
	// không cần sửa code
	private Map<String, CuaSoTruot> quanLyCuaSo = new HashMap<>();

	public BoPhanTichCaThe(DongVat idDvat) {
		super();
		this.idDvat = idDvat;
		this.boTinhToan = new BoTinhToanSinhHoc();
		this.boXuLyQuyTac = new BoXuLyQuyTac();

		quanLyCuaSo.put("DINH_BAY", new CuaSoTruot(1000));
		quanLyCuaSo.put("SUY_KIET", new CuaSoTruot(5000));

		
		boXuLyQuyTac.themQuyTac(new QuyTacDinhBay(1));
		boXuLyQuyTac.themQuyTac(new QuyTacSuyKiet(2));
	}

	@Override
	public void tiepNhanDuLieu(BangGhiSinhHoc data) {
		this.duLieuMoiNhat = data;
		long now = data.getMocThoiGianSuKien();

		// [FIX] Quét xóa dữ liệu ngoài cửa sổ TRƯỚC, rồi mới thêm dữ liệu mới
		// Thứ tự cũ (thêm trước, xóa sau) có thể xóa luôn bản ghi vừa thêm vào
		if (quanLyCuaSo.containsKey("DINH_BAY")) {
			quanLyCuaSo.get("DINH_BAY").quetVaLoaiBo(now - 10 * 60 * 1000);
		}

		if (quanLyCuaSo.containsKey("SUY_KIET")) {
			quanLyCuaSo.get("SUY_KIET").quetVaLoaiBo(now - 12 * 60 * 60 * 1000);
		}

		for (CuaSoTruot cs : quanLyCuaSo.values()) {
			cs.tiepNhan(data);
		}
	}

	
	@Override
	public KetQuaHanhVi thucHienPhanTich(ThuVienDinhMucSinhHoc dinhMuc) {
		
		if (quanLyCuaSo.containsKey("SUY_KIET")) {
			idDvat.setETichLuy(boTinhToan.tinhTongODBA(quanLyCuaSo.get("SUY_KIET").layDanhSachHienTai()));
		}

		
		KetQuaHanhVi kq = boXuLyQuyTac.danhGiaTatCa(idDvat, quanLyCuaSo, dinhMuc);

		
		double odba = 0;
		if (quanLyCuaSo.containsKey("DINH_BAY")) {
			odba = boTinhToan.tinhODBA(quanLyCuaSo.get("DINH_BAY").layDanhSachHienTai());
		}

		double nangLuong = 0;
		if (quanLyCuaSo.containsKey("SUY_KIET")) {
			nangLuong = boTinhToan.tinhTyLeNangLuong(quanLyCuaSo.get("SUY_KIET").layDanhSachHienTai(),
					idDvat.getLoai());
		}

		
		kq.setIdConVat(idDvat.getIdConVat());
		kq.setChiSoODBA(odba);
		kq.setTyLePhanTramNangLuong(nangLuong);

		if (duLieuMoiNhat != null) {
			kq.setT1(duLieuMoiNhat.getMocThoiGianSuKien());
		}
		kq.setT2(System.currentTimeMillis()); 

		return kq;
	}
}