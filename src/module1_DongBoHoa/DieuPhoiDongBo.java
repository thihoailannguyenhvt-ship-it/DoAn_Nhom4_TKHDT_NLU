package module1_DongBoHoa;
import dinhDanh.*;
// Lop dieu phoi toan bo qua trinh dong bo du lieu
public class DieuPhoiDongBo {

    private BoChuyenDoiDuLieu boChuyenDoi;
    private BoKiemTraDuLieu boKiemTra;
    private BoNoiSuyGPS boNoiSuy;
    private QuanLyThoiGian quanLyTime;
    private CauHinhDongBo cauHinh;

    // Do tre gia lap tu cam bien den he thong: 2 giay
    private static final long DO_TRE_HE_THONG = 2000;

    // Thoi gian xu ly gia lap cua Module 1: 1 giay
    private static final long THOI_GIAN_XU_LY_MODULE1 = 1000;

    public DieuPhoiDongBo(BoChuyenDoiDuLieu boChuyenDoi,
                          BoKiemTraDuLieu boKiemTra,
                          BoNoiSuyGPS boNoiSuy,
                          QuanLyThoiGian quanLyTime,
                          CauHinhDongBo cauHinh) {

        this.boChuyenDoi = boChuyenDoi;
        this.boKiemTra = boKiemTra;
        this.boNoiSuy = boNoiSuy;
        this.quanLyTime = quanLyTime;
        this.cauHinh = cauHinh;
    }

    // Thuc hien dong bo khi chi co mot dong CSV
    public BanGhiSinhHoc thucHienDongBo(String rawLine) throws NgoaiLeDuLieuLoi {

        // Chuyen doi gia toc
        BanGhiGiaTocBD acc = boChuyenDoi.chuyenDoiGiaToc(rawLine);

        // Kiem tra gia toc
        if (!boKiemTra.laGiaTocHopLe(acc)) {
            throw new NgoaiLeDuLieuLoi(rawLine);
        }

        // Lay thoi diem su kien
        long thoiDiemSuKien = acc.getThoiDiemSuKien();

        // Lay ID con vat
        String idConVat = trichID(rawLine);

        // Khai bao toa do
        ToaDo toaDo;

        // Khai bao trang thai
        TrangThaiDuLieu trangThai;

        // Neu dong CSV co viDo va kinhDo thi dung GPS trong file
        if (boChuyenDoi.coGPS(rawLine)) {

            // Lay toa do tu file CSV
            toaDo = boChuyenDoi.chuyenDoiToaDoTuDongCSV(rawLine);

            // Cap nhat bo dem GPS
            boNoiSuy.capNhatBoDem(toaDo, thoiDiemSuKien);

            // GPS trong file duoc xem la gia lap/thuc te dau vao
            trangThai = TrangThaiDuLieu.THUC_TE;

        } else {

            // Neu khong co GPS thi noi suy GPS
            toaDo = xuLyNoiSuy(thoiDiemSuKien);

            // Gan trang thai noi suy
            trangThai = TrangThaiDuLieu.NOI_SUY;
        }

        // Gia lap thoi diem he thong tiep nhan du lieu
        long mocThoiGianHeThong = thoiDiemSuKien + DO_TRE_HE_THONG;

        // Tao du lieu dong bo
        DuLieuDongBo dongBo = new DuLieuDongBo(
                idConVat,
                toaDo,
                acc,
                mocThoiGianHeThong,
                trangThai
        );

        // Dong goi ket qua
        return dongGoiKetQua(dongBo);
    }

    // Thuc hien quy trinh dong bo tu raw gia toc va raw GPS
    public BanGhiSinhHoc thucHienQuyTrinh(String accRaw, String gpsRaw) throws NgoaiLeDuLieuLoi {

        BanGhiGiaTocBD acc = boChuyenDoi.chuyenDoiGiaToc(accRaw);

        if (!boKiemTra.laGiaTocHopLe(acc)) {
            throw new NgoaiLeDuLieuLoi(accRaw);
        }

        long thoiDiemSuKien = acc.getThoiDiemSuKien();

        ToaDo toaDo;
        TrangThaiDuLieu trangThai;

        if (gpsRaw != null && !gpsRaw.trim().isEmpty()) {

            toaDo = boChuyenDoi.chuyenDoiToaDo(gpsRaw);

            boNoiSuy.capNhatBoDem(toaDo, thoiDiemSuKien);

            trangThai = TrangThaiDuLieu.THUC_TE;

        } else {

            toaDo = xuLyNoiSuy(thoiDiemSuKien);

            trangThai = TrangThaiDuLieu.NOI_SUY;
        }

        long mocThoiGianHeThong = thoiDiemSuKien + DO_TRE_HE_THONG;

        DuLieuDongBo dongBo = new DuLieuDongBo(
                trichID(accRaw),
                toaDo,
                acc,
                mocThoiGianHeThong,
                trangThai
        );

        return dongGoiKetQua(dongBo);
    }

    // Xu ly ban ghi sinh hoc theo du lieu da dong bo
    public BanGhiSinhHoc xuLyBanGhiSinhHocTienTheoDoi(DuLieuDongBo duLieu) {

        if (duLieu == null) {
            return null;
        }

        return dongGoiKetQua(duLieu);
    }

    // Xu ly noi suy GPS
    public ToaDo xuLyNoiSuy(long thoiDiem) {

        return boNoiSuy.tinhNoiSuy(thoiDiem);
    }

    // Kiem tra mat ket noi GPS
    public boolean kiemTraMatKetNoi(long thoiDiemCuoi, long thoiDiemHienTai) {

        long khoangCach = quanLyTime.tinhKhoangCach(thoiDiemCuoi, thoiDiemHienTai);

        return khoangCach > cauHinh.getGioiHanMatGPS();
    }

    // Dong goi ket qua thanh BanGhiSinhHoc
    public BanGhiSinhHoc dongGoiKetQua(DuLieuDongBo duLieuDongBo) {

        if (duLieuDongBo == null) {
            return null;
        }

        long mocThoiGianHeThong = duLieuDongBo.getThoiDiemDongBo();

        long t1 = mocThoiGianHeThong + THOI_GIAN_XU_LY_MODULE1;

        return new BanGhiSinhHoc(
                duLieuDongBo.getIdConVat(),
                duLieuDongBo.getToaDoHienTai(),
                duLieuDongBo.getGiaTocHienTai(),
                duLieuDongBo.getGiaTocHienTai().getThoiDiemSuKien(),
                mocThoiGianHeThong,
                duLieuDongBo.getTrangThai(),
                t1
        );
    }

    // Ham phu trich ID con vat
    private String trichID(String rawLine) {

        try {
            return boChuyenDoi.trichXuatIDConVat(rawLine);
        } catch (NgoaiLeDuLieuLoi e) {
            return "ANIMAL_001";
        }
    }
}