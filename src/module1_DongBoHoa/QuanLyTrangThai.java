package module1_DongBoHoa;

// Lop quan ly trang thai GPS cuoi cung
public class QuanLyTrangThai {

    // Vi tri cuoi cung
    private ToaDo viTriCuoiCung;

    // Thoi diem GPS cuoi cung
    private long thoiDiemGpsCuoi;

    // Luu vet vi tri va thoi diem moi nhat
    public void luuVet(ToaDo viTri, long thoiDiem) {

        // Gan vi tri cuoi cung
        this.viTriCuoiCung = viTri;

        // Gan thoi diem GPS cuoi
        this.thoiDiemGpsCuoi = thoiDiem;
    }

    // Lay vi tri cuoi cung
    public ToaDo layViTriCuoi() {

        // Tra ve vi tri cuoi cung
        return viTriCuoiCung;
    }

    // Lay thoi diem GPS cuoi
    public long getThoiDiemGpsCuoi() {

        // Tra ve thoi diem GPS cuoi
        return thoiDiemGpsCuoi;
    }
}