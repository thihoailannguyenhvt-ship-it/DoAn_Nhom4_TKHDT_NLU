package module1_DongBoHoa;
import java.util.Random;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class GiaLapGPS implements INguonDuLieu {

  
    private final double viDoGoc = 11.422179;
    private final double kinhDoGoc = 107.428679;
    private long lastTimestamp = -1;
 
    private double theta;
    private double sigma;
    private final Random random = new Random();
    private double currentLat = viDoGoc;
    private double currentLon = kinhDoGoc;
   
 
    public GiaLapGPS(String species, double theta, double sigma) {
        this.theta = theta;
        this.sigma = sigma;
    }
    
    public GiaLapGPS(double theta, double sigma, double currentLat, double currentLon) {
		
		this.theta = theta;
		this.sigma = sigma;
		this.currentLat = currentLat;
		this.currentLon = currentLon;
	}

	@Override
    public void napDuLieuTho() {
       
        System.out.println("Da kich hoat bo gia lap toa do dinh vi khoa hoc Ornstein-Uhlenbeck.");
    }

  
    @Override
    public ToaDo sinhGPS(long thoiDiem) {
    	if (thoiDiem <= 0) {
            return new ToaDo(viDoGoc, kinhDoGoc);
        }
    	 // FIX BUG 1+2: Tính dt thực tế từ khoảng cách timestamp (đơn vị: giây)
        // Lần đầu tiên gọi: dùng dt mặc định 1.0 giây
        double dt;
        if (lastTimestamp < 0) {
            dt = 1.0;
        } else {
            double deltaTms = (double)(thoiDiem - lastTimestamp);
            // Nếu timestamp đi ngược hoặc bằng nhau → giữ dt = 1.0 để tránh dt=0 hoặc âm
            dt = (deltaTms > 0) ? deltaTms / 1000.0 : 1.0;
        }
        /* * GIỚI HẠN dt (Time-step Capping) - BẢN VÁ QUAN TRỌNG:
         * - Math.max(dt, 0.01): Tránh việc dt quá nhỏ (gần bằng 0) gây sai số tính toán hoặc "đóng băng" 
         * khi dữ liệu gia tốc có tần suất quá cao.
         * - Math.min(dt, 60.0): Nếu mất sóng quá lâu (ví dụ 15 phút), chúng ta chặn dt ở mức 60 giây.
         * Việc này ngăn chặn mô hình OU bị "văng" ra ngoài không gian địa lý do sai số tích lũy lớn.
         */
        dt = Math.max(0.01, Math.min(dt, 60.0));
        lastTimestamp = thoiDiem;
 
        double sqrtDt = Math.sqrt(dt);
        double noiseLat = random.nextGaussian() * sqrtDt;
        double noiseLon = random.nextGaussian() * sqrtDt;

        
        currentLat += theta * (viDoGoc - currentLat) * dt + sigma * noiseLat;
        currentLon += theta * (kinhDoGoc - currentLon) * dt + sigma * noiseLon;

        try {
            return new ToaDo(currentLat, currentLon);
        } catch (IllegalArgumentException e) {
           
            return new ToaDo(viDoGoc, kinhDoGoc);
        }
    }
}