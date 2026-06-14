package module2;

// Lop phan tich hanh vi cua tung ca the dong vat
public class BoPhanTichCaThe implements IPhanTichHanhVi {

    private DongVat idDvat;
    private BoTinhToanSinhHoc boTinhToan;
    private CuaSoTruot cuaSoDinhBay;
    private CuaSoTruot cuaSoSuyKiet;
    private BoXuLyQuyTac boXuLyQuyTac;
    private BangGhiSinhHoc duLieuMoiNhat;

    public BoPhanTichCaThe(DongVat idDvat) {
        super();

        this.idDvat = idDvat;
        this.boTinhToan = new BoTinhToanSinhHoc();

        // Cua so 10 phut cho quy tac dinh bay
        this.cuaSoDinhBay = new CuaSoTruot(1000);

        // Cua so 12 gio cho quy tac suy kiet
        this.cuaSoSuyKiet = new CuaSoTruot(5000);

        this.boXuLyQuyTac = new BoXuLyQuyTac();
        
     // Khởi tạo các quy tắc bên trong Facade
        boXuLyQuyTac.themQuyTac(
                new QuyTacDinhBay(1));

        boXuLyQuyTac.themQuyTac(
                new QuyTacSuyKiet(2));
    }

   

    // Tiep nhan du lieu tu file KetQuaModule1.csv
    public void tiepNhanDuLieu(BangGhiSinhHoc data) {

        duLieuMoiNhat = data;

        cuaSoDinhBay.tiepNhan(data);
        cuaSoSuyKiet.tiepNhan(data);

        long now = data.getMocThoiGianSuKien();

        // Giu lai 10 phut gan nhat
        cuaSoDinhBay.quetVaLoaiBo(now - 10 * 60 * 1000);

        // Giu lai 12 gio gan nhat
        cuaSoSuyKiet.quetVaLoaiBo(now - 12 * 60 * 60 * 1000);
    }

    @Override
    public KetQuaHanhVi thucHienPhanTich(ThuVienDinhMucSinhHoc dinhMuc) {
    	idDvat.seteTichLuy(
                boTinhToan.tinhTongODBA(cuaSoSuyKiet.layDanhSachHienTai())
        );

        KetQuaHanhVi kq =
                boXuLyQuyTac.danhGiaTatCa(
                        idDvat,
                        cuaSoDinhBay,
                        cuaSoSuyKiet,
                        dinhMuc
                );

        double odba =
                boTinhToan.tinhODBA(cuaSoDinhBay.layDanhSachHienTai());

        double nangLuong =
                boTinhToan.tinhTyLeNangLuong(
                        cuaSoSuyKiet.layDanhSachHienTai(),
                        idDvat.getLoai()
                );

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