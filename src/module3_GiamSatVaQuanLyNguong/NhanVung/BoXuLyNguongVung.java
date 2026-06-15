package module3_GiamSatVaQuanLyNguong.NhanVung;

import dinhDanh.TinhTrangVung;
import module3_GiamSatVaQuanLyNguong.In.*;


import java.util.EnumMap;
import java.util.Map;

/**
 * Bộ xử lý ngưỡng vùng – điều phối phần [B].
 *
 * Dùng EnumMap để chọn chiến lược theo TinhTrangVung,
 * đối xứng hoàn toàn với BoXuLyNguong bên NhanHanhVi.
 *
 * Thêm mức vùng mới → chỉ cần thêm 1 dòng đăng ký ở constructor.
 */
public class BoXuLyNguongVung {

    private final BoDanhGiaVung boDanhGiaChung;
    private final Map<TinhTrangVung, ChienLuocDanhGiaVung> danhMucChienLuoc;

    public BoXuLyNguongVung() {
        this.boDanhGiaChung  = new BoDanhGiaVung();
        this.danhMucChienLuoc = new EnumMap<>(TinhTrangVung.class);

        // Đăng ký chiến lược cho từng mức vùng
        danhMucChienLuoc.put(TinhTrangVung.AN_TOAN,       new ChienLuocXuLyAnToan(boDanhGiaChung));
        danhMucChienLuoc.put(TinhTrangVung.TIEN_CANH_BAO, new ChienLuocXuLyTienCanhBao(boDanhGiaChung));
        danhMucChienLuoc.put(TinhTrangVung.CANH_BAO,      new ChienLuocXuLyCanhBao(boDanhGiaChung));
        danhMucChienLuoc.put(TinhTrangVung.NGUY_CAP,      new ChienLuocXuLyNguyCAP(boDanhGiaChung));
    }

    public BoXuLyNguongVung(long cuaSoThoiGianMs) {
        this.boDanhGiaChung  = new BoDanhGiaVung(cuaSoThoiGianMs);
        this.danhMucChienLuoc = new EnumMap<>(TinhTrangVung.class);

        danhMucChienLuoc.put(TinhTrangVung.AN_TOAN,       new ChienLuocXuLyAnToan(boDanhGiaChung));
        danhMucChienLuoc.put(TinhTrangVung.TIEN_CANH_BAO, new ChienLuocXuLyTienCanhBao(boDanhGiaChung));
        danhMucChienLuoc.put(TinhTrangVung.CANH_BAO,      new ChienLuocXuLyCanhBao(boDanhGiaChung));
        danhMucChienLuoc.put(TinhTrangVung.NGUY_CAP,      new ChienLuocXuLyNguyCAP(boDanhGiaChung));
    }

    /**
     * Đánh giá tình trạng vùng – chọn chiến lược theo TinhTrangVung.
     *
     * @param goiTin Bản ghi sinh học tổng hợp từ Module 2 + Module 4
     * @return KetQuaDanhGia với LoaiHanhDong tương ứng
     */
    public KetQuaDanhGia danhGia(BanGhiSinhHoc goiTin) {
        TinhTrangVung vung = goiTin.layTinhTrangVung();
        ChienLuocDanhGiaVung chienLuoc = danhMucChienLuoc.get(vung);

        // Fallback an toàn nếu có mức vùng chưa đăng ký
        if (chienLuoc == null) {
            return KetQuaDanhGia.dangTheoDoi(
                "Mức vùng chưa được đăng ký: " + vung);
        }

        return chienLuoc.thucHienDanhGia(goiTin);
    }

    public void xoaCuaSo(String idCaThe) {
        boDanhGiaChung.xoaCuaSo(idCaThe);
    }

    public int laySoConVatDangTheoDoi() {
        return boDanhGiaChung.laySoConVatDangTheoDoi();
    }
}

