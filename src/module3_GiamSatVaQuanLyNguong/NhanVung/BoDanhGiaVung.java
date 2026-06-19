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
 */
public class BoDanhGiaVung {

    private long cuaSoThoiGianMs;

    /** Key: idConVat → lịch sử tình trạng vùng trong cửa sổ trượt */
    private final Map<String, Deque<BanGhiVung>> lichSuVung;

    // ── Inner class ────────────────────────────────────────────────────────
    private static class BanGhiVung {
        final TinhTrangVung vung;
        final long          thoiGian; // Giữ nguyên kiểu long để tính toán cửa sổ trượt chính xác

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
        TinhTrangVung vung     = banGhi.layTinhTrangVung();
        String        idConVat = banGhi.layIdCaThe();
        
        // SỬA ĐỔI: Nhận chuỗi ngày tháng String từ gói tin
        String thoiGianStr     = banGhi.layThoiGianSuKien(); 
        
        // SỬA ĐỔI: Chuyển đổi chuỗi ngày tháng sang số long (mili-giây) để chạy thuật toán cửa sổ trượt
        long thoiGian          = chuyenDoiStringVeLong(thoiGianStr); 

        // ── Ngoại lệ: NGUY_CAP – phát ngay, không qua cửa sổ ─────────────
        if (vung == TinhTrangVung.NGUY_CAP) {
            lichSuVung.remove(idConVat);
            // Đã bổ sung dữ liệu không gian
            return KetQuaDanhGia.guiThangM5(
                MucDoNghiemTrong.KHAN_CAP,
                "Vùng NGUY_CAP – con vật ở khu dân cư, gửi thẳng Module 5",
                banGhi.layIdVung(),
                banGhi.layLoaiVung(),
                banGhi.layPhanTramGiaoThoa()
            );
        }

        // ── AN_TOAN – reset cửa sổ, chờ tiếp ─────────────────────────────
        if (vung == TinhTrangVung.AN_TOAN) {
            lichSuVung.remove(idConVat);
            return KetQuaDanhGia.dangTheoDoi(
                "AN_TOAN – cửa sổ vùng đã reset"
            );
        }

        // ── TIEN_CANH_BAO / CANH_BAO – tích lũy cửa sổ ───────────────────
        Deque<BanGhiVung> cuaSo = lichSuVung
            .computeIfAbsent(idConVat, k -> new ArrayDeque<>());

        // Loại bản ghi cũ hơn T (cửa sổ trượt theo thời gian sự kiện) -> LOGIC ĐƯỢC GIỮ NGUYÊN HOÀN TOÀN
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

            lichSuVung.remove(idConVat);
            // Đã bổ sung dữ liệu không gian
            return KetQuaDanhGia.guiThangM5(
                mucDo,
                String.format("Vùng đồng nhất %.0f phút (cao nhất: %s) – gửi thẳng Module 5",
                    cuaSoThoiGianMs / 60_000.0, mucCaoBaGio),
                banGhi.layIdVung(),
                banGhi.layLoaiVung(),
                banGhi.layPhanTramGiaoThoa()
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

    private boolean kiemTraDongNhat(Deque<BanGhiVung> cuaSo) {
        TinhTrangVung truoc = null;
        for (BanGhiVung b : cuaSo) {
            if (b.vung.ordinal() < TinhTrangVung.TIEN_CANH_BAO.ordinal()) {
                return false;
            }
            if (truoc != null && b.vung.ordinal() < truoc.ordinal()) {
                return false;
            }
            truoc = b.vung;
        }
        return true;
    }

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

    public void xoaCuaSo(String idConVat) {
        lichSuVung.remove(idConVat);
    }

    public int laySoConVatDangTheoDoi() { 
        return lichSuVung.size(); 
    }

    /**
     * HÀM TIỆN ÍCH BỔ SUNG: Chuyển đổi an toàn chuỗi ngày tháng "yyyy-MM-dd HH:mm:ss" 
     * sang số long (Epoch mili-giây) phục vụ tính toán khoảng thời gian trượt.
     */
    private long chuyenDoiStringVeLong(String thoiGianStr) {
        if (thoiGianStr == null || thoiGianStr.trim().isEmpty()) return 0L;
        try {
            java.time.format.DateTimeFormatter formatter = 
                java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                                                   .withZone(java.time.ZoneId.systemDefault());
            java.time.LocalDateTime ldt = java.time.LocalDateTime.parse(thoiGianStr.trim(), formatter);
            return ldt.atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli();
        } catch (Exception e) {
            // Cơ chế phòng vệ: Nếu chuỗi đưa vào vô tình là chuỗi số mili-giây thuần túy
            try {
                return Long.parseLong(thoiGianStr.trim());
            } catch (NumberFormatException nfe) {
                return 0L;
            }
        }
    }
}
