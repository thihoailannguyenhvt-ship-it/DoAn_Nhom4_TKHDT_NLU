package module1_DongBoHoa;

import java.util.*;
import java.io.*;

public class QuanLyCauHinh {
	public static class ThongSo {
        public double theta, sigma;
        public ThongSo(double t, double s) { this.theta = t; this.sigma = s; }
    }
	private static Map<String, ThongSo> configMap = new HashMap<>();

    public static void napCauHinh(String path) {
        Properties prop = new Properties();
        try (InputStream input = new FileInputStream(path)) {
            prop.load(input);
            for (String key : prop.stringPropertyNames()) {
                if (key.endsWith(".theta")) {
                    String species = key.replace(".theta", "");
                    double theta = Double.parseDouble(prop.getProperty(key));
                    double sigma = Double.parseDouble(prop.getProperty(species + ".sigma", "0.005"));
                    configMap.put(species, new ThongSo(theta, sigma));
                }
            }
        } catch (Exception e) {
            System.err.println("Lỗi load cấu hình: " + e.getMessage());
        }
    }

    public static ThongSo get(String species) {
        return configMap.getOrDefault(species, new ThongSo(0.1, 0.005)); // Mặc định
    }
}