package module5_CanhBaoVaDieuPhoi;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class CanhBaoVaDieuPhoi implements IDichVuCanhBao, IPhatLenhDieuPhoi {

    private List<KiemLam> danhSachKiemLamDangTruc;
    private BoLocKiemLamGanNhat boLocKiemLam;
    private String congTruyenTin;

    private List<CanhBao> dsCanhBao;
    private List<LenhDieuPhoi> dsLenh;

    public CanhBaoVaDieuPhoi() {
        this.danhSachKiemLamDangTruc = new ArrayList<KiemLam>();
        this.boLocKiemLam = new BoLocKiemLamGanNhat();
        this.congTruyenTin = "SMS + App + Radio";
        this.dsCanhBao = new ArrayList<CanhBao>();
        this.dsLenh = new ArrayList<LenhDieuPhoi>();
    }

    // Khoi tao danh sach kiem lam mac dinh
    public void khoiTaoKiemLamMacDinh() {
        danhSachKiemLamDangTruc.clear();

        danhSachKiemLamDangTruc.add(new KiemLam(
                "KL-001", "Nguyen Thi Hoai Lan",
                new Diem(107.442002, 11.435684),
                true, "DEV_001", "Khu dan cu"
        ));

        danhSachKiemLamDangTruc.add(new KiemLam(
                "KL-002", "Nguyen Vo Huyen Tran",
                new Diem(107.450240, 11.445166),
                true, "DEV_002", "Bia rung Dong Bac"
        ));

        danhSachKiemLamDangTruc.add(new KiemLam(
                "KL-003", "Nguyen Thi Nhu Huynh",
                new Diem(107.438634, 11.429440),
                true, "DEV_003", "Tuyen duong du lich"
        ));

        danhSachKiemLamDangTruc.add(new KiemLam(
                "KL-004", "Lam Thi Hoang Nhu",
                new Diem(107.459740, 11.453500),
                true, "DEV_004", "Khu bao ton trung tam"
        ));

        // Gia lap mot so kiem lam dang ban
        danhSachKiemLamDangTruc.get(0).setDangRanh(false);
        danhSachKiemLamDangTruc.get(2).setDangRanh(false);
    }

    // Controller doc ket qua Module 3
    public void khoiTaoDuLieuTuModule3(String duongDanFile) {
        dsCanhBao.clear();
        dsLenh.clear();

        BoDocKetQuaModule3 boDoc = new BoDocKetQuaModule3();
        List<CanhBao> ketQua = boDoc.docDanhSachCanhBao(duongDanFile);

        dsCanhBao.addAll(ketQua);
        taoLenhDieuPhoiTuCanhBao(20);
    }

    // Tao lenh dieu phoi tu canh bao
    public void taoLenhDieuPhoiTuCanhBao(int soLenhToiDa) {
        dsLenh.clear();

        int demLenh = 1;

        for (int i = 0; i < dsCanhBao.size(); i++) {
            CanhBao cb = dsCanhBao.get(i);

            if (cb.getMucDoNguyHiem() == MucDoCanhBao.NGUY_CAP
                    || cb.getMucDoNguyHiem() == MucDoCanhBao.CANH_BAO) {

                LenhDieuPhoi lenh = new LenhDieuPhoi(
                        "LD-" + String.format("%03d", demLenh + 88),
                        cb.getMaCanhBao(),
                        cb.getKiemLamPhuTrach(),
                        "08:" + String.format("%02d", 20 + demLenh),
                        "Da phat hanh",
                        "Di chuyen den " + cb.getDiaDiem() + " de xu ly canh bao.",
                        "Lenh duoc tao tu ket qua Module 3."
                );

                dsLenh.add(lenh);
                demLenh++;

                if (demLenh > soLenhToiDa) {
                    break;
                }
            }
        }
    }

    @Override
    public void tiepNhanCanhBao(CanhBao suCo) {
        dsCanhBao.add(suCo);

        KiemLam kl = boLocKiemLam.timKiemLamGanNhat(
                suCo.getViTri(),
                danhSachKiemLamDangTruc
        );

        if (kl != null) {
            suCo.setKiemLamPhuTrach(kl.getTenKiemLam());
            suCo.setTrangThai("Dang xu ly");
        }
    }

    public void guiLenhThucThi(LenhDieuPhoi lenh) {
        if (lenh != null && !dsLenh.contains(lenh)) {
            dsLenh.add(lenh);
        }
    }

    @Override
    public void phatLenh(LenhDieuPhoi lenh, KiemLam kiemLam) {
        if (lenh == null || kiemLam == null) {
            return;
        }

        lenh.guiDenThietBi(kiemLam);
        kiemLam.xacNhanTiepNhan(lenh.getMaLenh());
    }

    // Nghiep vu phat lenh nhanh
    public LenhDieuPhoi phatLenhChoCanhBaoDauTien() {
        if (dsCanhBao.isEmpty()) {
            return null;
        }

        CanhBao cb = dsCanhBao.get(0);

        KiemLam kl = boLocKiemLam.timKiemLamGanNhat(
                cb.getViTri(),
                danhSachKiemLamDangTruc
        );

        if (kl == null) {
            return null;
        }

        LenhDieuPhoi lenh = new LenhDieuPhoi(
                "LD-" + String.format("%03d", dsLenh.size() + 90),
                cb.getMaCanhBao(),
                kl.getTenKiemLam(),
                "08:35",
                "Da phat hanh",
                "Di chuyen den " + cb.getDiaDiem() + " de xu ly canh bao.",
                "Lenh duoc tao tu Controller Module 5."
        );

        dsLenh.add(lenh);

        cb.setTrangThai("Dang xu ly");
        cb.setKiemLamPhuTrach(kl.getTenKiemLam());
        kl.setDangRanh(false);

        phatLenh(lenh, kl);

        return lenh;
    }

    // Nghiep vu hoan thanh canh bao dau tien
    public CanhBao hoanThanhCanhBaoDauTien() {
        if (dsCanhBao.isEmpty()) {
            return null;
        }

        CanhBao cb = dsCanhBao.get(0);
        cb.setTrangThai("Hoan thanh");
        return cb;
    }

    // Cap nhat tat ca kiem lam thanh san sang
    public void datTatCaKiemLamSanSang() {
        for (KiemLam kl : danhSachKiemLamDangTruc) {
            kl.setDangRanh(true);
        }
    }

    // Cap nhat cau hinh he thong
    public void capNhatCauHinh(double banKinhQuet, String congTruyenTinMoi) {
        boLocKiemLam.setBanKinhQuetMacDinh(banKinhQuet);
        this.congTruyenTin = congTruyenTinMoi;
    }

    // Controller tao noi dung popup chi tiet
    public String taoNoiDungChiTietCanhBao(CanhBao cb) {
        if (cb == null) {
            return "Khong co du lieu canh bao.";
        }

        DiaDiemBaoTon dd = timDiaDiemBaoTon(cb.getDiaDiem());

        return "Ma canh bao: " + cb.getMaCanhBao() + "\n"
                + "Ma dong vat: " + cb.getMaDongVat() + "\n"
                + "Loai su co: " + cb.getLoaiSuCo() + "\n\n"
                + "THONG TIN KHU VUC BAO TON\n"
                + "Ma dia diem: " + dd.getMaDiaDiem() + "\n"
                + "Ten khu vuc: " + dd.getTenKhuVuc() + "\n"
                + "Muc rui ro khu vuc: " + dd.getMucDoRuiRo() + "\n"
                + "So camera giam sat: " + dd.getSoLuongCamera() + "\n"
                + "So kiem lam phu trach: " + dd.getSoKiemLamPhuTrach() + "\n"
                + "Ghi chu: " + dd.getGhiChu() + "\n\n"
                + "THONG TIN XU LY\n"
                + "Muc do canh bao: " + cb.getMucDoNguyHiem().getLabel() + "\n"
                + "Trang thai: " + cb.getTrangThai() + "\n"
                + "Kiem lam phu trach: " + cb.getKiemLamPhuTrach();
    }

    public DiaDiemBaoTon timDiaDiemBaoTon(String tenDiaDiem) {
        if (tenDiaDiem.equals("Khu dan cu")) {
            return new DiaDiemBaoTon("KV-001", "Khu dan cu", "Nguy cap",
                    new Color(220, 75, 75), 12, 4,
                    "Khu vuc gan dan cu, uu tien gui thang Module 5.");
        }

        if (tenDiaDiem.equals("Bia rung Dong Bac")) {
            return new DiaDiemBaoTon("KV-002", "Bia rung Dong Bac", "Canh bao",
                    new Color(225, 160, 55), 8, 3,
                    "Khu vuc tiep giap vung dem, can theo doi lien tuc.");
        }

        if (tenDiaDiem.equals("Tuyen duong du lich")) {
            return new DiaDiemBaoTon("KV-003", "Tuyen duong du lich", "Nguy cap",
                    new Color(220, 75, 75), 10, 5,
                    "Khu vuc co khach tham quan, can dieu phoi nhanh.");
        }

        if (tenDiaDiem.equals("Khu bao ton trung tam")) {
            return new DiaDiemBaoTon("KV-004", "Khu bao ton trung tam", "Canh bao",
                    new Color(225, 160, 55), 15, 6,
                    "Khu vuc co mat do cam bien cao, du lieu cap nhat lien tuc.");
        }

        if (tenDiaDiem.equals("Khu Dong Nam")) {
            return new DiaDiemBaoTon("KV-005", "Khu Dong Nam", "Canh bao",
                    new Color(225, 160, 55), 6, 2,
                    "Khu vuc xa trung tam, can tinh toan kiem lam gan nhat.");
        }

        if (tenDiaDiem.equals("Tram quan sat so 2")) {
            return new DiaDiemBaoTon("KV-006", "Tram quan sat so 2", "Binh thuong",
                    Color.GRAY, 5, 2,
                    "Khu vuc co tram quan sat co dinh, muc rui ro hien tai thap.");
        }

        return new DiaDiemBaoTon("KV-000", tenDiaDiem, "Khong xac dinh",
                Color.GRAY, 0, 0,
                "Chua co du lieu chi tiet ve khu vuc nay.");
    }

    public List<KiemLam> getDanhSachKiemLamDangTruc() {
        return danhSachKiemLamDangTruc;
    }

    public void setDanhSachKiemLamDangTruc(List<KiemLam> danhSachKiemLamDangTruc) {
        this.danhSachKiemLamDangTruc = danhSachKiemLamDangTruc;
    }

    public BoLocKiemLamGanNhat getBoLocKiemLam() {
        return boLocKiemLam;
    }

    public void setBoLocKiemLam(BoLocKiemLamGanNhat boLocKiemLam) {
        this.boLocKiemLam = boLocKiemLam;
    }

    public String getCongTruyenTin() {
        return congTruyenTin;
    }

    public void setCongTruyenTin(String congTruyenTin) {
        this.congTruyenTin = congTruyenTin;
    }

    public List<CanhBao> getDsCanhBao() {
        return dsCanhBao;
    }

    public List<LenhDieuPhoi> getDsLenh() {
        return dsLenh;
    }
}
