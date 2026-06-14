package module1_DongBoHoa;

// Lop quan ly thoi gian dong bo
public class QuanLyThoiGian {

    // Lay thoi diem he thong hien tai
    public long layThoiDiemHeThong() {

        // Tra ve timestamp he thong
        return System.currentTimeMillis();
    }

    // Kiem tra hai thoi diem co hop le khong
    public boolean laHopLe(long thoiDiem) {

        // Thoi diem hop le khi lon hon 0
        return thoiDiem > 0;
    }

    // Tinh khoang cach giua hai thoi diem
    public long tinhKhoangCach(long t1, long t2) {

        // Tra ve khoang cach tuyet doi
        return Math.abs(t1 - t2);
    }
}