package module1_DongBoHoa;

import java.util.List;

// Lop luu du lieu gia toc dang danh sach
public class BanGhiGiaTocBD {

    // Moc thoi gian su kien cua du lieu gia toc
    private long thoiDiemSuKien;

    // Danh sach gia toc truc X
    private List<Double> trucX;

    // Danh sach gia toc truc Y
    private List<Double> trucY;

    // Danh sach gia toc truc Z
    private List<Double> trucZ;

    // Ham khoi tao ban ghi gia toc
    public BanGhiGiaTocBD(long thoiDiemSuKien,
                        List<Double> trucX,
                        List<Double> trucY,
                        List<Double> trucZ) {

        // Gan moc thoi gian su kien
        this.thoiDiemSuKien = thoiDiemSuKien;

        // Gan danh sach truc X
        this.trucX = trucX;

        // Gan danh sach truc Y
        this.trucY = trucY;

        // Gan danh sach truc Z
        this.trucZ = trucZ;
    }

    // Lay thoi diem su kien
    public long getThoiDiemSuKien() {
        return thoiDiemSuKien;
    }

    // Lay danh sach truc X
    public List<Double> getTrucX() {
        return trucX;
    }

    // Lay danh sach truc Y
    public List<Double> getTrucY() {
        return trucY;
    }

    // Lay danh sach truc Z
    public List<Double> getTrucZ() {
        return trucZ;
    }

    

    // Chuyen doi thanh chuoi de in ra
    @Override
    public String toString() {
        return "GiaToc["
                + "trucX=" + trucX
                + ", trucY=" + trucY
                + ", trucZ=" + trucZ
                + "]";
    }
}