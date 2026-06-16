package module4_GeofenceDongVaNguCanhKhongGian;

import java.util.List;

/**
 * Lớp Factory chịu trách nhiệm khởi tạo tập trung các loại Vùng Không Gian (Tĩnh).
 * Giúp che giấu logic khởi tạo phức tạp và chuẩn hóa đầu ra về lớp cha VungKhongGian.
 */
public class VungFactory {

    /**
     * Nhà máy sản xuất Trạm Quan Sát (Vùng tròn trắc địa)
     */
    public static VungKhongGian taoTramQuanSat(String idVung, String tenVung, Diem tam, double banKinhBaoVeMet) {
        return new TramQuanSat(idVung, tenVung, tam, banKinhBaoVeMet);
    }

    /**
     * Nhà máy sản xuất Khu Dân Cư (Vùng đa giác phức tạp)
     */
    public static VungKhongGian taoKhuDanCu(String idVung, String tenVung, Diem toaDoTam, List<Diem> vertices) {
        return new KhuDanCu(idVung, tenVung, toaDoTam, vertices);
    }

    /**
     * Nhà máy sản xuất Tuyến Đường Du Lịch (Đường gấp khúc có vùng đệm)
     */
    public static VungKhongGian taoTuyenDuongDuLich(String idVung, String tenVung, List<Diem> danhSachDinh, double doRongBufferMet) {
        return new TuyenDuongDuLich(idVung, tenVung, danhSachDinh, doRongBufferMet);
    }
}