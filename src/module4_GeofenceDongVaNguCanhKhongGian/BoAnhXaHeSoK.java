package module4_GeofenceDongVaNguCanhKhongGian;

public class BoAnhXaHeSoK {

    private double k;

    public BoAnhXaHeSoK() {
        this.k = 1.0;
    }

    public BoAnhXaHeSoK(double k) {
        this.k = k;
    }

    public void capNhatK(String trangThai) {
        if (trangThai == null) {
            k = 1.0;
            return;
        }

        switch (trangThai.toUpperCase()) {

            case "BINH_THUONG":
            case "ANTOAN":
            case "NORMAL":
                k = 1.0;
                break;

            case "TIENCANHBAO":
            case "CANHBAO":
            case "CANH_BAO":
                k = 2.0;
                break;

            case "DINH_BAY":
            case "SUY_KIET":
            case "NGUYCAP":
            case "NGUY_CAP":
                k = 4.0;
                break;

            default:
                k = 1.0;
        }
    }

    public double layK() {
        return k;
    }
}
