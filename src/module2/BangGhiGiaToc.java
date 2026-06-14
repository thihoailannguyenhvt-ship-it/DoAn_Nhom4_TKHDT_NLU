package module2;

public class BangGhiGiaToc {
	private long thoiDiemSuKien;

    private double trucX;

    private double trucY;

    private double trucZ;

    public BangGhiGiaToc(long thoiDiemSuKien,
                         double trucX,
                         double trucY,
                         double trucZ) {

        this.thoiDiemSuKien = thoiDiemSuKien;
        this.trucX = trucX;
        this.trucY = trucY;
        this.trucZ = trucZ;
    }

    public long getThoiDiemSuKien() {
        return thoiDiemSuKien;
    }

    public double getTrucX() {
        return trucX;
    }

    public double getTrucY() {
        return trucY;
    }

    public double getTrucZ() {
        return trucZ;
    }

    @Override
    public String toString() {
        return "BangGhiGiaToc [x=" + trucX
                + ", y=" + trucY
                + ", z=" + trucZ + "]";
    }

}
