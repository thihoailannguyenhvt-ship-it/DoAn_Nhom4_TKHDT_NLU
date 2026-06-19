package module4_GeofenceDongVaNguCanhKhongGian;


public abstract class VungKhongGian {
  
    protected String idVung;
    protected String tenVung;
    protected Diem toaDoTam; 

   
    public VungKhongGian(String idVung, String tenVung, Diem toaDoTam) {
        this.idVung = idVung;
        this.tenVung = tenVung;
        this.toaDoTam = toaDoTam;
    }
    
    public abstract String getLoaiVung();

   

    
    public abstract double tinhToanGiaoThoa(Diem tamGeofence, double banKinhGeofenceDo);

  
    public String getIdVung() { return idVung; }
    public String getTenVung() { return tenVung; }
    public Diem getToaDoTam() { return toaDoTam; }
 // Trong VungKhongGian.java
    @Override
    public String toString() {
        return getLoaiVung() + " (" + getIdVung() + ")";
    }
}