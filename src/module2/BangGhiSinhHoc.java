package module2;

public class BangGhiSinhHoc {
    // Ma  dinh danh duy nhat cua con vat
	private String idConVat;
	
	private String viTriToaDo;
	// Moc thoi gian su kien thuc te xay ra
	private long mocThoiGianSuKien;

	// Moc thoi gian he thong tiep nhan du lieu
	private long mocThoiGianHeThong;
    // Du lieu gia toc dc dong bo
	private BangGhiGiaToc giaToc;
	public BangGhiSinhHoc() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BangGhiSinhHoc(String idConVat, String viTriToaDo, long mocThoiGianSuKien, long mocThoiGianHeThong,
			BangGhiGiaToc giaToc) {
		super();
		this.idConVat = idConVat;
		this.viTriToaDo = viTriToaDo;
		this.mocThoiGianSuKien = mocThoiGianSuKien;
		this.mocThoiGianHeThong = mocThoiGianHeThong;
		this.giaToc = giaToc;
	}
	public String getIdConVat() {
		return idConVat;
	}
	public void setIdConVat(String idConVat) {
		this.idConVat = idConVat;
	}
	public String getViTriToaDo() {
		return viTriToaDo;
	}
	public void setViTriToaDo(String viTriToaDo) {
		this.viTriToaDo = viTriToaDo;
	}
	public long getMocThoiGianSuKien() {
		return mocThoiGianSuKien;
	}
	public void setMocThoiGianSuKien(long mocThoiGianSuKien) {
		this.mocThoiGianSuKien = mocThoiGianSuKien;
	}
	public long getMocThoiGianHeThong() {
		return mocThoiGianHeThong;
	}
	public void setMocThoiGianHeThong(long mocThoiGianHeThong) {
		this.mocThoiGianHeThong = mocThoiGianHeThong;
	}
	public BangGhiGiaToc getGiaToc() {
		return giaToc;
	}
	public void setGiaToc(BangGhiGiaToc giaToc) {
		this.giaToc = giaToc;
	}
	
	

}
