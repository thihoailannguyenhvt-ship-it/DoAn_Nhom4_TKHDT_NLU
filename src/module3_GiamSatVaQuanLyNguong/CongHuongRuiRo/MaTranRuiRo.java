package module3_GiamSatVaQuanLyNguong.CongHuongRuiRo;

import dinhDanh.LoaiHanhDong;
import dinhDanh.MucDoNghiemTrong;
import dinhDanh.NhanTrangThai;
import dinhDanh.TinhTrangVung;
import dinhDanh.TrangThaiThamDinh;
import module3_GiamSatVaQuanLyNguong.In.*;

import java.util.EnumMap;
import java.util.Map;

/**
 * Ma trận rủi ro – phần [C]: kết hợp nhãn hành vi × nhãn vùng.
 *
 * Vai trò: Tiếp nhận các trường hợp mà [A] và [B] chưa đủ thuyết phục
 * (nhãn không đồng nhất, chuỗi vùng không ổn định, đang theo dõi...)
 * và đưa ra quyết định dựa trên sự kết hợp M2 × M4.
 *
 * Mẫu thiết kế áp dụng
 * 
 *   Lookup Table: EnumMap lồng nhau thay vì if/else – thêm quy tắc
 *       mới chỉ cần thêm 1 dòng trong {@link #khoiTaoBang()}, không chạm logic.
 *   Null Object: Ô trống trong bảng (không có quy tắc) trả về
 *       {@code DANG_THEO_DOI} thay vì null – tránh NullPointerException.</li>
 *  Factory Method: Dùng các static factory của {@link KetQuaDanhGia}
 *       để tạo kết quả nhất quán với phần còn lại của module.</li>
 * 
 *
 * Bảng ma trận>
 * 
 * NhanTrangThai \ TinhTrangVung | AN_TOAN   | TIEN_CANH_BAO | CANH_BAO  | NGUY_CAP
 * ──────────────────────────────┼───────────┼───────────────┼───────────┼──────────
 * BINH_THUONG                   | —         | GIAM_SAT      | CANH_BAO  | CANH_BAO
 * DINH_BAY                      | CANH_BAO  | CANH_BAO      | KHAN_CAP  | KHAN_CAP
 * SUY_KIET                      | —         | —             | —         | —
 * 
 *
 * SUY_KIET không có trong bảng vì đã được xử lý độc lập ở [A].
 * BINH_THUONG + AN_TOAN = ô trống → DANG_THEO_DOI (Null Object).
 */
public class MaTranRuiRo {

    /**
     * Bảng tra cứu hai chiều:
     * NhanTrangThai → (TinhTrangVung → KetQuaDanhGia)
     *
     * EnumMap lồng nhau đảm bảo tra cứu O(1) và không cần if/else.
     */
    private final Map<NhanTrangThai,
                  Map<TinhTrangVung, KetQuaDanhGia>> bangTraCuu;

    // ── Constructor ────────────────────────────────────────────────────────

    public MaTranRuiRo() {
        bangTraCuu = new EnumMap<>(NhanTrangThai.class);
        khoiTaoBang();
    }

    /**
     * Khai báo toàn bộ quy tắc ma trận.
     * Đây là nơi DUY NHẤT định nghĩa logic kết hợp M2 × M4.
     * Thêm/sửa quy tắc → chỉnh ở đây, không đụng logic khác.
     */
    private void khoiTaoBang() {

        // ── BINH_THUONG × Vùng ────────────────────────────────────────────
        // Con vật khỏe mạnh nhưng tiếp cận / ở trong vùng nguy hiểm
        // → vẫn là mối nguy cho cả con vật lẫn cộng đồng
        Map<TinhTrangVung, KetQuaDanhGia> hangBinhThuong =
            new EnumMap<>(TinhTrangVung.class);

        // AN_TOAN + BINH_THUONG → không có trong bảng (Null Object xử lý)
        hangBinhThuong.put(TinhTrangVung.TIEN_CANH_BAO,
            taoKetQua(MucDoNghiemTrong.GIAM_SAT,
                "BINH_THUONG + TIEN_CANH_BAO – theo dõi tiếp cận"));

        hangBinhThuong.put(TinhTrangVung.CANH_BAO,
            taoKetQua(MucDoNghiemTrong.CANH_BAO,
                "BINH_THUONG + CANH_BAO – con vật khỏe nhưng đang vào vùng nguy hiểm"));

        hangBinhThuong.put(TinhTrangVung.NGUY_CAP,
            taoKetQua(MucDoNghiemTrong.CANH_BAO,
                "BINH_THUONG + NGUY_CAP – con vật khỏe mạnh nhưng đang ở khu dân cư"));

        bangTraCuu.put(NhanTrangThai.BINH_THUONG, hangBinhThuong);

        // ── DINH_BAY × Vùng ──────────────────────────────────────────────
        // Con vật bị mắc bẫy + vùng nguy hiểm = cộng hưởng rủi ro cao nhất
        Map<TinhTrangVung, KetQuaDanhGia> hangDinhBay =
            new EnumMap<>(TinhTrangVung.class);

        hangDinhBay.put(TinhTrangVung.AN_TOAN,
            taoKetQua(MucDoNghiemTrong.CANH_BAO,
                "DINH_BAY + AN_TOAN – dính bẫy dù ở vùng an toàn vẫn cần can thiệp"));

        hangDinhBay.put(TinhTrangVung.TIEN_CANH_BAO,
            taoKetQua(MucDoNghiemTrong.CANH_BAO,
                "DINH_BAY + TIEN_CANH_BAO – dính bẫy và đang tiếp cận vùng nguy hiểm"));

        hangDinhBay.put(TinhTrangVung.CANH_BAO,
            taoKetQua(MucDoNghiemTrong.KHAN_CAP,
                "DINH_BAY + CANH_BAO – cộng hưởng rủi ro: bẫy trong vùng nguy hiểm"));

        hangDinhBay.put(TinhTrangVung.NGUY_CAP,
            taoKetQua(MucDoNghiemTrong.KHAN_CAP,
                "DINH_BAY + NGUY_CAP – khẩn cấp tối đa: bẫy tại khu dân cư"));

        bangTraCuu.put(NhanTrangThai.DINH_BAY, hangDinhBay);

        // SUY_KIET không đăng ký – xử lý độc lập ở [A], không bao giờ vào đây
    }

    // ── Phương thức chính ──────────────────────────────────────────────────

    /**
     * Tra cứu ma trận và trả về kết quả đánh giá.
     *
     * Được gọi từ {@code BoDieuPhoiTrungTam} khi [A] và [B] chưa đủ thuyết phục.
     *
     * @param nhan Nhãn hành vi từ Module 2
     * @param vung Tình trạng vùng từ Module 4
     * @return KetQuaDanhGia tương ứng, hoặc DANG_THEO_DOI nếu không có quy tắc
     */
    public KetQuaDanhGia traCuu(NhanTrangThai nhan, TinhTrangVung vung) {
        Map<TinhTrangVung, KetQuaDanhGia> hang = bangTraCuu.get(nhan);

        // Không có hàng → nhãn không thuộc ma trận (SUY_KIET, hoặc chưa đăng ký)
        if (hang == null) {
            return KetQuaDanhGia.dangTheoDoi(
                "Nhãn '" + nhan + "' không thuộc ma trận – tiếp tục theo dõi");
        }

        KetQuaDanhGia ketQua = hang.get(vung);

        // Ô trống trong bảng (Null Object Pattern) – không có quy tắc cho tổ hợp này
        if (ketQua == null) {
            return KetQuaDanhGia.dangTheoDoi(
                String.format("Không có quy tắc cho (%s × %s) – tiếp tục theo dõi",
                    nhan, vung));
        }

        return ketQua;
    }

    // ── Tiện ích nội bộ ───────────────────────────────────────────────────

    /**
     * Tạo KetQuaDanhGia chuẩn cho một ô trong bảng ma trận.
     * Luôn là GUI_THANG_M5 + DA_XAC_NHAN vì ma trận chỉ được gọi
     * khi đã có đủ bằng chứng từ cả M2 và M4.
     */
    private KetQuaDanhGia taoKetQua(MucDoNghiemTrong mucDo, String lyDo) {
        return new KetQuaDanhGia(
            TrangThaiThamDinh.DA_XAC_NHAN,
            LoaiHanhDong.GUI_THANG_M5,
            mucDo,
            lyDo
        );
    }
}


