package module1_DongBoHoa;


public final class NgoaiLeDongBo {

    
    private NgoaiLeDongBo() {}

  
    public static class InvalidDataFormatException extends Exception {
        public InvalidDataFormatException(String thongBao) {
            super("======> [LOI DINH DANG]: " + thongBao);
        }
    }

    public static class MissingCriticalFieldsException extends Exception {
        public MissingCriticalFieldsException(String thongBao) {
            super("======> [THIEU TRUONG BAT BUOC]: " + thongBao);
        }
    }

    
    public static class ChronologicalOrderException extends Exception {
        public ChronologicalOrderException(String thongBao) {
            super("======> [SAI THU TU THOI GIAN]: " + thongBao);
        }
    }

    public static class FutureTimestampException extends Exception {
        public FutureTimestampException(String thongBao) {
            super("======> [THOI GIAN TUONG LAI]: " + thongBao);
        }
    }

  
    public static class ExcessiveLatencyException extends Exception {
        public ExcessiveLatencyException(String thongBao) {
            super("======> [CANH BAO TRE MANG NGIEM TRONG]: " + thongBao);
        }
    }

 
    public static class ColdStartException extends Exception {
        public ColdStartException(String thongBao) {
            super("======> [KHOI DONG LANH]: " + thongBao);
        }
    }
}