package module1_DongBoHoa;
import dinhDanh.*;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

// Lop luu tru ban ghi sinh hoc sau khi Module 1 dong bo du lieu
public class BanGhiSinhHoc {

    // Ma dinh danh cua con vat
    private String idConVat;

    // Vi tri GPS cua con vat
    private ToaDo viTri;

    // Du lieu gia toc cua con vat
    private BanGhiGiaTocBD giaToc;

    // Moc thoi gian su kien
    private long mocThoiGianSuKien;

    // Moc thoi gian he thong
    private long mocThoiGianHeThong;

    // Trang thai du lieu
    private TrangThaiDuLieu trangThai;

    // Thoi diem Module 1 hoan thanh xu ly
    private long t1;

    // Ham khoi tao rong
    public BanGhiSinhHoc() {
        super();
    }

    // Ham khoi tao day du
    public BanGhiSinhHoc(String idConVat,
                         ToaDo viTri,
                         BanGhiGiaTocBD giaToc,
                         long mocThoiGianSuKien,
                         long mocThoiGianHeThong,
                         TrangThaiDuLieu trangThai,
                         long t1) {

        // Gan ma con vat
        this.idConVat = idConVat;

        // Gan vi tri
        this.viTri = viTri;

        // Gan gia toc
        this.giaToc = giaToc;

        // Gan moc thoi gian su kien
        this.mocThoiGianSuKien = mocThoiGianSuKien;

        // Gan moc thoi gian he thong
        this.mocThoiGianHeThong = mocThoiGianHeThong;

        // Gan trang thai
        this.trangThai = trangThai;

        // Gan thoi diem Module 1 xu ly xong
        this.t1 = t1;
    }

    // Lay ma con vat
    public String getIdConVat() {
        return idConVat;
    }

    // Gan ma con vat
    public void setIdConVat(String idConVat) {
        this.idConVat = idConVat;
    }

    // Lay vi tri
    public ToaDo getViTri() {
        return viTri;
    }

    // Gan vi tri
    public void setViTri(ToaDo viTri) {
        this.viTri = viTri;
    }

    // Lay gia toc
    public BanGhiGiaTocBD getGiaToc() {
        return giaToc;
    }

    // Gan gia toc
    public void setGiaToc(BanGhiGiaTocBD giaToc) {
        this.giaToc = giaToc;
    }

    // Lay moc thoi gian su kien
    public long getMocThoiGianSuKien() {
        return mocThoiGianSuKien;
    }

    // Gan moc thoi gian su kien
    public void setMocThoiGianSuKien(long mocThoiGianSuKien) {
        this.mocThoiGianSuKien = mocThoiGianSuKien;
    }

    // Lay moc thoi gian he thong
    public long getMocThoiGianHeThong() {
        return mocThoiGianHeThong;
    }

    // Gan moc thoi gian he thong
    public void setMocThoiGianHeThong(long mocThoiGianHeThong) {
        this.mocThoiGianHeThong = mocThoiGianHeThong;
    }

    // Lay trang thai
    public TrangThaiDuLieu getTrangThai() {
        return trangThai;
    }

    // Gan trang thai
    public void setTrangThai(TrangThaiDuLieu trangThai) {
        this.trangThai = trangThai;
    }

    // Lay thoi diem Module 1 xu ly xong
    public long getT1() {
        return t1;
    }

    // Gan thoi diem Module 1 xu ly xong
    public void setT1(long t1) {
        this.t1 = t1;
    }

    // Tinh tong do tre tu luc su kien xay ra den khi Module 1 xu ly xong
    public long getDoTreHeThong() {
        return t1 - mocThoiGianSuKien;
    }

    // Chuyen milliseconds thanh ngay gio de doc
    private String dinhDangThoiGian(long millis) {

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return Instant.ofEpochMilli(millis)
                .atZone(ZoneId.systemDefault())
                .format(formatter);
    }

    // Chuyen doi thanh dong CSV
    public String toCSV() {

        return idConVat + ","
                + viTri.getViDo() + ","
                + viTri.getKinhDo() + ","
                + "\"" + giaToc.getTrucX() + "\"" + ","
                + "\"" + giaToc.getTrucY() + "\"" + ","
                + "\"" + giaToc.getTrucZ() + "\"" + ","
                + dinhDangThoiGian(mocThoiGianSuKien) + ","
                + dinhDangThoiGian(mocThoiGianHeThong) + ","
                + trangThai + ","
                + dinhDangThoiGian(t1) + ","
                + getDoTreHeThong();
    }

    // Hien thi thong tin ban ghi sinh hoc
    @Override
    public String toString() {

        return "BanGhiSinhHoc["
                + "idConVat=" + idConVat
                + ", viTri=" + viTri
                + ", giaToc=" + giaToc
                + ", mocThoiGianSuKien=" + dinhDangThoiGian(mocThoiGianSuKien)
                + ", mocThoiGianHeThong=" + dinhDangThoiGian(mocThoiGianHeThong)
                + ", trangThai=" + trangThai
                + ", t1=" + dinhDangThoiGian(t1)
                + ", doTre=" + getDoTreHeThong() + " ms"
                + "]";
    }
}