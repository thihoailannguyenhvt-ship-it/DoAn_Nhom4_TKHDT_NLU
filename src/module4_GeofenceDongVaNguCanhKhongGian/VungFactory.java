package module4_GeofenceDongVaNguCanhKhongGian;

public class VungFactory {
	public static TramQuanSat taoTramQuanSat(Diem tam, double banKinh) {
        return new TramQuanSat(tam, banKinh);
    }

    public static KhuDanCu taoKhuDanCu(Diem tam, double banKinh) {
        return new KhuDanCu(tam, banKinh);
    }

    public static TuyenDuongDuLich taoTuyenDuongDuLich(Diem tam, double banKinh) {
        return new TuyenDuongDuLich(tam, banKinh);
    }
}

