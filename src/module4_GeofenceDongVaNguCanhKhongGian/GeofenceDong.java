package module4_GeofenceDongVaNguCanhKhongGian;

import java.util.List;

public class GeofenceDong {

    private Diem tamHienTai;
    private double banKinhR;
    private List<Diem> dsDinh;

    public GeofenceDong(Diem tamHienTai, double banKinhR, List<Diem> dsDinh) {
        this.tamHienTai = tamHienTai;
        this.banKinhR = banKinhR;
        this.dsDinh = dsDinh;
    }

    public Diem getTamHienTai() {
        return tamHienTai;
    }

    public void setTamHienTai(Diem tamHienTai) {
        this.tamHienTai = tamHienTai;
    }

    public double getBanKinhR() {
        return banKinhR;
    }

    public void setBanKinhR(double banKinhR) {
        this.banKinhR = banKinhR;
    }

    public List<Diem> getDsDinh() {
        return dsDinh;
    }

    public void setDsDinh(List<Diem> dsDinh) {
        this.dsDinh = dsDinh;
    }

    public void capNhatTam(Diem viTriMoi) {
        this.tamHienTai = viTriMoi;
    }

    public void capNhatBanKinh(double banKinhMoi) {
        this.banKinhR = banKinhMoi;
    }

    public double layBanKinh() {
        return banKinhR;
    }

    public List<Diem> getDanhSachDiem() {
        return dsDinh;
    }

    public double tinhDienTichDaGiac() {
        if (dsDinh == null || dsDinh.size() < 3) {
            return 0;
        }

        double tong1 = 0;
        double tong2 = 0;

        for (int i = 0; i < dsDinh.size(); i++) {
            Diem p1 = dsDinh.get(i);
            Diem p2 = dsDinh.get((i + 1) % dsDinh.size());

            tong1 += p1.getKinhDo() * p2.getViDo();
            tong2 += p1.getViDo() * p2.getKinhDo();
        }

        return Math.abs(tong1 - tong2) / 2.0;
    }
}
