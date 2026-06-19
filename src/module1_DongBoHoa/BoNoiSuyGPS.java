package module1_DongBoHoa;


public class BoNoiSuyGPS {

	private final INguonDuLieu nguonDuLieu;

	
	public BoNoiSuyGPS(INguonDuLieu nguonDuLieu) {
		
		if (nguonDuLieu == null) {
			throw new IllegalArgumentException("Loi kien truc: Nguon du lieu cap vao Bo noi suy khong duoc null.");
		}
		this.nguonDuLieu = nguonDuLieu;
	}

	
	public ToaDo noiSuyToaDoTheoThoiGian(long mocThoiGianSuKien) {
		
		if (mocThoiGianSuKien <= 0) {
			
			throw new IllegalArgumentException(
					"Moc thoi gian cua file du lieu de noi suy phai lon hon 0: " + mocThoiGianSuKien);
		}

	
		ToaDo toaDoKetQua = this.nguonDuLieu.sinhGPS(mocThoiGianSuKien);

	
		if (toaDoKetQua == null || !BoKiemTraDuLieu.laToaDoHopLe(toaDoKetQua.getViDo(), toaDoKetQua.getKinhDo())) {
			
			System.err.println("[Canh bao thuat toan] Toa do noi suy ra tai moc " + mocThoiGianSuKien
					+ " bi loi vi tri. Dang tu dong sua sai.");

			return new ToaDo(11.422179, 107.428679);
		}

		
		return toaDoKetQua;
	}

	
	public INguonDuLieu getNguonDuLieu() {
		
		return this.nguonDuLieu;
	}
}