package module1_DongBoHoa;


public final class BoKiemTraDuLieu {

   
    private BoKiemTraDuLieu() {
        
        throw new UnsupportedOperationException("Day la lop tien ich kiem tra, khong duoc phep khoi tao.");
    }

  
    public static boolean laIdHopLe(String idConVat) {
       
        if (idConVat == null) {
            return false;
        }
       
        return !idConVat.trim().isEmpty();
    }

   
    public static boolean laToaDoHopLe(double viDo, double kinhDo) {
       
        if (viDo < -90.0 || viDo > 90.0) {
            return false; 
        }
        
        if (kinhDo < -180.0 || kinhDo > 180.0) {
            return false;
        }
        
        
        return true;
    }

  
    public static boolean laDuLieuGiaTocHopLe(double[] x, double[] y, double[] z) {
        
        if (x == null || y == null || z == null) {
            return false;
        }

      
        if (x.length == 0 || y.length == 0 || z.length == 0) {
            return false;
        }

        
        return (x.length == y.length) && (x.length == z.length);
    }
}