package module1_DongBoHoa;

// Lop luu du lieu da duoc dong bo
public class DuLieuDongBo {

    // Ma dinh danh con vat
    private String idConVat;

    // Toa do hien tai
    private ToaDo toaDoHienTai;

    // Ban ghi gia toc hien tai
    private BanGhiGiaTocBD giaTocHienTai;

    // Thoi diem dong bo
    private long thoiDiemDongBo;

    // Trang thai du lieu
    private TrangThaiDuLieu trangThai;

    // Ham khoi tao du lieu dong bo
    public DuLieuDongBo(String idConVat, ToaDo toaDoHienTai,
                        BanGhiGiaTocBD giaTocHienTai, long thoiDiemDongBo,
                        TrangThaiDuLieu trangThai) {

        this.idConVat = idConVat;
        this.toaDoHienTai = toaDoHienTai;
        this.giaTocHienTai = giaTocHienTai;
        this.thoiDiemDongBo = thoiDiemDongBo;
        this.trangThai = trangThai;
    }

    public String getIdConVat() {
        return idConVat;
    }

    public ToaDo getToaDoHienTai() {
        return toaDoHienTai;
    }

    public BanGhiGiaTocBD getGiaTocHienTai() {
        return giaTocHienTai;
    }

    public long getThoiDiemDongBo() {
        return thoiDiemDongBo;
    }

    public TrangThaiDuLieu getTrangThai() {
        return trangThai;
    }
}