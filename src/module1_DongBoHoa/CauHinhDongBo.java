package module1_DongBoHoa;

public final class CauHinhDongBo {

    public static final long MAC_DINH_SYNC_THRESHOLD = 0; 
    
    
    public static final long MAC_DINH_GIOI_HAN_MAT_GPS = 900000;
    
   
    public static final long MAC_DINH_GIOI_HAN_TRE_NGHIEM_TRONG = 172800000; 

    public static final int MAC_DINH_BUFFER_SIZE = 100;
    private final long syncThreshold;
    private final long gioiHanMatGPS;
    private final long gioiHanTreNghiemTrong;
    private final int bufferSize; 
  
    public CauHinhDongBo() {
        this.syncThreshold = MAC_DINH_SYNC_THRESHOLD;
        this.gioiHanMatGPS = MAC_DINH_GIOI_HAN_MAT_GPS;
        this.gioiHanTreNghiemTrong = MAC_DINH_GIOI_HAN_TRE_NGHIEM_TRONG;
        this.bufferSize = MAC_DINH_BUFFER_SIZE;
    }

   
    public CauHinhDongBo(long syncThreshold, long gioiHanMatGPS, long gioiHanTreNghiemTrong, int bufferSize) {
        if (syncThreshold < 0) {
            throw new IllegalArgumentException("Nguong cua so dong bo khong duoc am: " + syncThreshold);
        }
        if (gioiHanMatGPS <= 0) {
            throw new IllegalArgumentException("Nguong gioi han mat GPS phai lon hon 0 ms: " + gioiHanMatGPS);
        }
        if (gioiHanTreNghiemTrong <= 0) {
            throw new IllegalArgumentException("Nguong gioi han tre du lieu nghiêm trong phai lon hon 0 ms: " + gioiHanTreNghiemTrong);
        }
        
        this.syncThreshold = syncThreshold;
        this.gioiHanMatGPS = gioiHanMatGPS;
        this.gioiHanTreNghiemTrong = gioiHanTreNghiemTrong;
        this.bufferSize = bufferSize;
    }

  
    
    public long getSyncThreshold() {
        return syncThreshold;
    }

    public long getGioiHanMatGPS() {
        return gioiHanMatGPS;
    }

    public long getGioiHanTreNghiemTrong() {
        return gioiHanTreNghiemTrong;
    }

    public int getBufferSize() {
        return bufferSize;
    }

    @Override
    public String toString() {
        return "CauHinhDongBo{" +
                "syncThreshold=" + syncThreshold + "ms" +
                ", gioiHanMatGPS=" + gioiHanMatGPS + "ms" +
                ", gioiHanTreNghiemTrong=" + gioiHanTreNghiemTrong + "ms" +
                '}';
    }
}