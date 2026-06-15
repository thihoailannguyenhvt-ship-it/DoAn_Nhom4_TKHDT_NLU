package module4_GeofenceDongVaNguCanhKhongGian;

public class BoTinhToanBanKinh {

    private static final double R_BASE = 0.002;

    public BoTinhToanBanKinh() {
    }

    public double tinhBanKinh(double k, double odba) {

        double r = R_BASE + k * odba * 0.003;

        if (r < 0.002) {
            r = 0.002;
        }

        if (r > 0.02) {
            r = 0.02;
        }

        return r;
    }

    public double getRBase() {
        return R_BASE;
    }
}

