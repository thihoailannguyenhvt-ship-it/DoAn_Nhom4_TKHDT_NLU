package module3_GiamSatVaQuanLyNguong.NhanHanhVi;

import module3_GiamSatVaQuanLyNguong.In.*;

/**
 * Chiến lược đánh giá – interface Strategy Pattern.
 *
 * Mỗi cài đặt xử lý một trường hợp cụ thể:
 *   ChienLuocXuLyDinhBay  → xét độc lập nhãn DINH_BAY (phần A)
 *   ChienLuocMacDinh      → xét độc lập nhãn vùng + fallback (phần B, C)
 *
 * BoXuLyNguong chọn chiến lược phù hợp và gọi {@link #danhGia(BanGhiSinhHoc)}.
 */
public interface ChienLuocDanhGia {

    /**
     * Đánh giá bản ghi sinh học và trả về quyết định tiếp theo.
     *
     * @param banGhi Bản ghi sinh học tổng hợp từ Module 2 + Module 4
     * @return KetQuaDanhGia với LoaiHanhDong tương ứng
     */
    KetQuaDanhGia thucHienDanhGia(BanGhiSinhHoc goiTin, BanGhiSinhHoc hoSo);
}

