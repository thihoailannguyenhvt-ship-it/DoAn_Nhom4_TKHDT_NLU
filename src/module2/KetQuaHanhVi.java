package module2;

// Lop luu ket qua phan tich hanh vi cua Module 2
public class KetQuaHanhVi {

    private NhanTrangThai nhanTrangThai;

    // Thoi diem su kien cua ban ghi dau vao
    private long t1;

    // Thoi diem Module 2 hoan thanh phan tich
    private long t2;

    private String idConVat;

    private double chiSoODBA;

    private double tyLePhanTramNangLuong;

    public KetQuaHanhVi(NhanTrangThai nhanTrangThai) {

        this.nhanTrangThai = nhanTrangThai;
        this.idConVat = "";
        this.chiSoODBA = 0;
        this.tyLePhanTramNangLuong = 0;
        this.t1 = 0;
        this.t2 = System.currentTimeMillis();
    }

    public KetQuaHanhVi(NhanTrangThai nhanTrangThai,
                        long t1,
                        long t2,
                        String idConVat,
                        double chiSoODBA,
                        double tyLePhanTramNangLuong) {

        this.nhanTrangThai = nhanTrangThai;
        this.t1 = t1;
        this.t2 = t2;
        this.idConVat = idConVat;
        this.chiSoODBA = chiSoODBA;
        this.tyLePhanTramNangLuong = tyLePhanTramNangLuong;
    }

    public NhanTrangThai getNhanTrangThai() {
        return nhanTrangThai;
    }

    public void setNhanTrangThai(NhanTrangThai nhanTrangThai) {
        this.nhanTrangThai = nhanTrangThai;
    }

    public long getT1() {
        return t1;
    }

    public void setT1(long t1) {
        this.t1 = t1;
    }

    public long getT2() {
        return t2;
    }

    public void setT2(long t2) {
        this.t2 = t2;
    }

    public String getIdConVat() {
        return idConVat;
    }

    public void setIdConVat(String idConVat) {
        this.idConVat = idConVat;
    }

    public double getChiSoODBA() {
        return chiSoODBA;
    }

    public void setChiSoODBA(double chiSoODBA) {
        this.chiSoODBA = chiSoODBA;
    }

    public double getTyLePhanTramNangLuong() {
        return tyLePhanTramNangLuong;
    }

    public void setTyLePhanTramNangLuong(double tyLePhanTramNangLuong) {
        this.tyLePhanTramNangLuong = tyLePhanTramNangLuong;
    }

    public long getDoTreModule2() {
        return t2 - t1;
    }

    public String toCSV() {

        return idConVat + ","
                + nhanTrangThai + ","
                + chiSoODBA + ","
                + tyLePhanTramNangLuong + ","
                + t1 + ","
                + t2 + ","
                + getDoTreModule2();
    }

    @Override
    public String toString() {

        return "KetQuaHanhVi["
                + "idConVat=" + idConVat
                + ", trangThai=" + nhanTrangThai
                + ", ODBA=" + chiSoODBA
                + ", tyLeNangLuong=" + tyLePhanTramNangLuong
                + ", t1=" + t1
                + ", t2=" + t2
                + ", doTreModule2=" + getDoTreModule2() + " ms"
                + "]";
    }
}