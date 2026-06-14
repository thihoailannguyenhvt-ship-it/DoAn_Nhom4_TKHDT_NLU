package module1_DongBoHoa;

// Lop ngoai le du lieu loi
public class NgoaiLeDuLieuLoi extends Exception {

    // Du lieu tho bi loi
    private String duLieuTho;

    // Ham khoi tao ngoai le du lieu loi
    public NgoaiLeDuLieuLoi(String duLieuTho) {

        // Goi constructor cua lop Exception
        super("Du lieu loi: " + duLieuTho);

        // Luu du lieu tho bi loi
        this.duLieuTho = duLieuTho;
    }

    // Lay thong tin loi
    public String layThongTinLoi() {

        // Tra ve thong tin loi
        return "Du lieu khong hop le: " + duLieuTho;
    }
}