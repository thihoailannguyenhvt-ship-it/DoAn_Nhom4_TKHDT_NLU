package module1_DongBoHoa;

import java.util.Objects;


public final class ToaDo {

   
    private final double viDo;
    private final double kinhDo;

    
    public ToaDo(double viDo, double kinhDo) {
        
        if (viDo < -90.0 || viDo > 90.0) {
            throw new IllegalArgumentException("Vi do khong hop le (phai tu -90 den 90): " + viDo);
        }
        if (kinhDo < -180.0 || kinhDo > 180.0) {
            throw new IllegalArgumentException("Kinh do khong hop le (phai tu -180 den 180): " + kinhDo);
        }

        this.viDo = viDo;
        this.kinhDo = kinhDo;
    }

   
    public double getViDo() {
        return viDo;
    }

    public double getKinhDo() {
        return kinhDo;
    }

    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ToaDo khac = (ToaDo) obj;
       
        return Double.compare(khac.viDo, this.viDo) == 0 && 
               Double.compare(khac.kinhDo, this.kinhDo) == 0;
    }

    
    @Override
    public int hashCode() {
        return Objects.hash(viDo, kinhDo);
    }

   
    @Override
    public String toString() {
        return String.format("(%.6f, %.6f)", viDo, kinhDo);
    }
}