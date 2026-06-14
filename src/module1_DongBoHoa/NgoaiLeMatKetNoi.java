package module1_DongBoHoa;

// Lop ngoai le mat ket noi
public class NgoaiLeMatKetNoi extends Exception {

    // Ham khoi tao ngoai le mat ket noi
    public NgoaiLeMatKetNoi(String thongBao) {

        // Goi constructor cua lop Exception
        super(thongBao);
    }
}