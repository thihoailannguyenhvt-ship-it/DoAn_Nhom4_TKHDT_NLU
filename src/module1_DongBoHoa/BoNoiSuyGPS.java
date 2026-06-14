package module1_DongBoHoa;

import java.util.LinkedList;
import java.util.Queue;

// Lop noi suy GPS khi thieu du lieu
public class BoNoiSuyGPS {

    // Bo dem luu cac toa do gan nhat
    private Queue<ToaDo> boDemGPS = new LinkedList<>();

    // Cap nhat bo dem GPS
    public void capNhatBoDem(ToaDo viTri, long thoiDiem) {

        // Neu vi tri null thi khong cap nhat
        if (viTri == null) {
            return;
        }

        // Them vi tri vao bo dem
        boDemGPS.add(viTri);

        // Chi giu toi da 5 vi tri gan nhat
        if (boDemGPS.size() > 5) {
            boDemGPS.poll();
        }
    }

    // Tinh noi suy GPS theo thoi diem dich
    public ToaDo tinhNoiSuy(long thoiDiemDich) {

        // Neu bo dem rong thi tra ve toa do mac dinh
        if (boDemGPS.isEmpty()) {
            return new ToaDo(11.422179, 107.428679);
        }

        // Tinh tong vi do
        double tongViDo = 0;

        // Tinh tong kinh do
        double tongKinhDo = 0;

        // Duyet cac toa do trong bo dem
        for (ToaDo toaDo : boDemGPS) {
            tongViDo += toaDo.getViDo();
            tongKinhDo += toaDo.getKinhDo();
        }

        // Tinh vi do trung binh
        double viDoTB = tongViDo / boDemGPS.size();

        // Tinh kinh do trung binh
        double kinhDoTB = tongKinhDo / boDemGPS.size();

        // Tra ve toa do noi suy
        return new ToaDo(viDoTB, kinhDoTB);
    }
}