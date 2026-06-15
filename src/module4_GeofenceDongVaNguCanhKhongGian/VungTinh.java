package module4_GeofenceDongVaNguCanhKhongGian;

public class VungTinh extends VungKhongGian {

    private String maVung;
    private String loaiVung;
    private String hinhHoc;
    private int mucDoRuiRo;
    private Diem toaDo;
    private double banKinh;

    public VungTinh(String maVung, String loaiVung, String hinhHoc, int mucDoRuiRo, Diem toaDo) {
        this.maVung = maVung;
        this.loaiVung = loaiVung;
        this.hinhHoc = hinhHoc;
        this.mucDoRuiRo = mucDoRuiRo;
        this.toaDo = toaDo;
        this.banKinh = 0.005;
    }

    public VungTinh(String maVung, String loaiVung, String hinhHoc,
                    int mucDoRuiRo, Diem toaDo, double banKinh) {
        this.maVung = maVung;
        this.loaiVung = loaiVung;
        this.hinhHoc = hinhHoc;
        this.mucDoRuiRo = mucDoRuiRo;
        this.toaDo = toaDo;
        this.banKinh = banKinh;
    }

    public String getMaVung() {
        return maVung;
    }

    public String getLoaiVung() {
        return loaiVung;
    }

    public String getHinhHoc() {
        return hinhHoc;
    }

    public int getMucDoRuiRo() {
        return mucDoRuiRo;
    }

    public Diem getToaDo() {
        return toaDo;
    }

    public void setDiem(Diem diem) {
        this.toaDo = diem;
    }

    public void setToaDo(Diem toaDo) {
        this.toaDo = toaDo;
    }

    public double getBanKinh() {
        return banKinh;
    }

    @Override
    public double tinhToanGiaoThoa(GeofenceDong geofence) {

        double d = tinhKhoangCach(toaDo, geofence.getTamHienTai());
        double r1 = banKinh;
        double r2 = geofence.getBanKinhR();

        if (d >= r1 + r2) {
            return 0;
        }

        double mucGan = 1.0 - (d / (r1 + r2));
        return Math.max(0, Math.min(100, mucGan * 100));
    }

    private double tinhKhoangCach(Diem a, Diem b) {
        double dx = a.getKinhDo() - b.getKinhDo();
        double dy = a.getViDo() - b.getViDo();

        return Math.sqrt(dx * dx + dy * dy);
    }
}
