package module1_DongBoHoa;


public final class BanGhiGPS {

   
    private final String idConVat;
    private final long mocThoiGianSuKien;
    private final ToaDo viTri; 

   
    public BanGhiGPS(String idConVat, long mocThoiGianSuKien, ToaDo viTri) {
        
        if (idConVat == null || idConVat.trim().isEmpty()) {
            throw new IllegalArgumentException("ID con vat khong duoc phep de trong hoac null.");
        }
        
       
        if (mocThoiGianSuKien <= 0) {
            throw new IllegalArgumentException("Moc thoi gian su kien phai lon hon 0: " + mocThoiGianSuKien);
        }
        
      
        if (viTri == null) {
            throw new IllegalArgumentException("Toa do vi tri cua ban ghi GPS khong duoc phep null.");
        }

        this.idConVat = idConVat.trim();
        this.mocThoiGianSuKien = mocThoiGianSuKien;
        this.viTri = viTri; 
    }

    
    public String getIdConVat() {
        return idConVat;
    }

    public long getMocThoiGianSuKien() {
        return mocThoiGianSuKien;
    }

    public ToaDo getViTri() {
        return viTri;
    }

    
    @Override
    public String toString() {
        return String.format("BanGhiGPS{ID='%s', Time=%d, ToaDo=%s}", 
                idConVat, mocThoiGianSuKien, viTri.toString());
    }
}