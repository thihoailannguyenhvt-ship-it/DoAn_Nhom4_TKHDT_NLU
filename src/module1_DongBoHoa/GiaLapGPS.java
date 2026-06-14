package module1_DongBoHoa;

// Lop gia lap du lieu GPS
public class GiaLapGPS implements INguonDuLieu {

    // Ham nap du lieu tho theo interface
    @Override
    public void napDuLieuTho() {

        // In thong bao gia lap nap du lieu
        System.out.println("Dang nap du lieu GPS gia lap...");
    }

    // Ham sinh GPS theo thoi diem
    public BanGhiGPS sinhGPS(long thoiDiem) {

        // Tao vi do gia lap thay doi nhe theo thoi gian
        double viDo = 11.422179 + (thoiDiem % 1000) * 0.000001;

        // Tao kinh do gia lap thay doi nhe theo thoi gian
        double kinhDo = 107.428679 + (thoiDiem % 1000) * 0.000001;

        // Tao doi tuong toa do
        ToaDo toaDo = new ToaDo(viDo, kinhDo);

        // Tra ve ban ghi GPS gia lap
        return new BanGhiGPS("ANIMAL_001", thoiDiem, toaDo);
    }
}