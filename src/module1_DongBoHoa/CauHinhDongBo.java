package module1_DongBoHoa;

// Lop cau hinh dong bo
public class CauHinhDongBo {

    // Nguong dong bo cho phep
    private long nguongDongBo;

    // Gioi han mat GPS
    private long gioiHanMatGPS;

    // Ham khoi tao cau hinh dong bo
    public CauHinhDongBo(long nguongDongBo, long gioiHanMatGPS) {

        // Gan nguong dong bo
        this.nguongDongBo = nguongDongBo;

        // Gan gioi han mat GPS
        this.gioiHanMatGPS = gioiHanMatGPS;
    }

    public long getNguongDongBo() {
        return nguongDongBo;
    }

    public long getGioiHanMatGPS() {
        return gioiHanMatGPS;
    }
}