package module1_DongBoHoa;

// Lop luu ban ghi GPS
public class BanGhiGPS {

    // Ma dinh danh con vat
    private String idConVat;

    // Thoi diem su kien cua GPS
    private long thoiDiemSuKien;

    // Vi tri GPS
    private ToaDo viTri;

    // Ham khoi tao ban ghi GPS
    public BanGhiGPS(String idConVat, long thoiDiemSuKien, ToaDo viTri) {

        // Gan ma con vat
        this.idConVat = idConVat;

        // Gan thoi diem su kien
        this.thoiDiemSuKien = thoiDiemSuKien;

        // Gan vi tri
        this.viTri = viTri;
    }

    public String getIdConVat() {
        return idConVat;
    }

    public long getThoiDiemSuKien() {
        return thoiDiemSuKien;
    }

    public ToaDo getViTri() {
        return viTri;
    }

    @Override
    public String toString() {
        return "GPS[id=" + idConVat + ", time=" + thoiDiemSuKien + ", viTri=" + viTri + "]";
    }
}