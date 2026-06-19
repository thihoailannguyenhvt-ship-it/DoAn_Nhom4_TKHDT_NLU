package module5_CanhBaoVaDieuPhoi;

public class ThongBaoKiemLam {

    private String idConVat;
    private String loaiDoiTuong;
    private String trangThaiSucKhoe;
    private Diem toaDoHienTai;
    private MucDoCanhBao mucDoXamLan;

    public ThongBaoKiemLam(String idConVat, String loaiDoiTuong,
                           String trangThaiSucKhoe, Diem toaDoHienTai,
                           MucDoCanhBao mucDoXamLan) {
        this.idConVat = idConVat;
        this.loaiDoiTuong = loaiDoiTuong;
        this.trangThaiSucKhoe = trangThaiSucKhoe;
        this.toaDoHienTai = toaDoHienTai;
        this.mucDoXamLan = mucDoXamLan;
    }

    public String getMaThongBaoGoc() {
        return idConVat;
    }

    public String getIdConVat() {
        return idConVat;
    }

    public void setIdConVat(String idConVat) {
        this.idConVat = idConVat;
    }

    public String getLoaiDoiTuong() {
        return loaiDoiTuong;
    }

    public void setLoaiDoiTuong(String loaiDoiTuong) {
        this.loaiDoiTuong = loaiDoiTuong;
    }

    public String getTrangThaiSucKhoe() {
        return trangThaiSucKhoe;
    }

    public void setTrangThaiSucKhoe(String trangThaiSucKhoe) {
        this.trangThaiSucKhoe = trangThaiSucKhoe;
    }

    public Diem getToaDoHienTai() {
        return toaDoHienTai;
    }

    public void setToaDoHienTai(Diem toaDoHienTai) {
        this.toaDoHienTai = toaDoHienTai;
    }

    public MucDoCanhBao getMucDoXamLan() {
        return mucDoXamLan;
    }

    public void setMucDoXamLan(MucDoCanhBao mucDoXamLan) {
        this.mucDoXamLan = mucDoXamLan;
    }
}
