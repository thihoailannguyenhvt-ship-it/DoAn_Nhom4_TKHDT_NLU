package module5_CanhBaoVaDieuPhoi;

public class CanhBao {

    private String maCanhBao;
    private String maDongVat;
    private String loaiSuCo;

    // Ten dia diem hien thi tren giao dien
    private String diaDiem;

    // Toa do van duoc giu de tinh kiem lam gan nhat
    private Diem viTri;

    private long thoiDiem;
    private MucDoCanhBao mucDoNguyHiem;
    private String trangThai;
    private String kiemLamPhuTrach;

    public CanhBao(String maCanhBao, String maDongVat, String loaiSuCo,
                   String diaDiem, Diem viTri, long thoiDiem,
                   MucDoCanhBao mucDoNguyHiem, String trangThai,
                   String kiemLamPhuTrach) {

        this.maCanhBao = maCanhBao;
        this.maDongVat = maDongVat;
        this.loaiSuCo = loaiSuCo;
        this.diaDiem = diaDiem;
        this.viTri = viTri;
        this.thoiDiem = thoiDiem;
        this.mucDoNguyHiem = mucDoNguyHiem;
        this.trangThai = trangThai;
        this.kiemLamPhuTrach = kiemLamPhuTrach;
    }

    // Constructor cu de tranh loi neu class khac van goi kieu cu
    public CanhBao(String maCanhBao, String maDongVat, String loaiSuCo,
                   Diem viTri, long thoiDiem,
                   MucDoCanhBao mucDoNguyHiem, String trangThai,
                   String kiemLamPhuTrach) {

        this(maCanhBao, maDongVat, loaiSuCo, "Khong xac dinh",
                viTri, thoiDiem, mucDoNguyHiem, trangThai, kiemLamPhuTrach);
    }

    public String getMaCanhBao() {
        return maCanhBao;
    }

    public void setMaCanhBao(String maCanhBao) {
        this.maCanhBao = maCanhBao;
    }

    public String getMaDongVat() {
        return maDongVat;
    }

    public void setMaDongVat(String maDongVat) {
        this.maDongVat = maDongVat;
    }

    public String getLoaiSuCo() {
        return loaiSuCo;
    }

    public void setLoaiSuCo(String loaiSuCo) {
        this.loaiSuCo = loaiSuCo;
    }

    public String getDiaDiem() {
        return diaDiem;
    }

    public void setDiaDiem(String diaDiem) {
        this.diaDiem = diaDiem;
    }

    public Diem getViTri() {
        return viTri;
    }

    public void setViTri(Diem viTri) {
        this.viTri = viTri;
    }

    public long getThoiDiem() {
        return thoiDiem;
    }

    public void setThoiDiem(long thoiDiem) {
        this.thoiDiem = thoiDiem;
    }

    public MucDoCanhBao getMucDoNguyHiem() {
        return mucDoNguyHiem;
    }

    public void setMucDoNguyHiem(MucDoCanhBao mucDoNguyHiem) {
        this.mucDoNguyHiem = mucDoNguyHiem;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getKiemLamPhuTrach() {
        return kiemLamPhuTrach;
    }

    public void setKiemLamPhuTrach(String kiemLamPhuTrach) {
        this.kiemLamPhuTrach = kiemLamPhuTrach;
    }
}
