package module1_DongBoHoa;



import dinhDanh.TrangThaiDuLieu;


public final class DuLieuDongBo {

    private final String idConVat;
    private final ToaDo toaDoHienTai;
    private final BanGhiGiaTocBD giaTocHienTai;
    private final long thoiDiemSuKien; 
    private final TrangThaiDuLieu trangThai;

    public DuLieuDongBo(String idConVat, 
                        ToaDo toaDoHienTai, 
                        BanGhiGiaTocBD giaTocHienTai, 
                        long thoiDiemSuKien, 
                        TrangThaiDuLieu trangThai) {
        
        
        if (idConVat == null || idConVat.isEmpty()) {
            throw new IllegalArgumentException("ID con vat khong duoc de trong.");
        }
        if (giaTocHienTai == null) {
            throw new IllegalArgumentException("Ban ghi gia toc khong duoc null.");
        }
        
        this.idConVat = idConVat;
        this.toaDoHienTai = toaDoHienTai; 
        this.giaTocHienTai = giaTocHienTai;
        this.thoiDiemSuKien = thoiDiemSuKien;
        this.trangThai = trangThai;
    }

   
    public String getIdConVat() {
        return idConVat;
    }

    public ToaDo getToaDoHienTai() {
        return toaDoHienTai;
    }

    public BanGhiGiaTocBD getGiaTocHienTai() {
        return giaTocHienTai;
    }

    public long getThoiDiemSuKien() {
        return thoiDiemSuKien;
    }

    public TrangThaiDuLieu getTrangThai() {
        return trangThai;
    }
}