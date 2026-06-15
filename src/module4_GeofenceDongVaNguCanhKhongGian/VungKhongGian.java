package module4_GeofenceDongVaNguCanhKhongGian;

import java.util.List;

public abstract  class VungKhongGian {
	public abstract double tinhToanGiaoThoa(GeofenceDong geofence);
	protected double tinhDienTichDaGiac(List<Diem> dsDinh) {
		int n = dsDinh.size();

		if (n < 3)
			return 0;

		double tong1 = 0;
		double tong2 = 0;

		for (int i = 0; i < n; i++) {
			Diem p1 = dsDinh.get(i);
			Diem p2 = dsDinh.get((i + 1) % n);

			tong1 += p1.getKinhDo() * p2.getViDo();
			tong2 += p1.getViDo() * p2.getKinhDo();
		}

		return Math.abs(tong1 - tong2) / 2.0;
	}
	}

