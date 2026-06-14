package module1_DongBoHoa;

// Lop luu thong tin toa do GPS
public class ToaDo {

    // Vi do cua vi tri
    private double viDo;

    // Kinh do cua vi tri
    private double kinhDo;

    // Ham khoi tao toa do
    public ToaDo(double viDo, double kinhDo) {

        // Gan vi do
        this.viDo = viDo;

        // Gan kinh do
        this.kinhDo = kinhDo;
    }

    // Lay vi do
    public double getViDo() {
        return viDo;
    }

    // Lay kinh do
    public double getKinhDo() {
        return kinhDo;
    }

    // Chuyen toa do thanh chuoi de in ra
    @Override
    public String toString() {
        return "(" + viDo + ", " + kinhDo + ")";
    }
}