package module2_PhanTichHanhVi;

import java.util.LinkedList;
import java.util.Queue;


public class DongVat {

    private String idConVat;
    private double eTichLuy;
    private Loai loai;
    
    
    private Queue<Double> lichSuODBA = new LinkedList<>();

    
    public DongVat(String idConVat, double eTichLuy, Loai loai) {
        this.idConVat = idConVat;
        this.eTichLuy = eTichLuy;
        this.loai = loai;
    }

    
    public DongVat(String idConVat, Loai loai) {
        this(idConVat, 0.0, loai);
    }

    
    public String getIdConVat() { return idConVat; }
    public void setIdConVat(String idConVat) { this.idConVat = idConVat; }

    public double getETichLuy() { return eTichLuy; }
    public void setETichLuy(double eTichLuy) { this.eTichLuy = eTichLuy; }

    public Loai getLoai() { return loai; }
    public void setLoai(Loai loai) { this.loai = loai; }

    public Queue<Double> getLichSuODBA() { return lichSuODBA; }
    
    public String layMaLoai() { return loai.getTenLoai(); }

 
    public void capNhatNangLuong(double ODBAMoi) {
        this.eTichLuy += ODBAMoi;
        this.lichSuODBA.add(ODBAMoi);

        if (lichSuODBA.size() > 10) {
            lichSuODBA.poll();
        }
    }

    @Override
    public String toString() {
        return "DongVat{" +
                "id='" + idConVat + '\'' +
                ", eTichLuy=" + String.format("%.4f", eTichLuy) +
                ", loai=" + loai.getTenLoai() +
                '}';
    }
}