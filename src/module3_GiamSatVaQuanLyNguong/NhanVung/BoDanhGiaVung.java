package module3_GiamSatVaQuanLyNguong.NhanVung;

import dinhDanh.TinhTrangVung;
import dinhDanh.MucDoNghiemTrong;
import module3_GiamSatVaQuanLyNguong.In.*;


import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

/**
 * Bộ đánh giá vùng – phần [B]: xét độc lập nhãn không gian.
 *
 * Đối xứng hoàn toàn với BoDanhGiaHanhVi ở phần [A].
 *
 * <h3>Logic xử lý</h3>
 *
 * [Ngoại lệ] NGUY_CAP → guiThangM5 KHAN_CAP ngay, không qua cửa sổ.
 *
 * [AN_TOAN] → xóa cửa sổ, dangTheoDoi.
 *
 * [TIEN_CANH_BAO / CANH_BAO] → tích lũy cửa sổ trượt T:
 *
 *   Chưa đủ 2 bản ghi → DANG_THEO_DOI
 *   (chưa có xu hướng để phán đoán)
 *
 *   Đồng nhất (không lần nào giảm, tối thiểu TIEN_CANH_BAO trở lên)
 *   + đủ thời gian T → guiThangM5 CANH_BAO
 *   + chưa đủ T      → DANG_THEO_DOI
 *
 *   Không đồng nhất (có lần giảm xuống) → chuyenMaTran
 *   (cần kết hợp nhãn hành vi từ M2 để có thêm bằng chứng)
 *
 * <h3>Định nghĩa "đồng nhất" cho nhãn vùng</h3>
 * Trong cửa sổ T, vùng KHÔNG có lần nào giảm so với lần trước —
 * chỉ tăng hoặc giữ nguyên, và tất cả đều >= TIEN_CANH_BAO.
 * Ví dụ:
 *   TIEN_CANH_BAO → CANH_BAO → CANH_BAO  ✓ đồng nhất
 *   TIEN_CANH_BAO → TIEN_CANH_BAO         ✓ đồng nhất
 *   CANH_BAO → TIEN_CANH_BAO → CANH_BAO  ✗ không đồng nhất (có lần giảm)
 */
public class BoDanhGiaVung {

    private long cuaSoThoiGianMs;

    /** Key: idCaThe → lịch sử tình trạng vùng trong cửa sổ trượt */
    private final Map<String, Deque<BanGhiVung>> lichSuVung;

    // ── Inner class ────────────────────────────────────────────────────────
    private static class BanGhiVung {
        final TinhTrangVung vung;
        final long          thoiGian;

        BanGhiVung(TinhTrangVung vung, long thoiGian) {
            this.vung     = vung;
            this.thoiGian = thoiGian;
        }
    }

    // ── Constructor ────────────────────────────────────────────────────────

    public BoDanhGiaVung() {
        this(900_000L); // 15 phút
    }

    public BoDanhGiaVung(long cuaSoThoiGianMs) {
        this.cuaSoThoiGianMs = cuaSoThoiGianMs;
        this.lichSuVung      = new HashMap<>();
    }

    // ── Phương thức chính ──────────────────────────────────────────────────

    public KetQuaDanhGia danhGia(BanGhiSinhHoc banGhi) {
        TinhTrangVung vung    = banGhi.layTinhTrangVung();
        String        idCaThe = banGhi.layIdCaThe();
        long          thoiGian = System.currentTimeMillis();

        // ── Ngoại lệ: NGUY_CAP – phát ngay, không qua cửa sổ ─────────────
        if (vung == TinhTrangVung.NGUY_CAP) {
            lichSuVung.remove(idCaThe);
            return KetQuaDanhGia.guiThangM5(
                MucDoNghiemTrong.KHAN_CAP,
                "Vùng NGUY_CAP – con vật ở khu dân cư, gửi thẳng Module 5"
            );
        }

        // ── AN_TOAN – reset cửa sổ, chờ tiếp ─────────────────────────────
        if (vung == TinhTrangVung.AN_TOAN) {
            lichSuVung.remove(idCaThe);
            return KetQuaDanhGia.dangTheoDoi(
                "AN_TOAN – cửa sổ vùng đã reset"
            );
        }

        // ── TIEN_CANH_BAO / CANH_BAO – tích lũy cửa sổ ───────────────────
        Deque<BanGhiVung> cuaSo = lichSuVung
            .computeIfAbsent(idCaThe, k -> new ArrayDeque<>());

        // Loại bản ghi cũ hơn T (cửa sổ trượt theo thời gian thực)
        long nguong = thoiGian - cuaSoThoiGianMs;
        while (!cuaSo.isEmpty() && cuaSo.peekFirst().thoiGian < nguong) {
            cuaSo.pollFirst();
        }

        cuaSo.addLast(new BanGhiVung(vung, thoiGian));

        // Chưa đủ 2 bản ghi – chưa có xu hướng để phán đoán
        if (cuaSo.size() < 2) {
            return KetQuaDanhGia.dangTheoDoi(
                String.format("Vùng %s – mới bắt đầu tích lũy, chờ thêm dữ liệu",
                    vung)
            );
        }

        // Kiểm tra tính đồng nhất (không lần nào giảm, tất cả >= TIEN_CANH_BAO)
        if (!kiemTraDongNhat(cuaSo)) {
            // Có lần giảm → không đồng nhất → cần nhãn hành vi bổ trợ
            return KetQuaDanhGia.chuyenMaTran(
                String.format("Vùng không đồng nhất (%d bản ghi) – cần ma trận",
                    cuaSo.size())
            );
        }

        // Đồng nhất – kiểm tra đủ T chưa
        long khoangThoiGian = thoiGian - cuaSo.peekFirst().thoiGian;

        if (khoangThoiGian >= cuaSoThoiGianMs) {
            // Đồng nhất + đủ T → đủ thuyết phục
            TinhTrangVung mucCaoBaGio = layMucCaoNhat(cuaSo);
            MucDoNghiemTrong mucDo = mucCaoBaGio == TinhTrangVung.CANH_BAO
                ? MucDoNghiemTrong.CANH_BAO
                : MucDoNghiemTrong.GIAM_SAT;

            lichSuVung.remove(idCaThe);
            return KetQuaDanhGia.guiThangM5(
                mucDo,
                String.format("Vùng đồng nhất %.0f phút (cao nhất: %s) – gửi thẳng Module 5",
                    cuaSoThoiGianMs / 60_000.0, mucCaoBaGio)
            );
        }

        // Đồng nhất nhưng chưa đủ T – tiếp tục theo dõi
        return KetQuaDanhGia.dangTheoDoi(
            String.format("Vùng đồng nhất – đang tích lũy: %.1f/%.0f phút",
                khoangThoiGian / 60_000.0,
                cuaSoThoiGianMs / 60_000.0)
        );
    }

    // ── Logic nội bộ ──────────────────────────────────────────────────────

    /**
     * Kiểm tra tính đồng nhất của chuỗi vùng trong cửa sổ.
     *
     * Đồng nhất khi:
     *   1. Không có lần nào giảm so với lần trước (chỉ tăng hoặc giữ nguyên)
     *   2. Tất cả bản ghi đều >= TIEN_CANH_BAO
     *
     * Ví dụ đồng nhất:
     *   TIEN_CANH_BAO → CANH_BAO → CANH_BAO  ✓
     *   TIEN_CANH_BAO → TIEN_CANH_BAO         ✓
     * Ví dụ không đồng nhất:
     *   CANH_BAO → TIEN_CANH_BAO → CANH_BAO  ✗ (có lần giảm)
     */
    private boolean kiemTraDongNhat(Deque<BanGhiVung> cuaSo) {
        TinhTrangVung truoc = null;
        for (BanGhiVung b : cuaSo) {
            // Điều kiện 2: phải >= TIEN_CANH_BAO
            if (b.vung.ordinal() < TinhTrangVung.TIEN_CANH_BAO.ordinal()) {
                return false;
            }
            // Điều kiện 1: không giảm so với lần trước
            if (truoc != null && b.vung.ordinal() < truoc.ordinal()) {
                return false;
            }
            truoc = b.vung;
        }
        return true;
    }

    /**
     * Lấy mức vùng cao nhất trong cửa sổ để xác định mức độ nghiêm trọng.
     */
    private TinhTrangVung layMucCaoNhat(Deque<BanGhiVung> cuaSo) {
        TinhTrangVung caoNhat = TinhTrangVung.AN_TOAN;
        for (BanGhiVung b : cuaSo) {
            if (b.vung.ordinal() > caoNhat.ordinal()) {
                caoNhat = b.vung;
            }
        }
        return caoNhat;
    }

    // ── Cấu hình động ─────────────────────────────────────────────────────

    public void datCuaSoThoiGian(long cuaSoThoiGianMs) {
        this.cuaSoThoiGianMs = cuaSoThoiGianMs;
    }

    public void xoaCuaSo(String idCaThe) {
        lichSuVung.remove(idCaThe);
    }

    public int laySoCaTheDangTheoDoc() { return lichSuVung.size(); }
}

