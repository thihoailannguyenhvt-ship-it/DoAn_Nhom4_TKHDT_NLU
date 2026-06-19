package module3_GiamSatVaQuanLyNguong.NhanHanhVi;

import dinhDanh.NhanTrangThai;
import dinhDanh.MucDoNghiemTrong;
import module3_GiamSatVaQuanLyNguong.In.*;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

/**
 * Bộ đánh giá hành vi – phần [A]: xét độc lập nhãn hành vi.
 *
 * Ba nhánh xử lý rõ ràng:
 * SUY_KIET    → guiThangM5 CANH_BAO ngay, không qua cửa sổ
 * BINH_THUONG → xóa cửa sổ, dangTheoDoi
 * DINH_BAY    → tích lũy cửa sổ 15 phút:
 * đồng nhất đủ thời gian → guiThangM5 KHAN_CAP
 * không đồng nhất        → chuyenMaTran
 * chưa đủ thời gian      → dangTheoDoi
 */
public class BoDanhGiaHanhVi {

    private long cuaSoThoiGianMs;

    /** Key: idCaThe → lịch sử nhãn trong cửa sổ trượt */
    private final Map<String, Deque<BanGhiNhan>> lichSuNhan;

    private static class BanGhiNhan {
        final NhanTrangThai nhan;
        final long          thoiGian;

        BanGhiNhan(NhanTrangThai nhan, long thoiGian) {
            this.nhan     = nhan;
            this.thoiGian = thoiGian;
        }
    }

    public BoDanhGiaHanhVi() {
        this(900_000L); // 15 phút
    }

    public BoDanhGiaHanhVi(long cuaSoThoiGianMs) {
        this.cuaSoThoiGianMs = cuaSoThoiGianMs;
        this.lichSuNhan      = new HashMap<>();
    }

    public KetQuaDanhGia danhGia(BanGhiSinhHoc banGhi) {
        NhanTrangThai nhan    = banGhi.layNhanTrangThai();
        String        idCaThe = banGhi.layIdCaThe();
        long          thoiGian = System.currentTimeMillis();

        // ── Nhánh 1: SUY_KIET – phát ngay, không cần cửa sổ ──────────────
        if (nhan == NhanTrangThai.SUY_KIET) {
            lichSuNhan.remove(idCaThe); // dọn cửa sổ cũ nếu có
            return KetQuaDanhGia.guiThangM5(
                MucDoNghiemTrong.CANH_BAO,
                "SUY_KIET phát hiện – gửi thẳng Module 5",
                banGhi.layIdVung(),
                banGhi.layLoaiVung(),
                banGhi.layPhanTramGiaoThoa()
            );
        }

        // ── Nhánh 2: BINH_THUONG – reset cửa sổ, chờ tiếp ────────────────
        if (nhan == NhanTrangThai.BINH_THUONG) {
            lichSuNhan.remove(idCaThe);
            return KetQuaDanhGia.dangTheoDoi("BINH_THUONG – cửa sổ đã reset");
        }

        // ── Nhánh 3: DINH_BAY – tích lũy và kiểm tra cửa sổ ──────────────
        Deque<BanGhiNhan> cuaSo = lichSuNhan
            .computeIfAbsent(idCaThe, k -> new ArrayDeque<>());

        // Loại bản ghi cũ hơn 15 phút (cửa sổ trượt theo thời gian thực)
        long nguong = thoiGian - cuaSoThoiGianMs;
        while (!cuaSo.isEmpty() && cuaSo.peekFirst().thoiGian < nguong) {
            cuaSo.pollFirst();
        }

        cuaSo.addLast(new BanGhiNhan(nhan, thoiGian));

        // Kiểm tra tính đồng nhất trước
        if (!kiemTraDongNhat(cuaSo)) {
            // Có nhãn khác xen vào → không đủ thuyết phục → chuyển ma trận
            return KetQuaDanhGia.chuyenMaTran(
                String.format("Nhãn không đồng nhất trong cửa sổ (%d bản ghi) – cần ma trận",
                    cuaSo.size())
            );
        }

        // Đồng nhất toàn DINH_BAY – kiểm tra đủ 15 phút chưa
        long thoiGianBatDau = cuaSo.peekFirst().thoiGian;
        long khoangThoiGian = thoiGian - thoiGianBatDau;

        if (khoangThoiGian >= cuaSoThoiGianMs) {
            lichSuNhan.remove(idCaThe);
            return KetQuaDanhGia.guiThangM5(
                MucDoNghiemTrong.KHAN_CAP,
                String.format("DINH_BAY đồng nhất liên tục %.0f phút – xác nhận dính bẫy",
                    cuaSoThoiGianMs / 60_000.0),
                banGhi.layIdVung(),
                banGhi.layLoaiVung(),
                banGhi.layPhanTramGiaoThoa()
            );
        }

        // Đồng nhất nhưng chưa đủ thời gian – tiếp tục theo dõi
        return KetQuaDanhGia.dangTheoDoi(
            String.format("DINH_BAY đồng nhất – đang tích lũy: %.1f/%.0f phút",
                khoangThoiGian / 60_000.0,
                cuaSoThoiGianMs / 60_000.0)
        );
    }

    /** Kiểm tra toàn bộ cửa sổ có toàn DINH_BAY không */
    private boolean kiemTraDongNhat(Deque<BanGhiNhan> cuaSo) {
        for (BanGhiNhan b : cuaSo) {
            if (b.nhan != NhanTrangThai.DINH_BAY) return false;
        }
        return true;
    }

    public void datCuaSoThoiGian(long cuaSoThoiGianMs) {
        this.cuaSoThoiGianMs = cuaSoThoiGianMs;
    }

    public void xoaCuaSo(String idCaThe) {
        lichSuNhan.remove(idCaThe);
    }

    public int laySoCaTheDangTheoDoc() { return lichSuNhan.size(); }
}
