package module1_DongBoHoa;

// Lop kiem tra tinh hop le cua du lieu
public class BoKiemTraDuLieu {

    // Kiem tra object co null khong
    public boolean laDuLieuNull(Object obj) {

        // Neu object bang null thi tra ve true
        return obj == null;
    }

    // Kiem tra toa do co hop le khong
    public boolean laToaDoVatly(ToaDo toaDo) {

        // Neu toa do null thi khong hop le
        if (toaDo == null) {
            return false;
        }

        // Kiem tra vi do nam trong khoang -90 den 90
        boolean viDoHopLe = toaDo.getViDo() >= -90 && toaDo.getViDo() <= 90;

        // Kiem tra kinh do nam trong khoang -180 den 180
        boolean kinhDoHopLe = toaDo.getKinhDo() >= -180 && toaDo.getKinhDo() <= 180;

        // Tra ve ket qua kiem tra
        return viDoHopLe && kinhDoHopLe;
    }

    // Kiem tra gia toc co hop le khong
    public boolean laGiaTocHopLe(BanGhiGiaTocBD acc) {

        // Neu ban ghi gia toc null thi khong hop le
        if (acc == null) {
            return false;
        }

        // Neu danh sach truc X null hoac rong thi khong hop le
        if (acc.getTrucX() == null || acc.getTrucX().isEmpty()) {
            return false;
        }

        // Neu danh sach truc Y null hoac rong thi khong hop le
        if (acc.getTrucY() == null || acc.getTrucY().isEmpty()) {
            return false;
        }

        // Neu danh sach truc Z null hoac rong thi khong hop le
        if (acc.getTrucZ() == null || acc.getTrucZ().isEmpty()) {
            return false;
        }

        // Neu so mau 3 truc khong bang nhau thi khong hop le
        if (acc.getTrucX().size() != acc.getTrucY().size()
                || acc.getTrucX().size() != acc.getTrucZ().size()) {
            return false;
        }

        // Kiem tra tung gia tri truc X
        for (Double x : acc.getTrucX()) {

            // Neu gia tri null hoac NaN thi khong hop le
            if (x == null || Double.isNaN(x)) {
                return false;
            }
        }

        // Kiem tra tung gia tri truc Y
        for (Double y : acc.getTrucY()) {

            // Neu gia tri null hoac NaN thi khong hop le
            if (y == null || Double.isNaN(y)) {
                return false;
            }
        }

        // Kiem tra tung gia tri truc Z
        for (Double z : acc.getTrucZ()) {

            // Neu gia tri null hoac NaN thi khong hop le
            if (z == null || Double.isNaN(z)) {
                return false;
            }
        }

        // Neu tat ca dieu kien deu dung thi du lieu hop le
        return true;
    }
}