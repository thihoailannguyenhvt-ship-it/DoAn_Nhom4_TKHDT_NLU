package module1_DongBoHoa;

import dinhDanh.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;


public final class BanGhiSinhHoc {

	
	public static final String CSV_HEADER = "idConVat,viDo,kinhDo,trucX,trucY,trucZ,mocThoiGianSuKien,mocThoiGianHeThong,trangThai,t1,doTre";

	private final String idConVat;
	private final ToaDo viTri; 
	private final BanGhiGiaTocBD giaToc; 
	private final long mocThoiGianSuKien;
	private final long mocThoiGianHeThong;
	private final TrangThaiDuLieu trangThai; 
	private final long t1; 
	private final long doTre; 

	
	public BanGhiSinhHoc(String idConVat, ToaDo viTri, BanGhiGiaTocBD giaToc, long mocThoiGianSuKien,
			long mocThoiGianHeThong, TrangThaiDuLieu trangThai, long t1) {

		if (idConVat == null || idConVat.trim().isEmpty()) {
			throw new IllegalArgumentException("ID con vat khong duoc de trong.");
		}
		if (viTri == null) {
			throw new IllegalArgumentException("Toa do vi tri khong duoc phep null.");
		}
		if (giaToc == null) {
			throw new IllegalArgumentException("Du lieu gia toc khong duoc phep null.");
		}
		if (trangThai == null) {
			throw new IllegalArgumentException("Trang thai du lieu phai duoc dinh nghia ro rang.");
		}
		if (mocThoiGianSuKien <= 0 || mocThoiGianHeThong <= 0 || t1 <= 0) {
			throw new IllegalArgumentException("Cac moc thoi gian phai lon hon 0.");
		}

		this.idConVat = idConVat.trim();
		this.viTri = viTri;
		this.giaToc = giaToc;
		this.mocThoiGianSuKien = mocThoiGianSuKien;
		this.mocThoiGianHeThong = mocThoiGianHeThong;
		this.trangThai = trangThai;
		this.t1 = t1;

		this.doTre = Math.abs(mocThoiGianHeThong - mocThoiGianSuKien);
	}

	
	public String getIdConVat() {
		return idConVat;
	}

	public ToaDo getViTri() {
		return viTri;
	}

	public BanGhiGiaTocBD getGiaToc() {
		return giaToc;
	}

	public long getMocThoiGianSuKien() {
		return mocThoiGianSuKien;
	}

	public long getMocThoiGianHeThong() {
		return mocThoiGianHeThong;
	}

	public TrangThaiDuLieu getTrangThai() {
		return trangThai;
	}

	public long getT1() {
		return t1;
	}

	public long getDoTre() {
		return doTre;
	}

    private String formatArrayChuyenDoi(double[] array) {
        if (array == null || array.length == 0) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
            if (i < array.length - 1) {
                sb.append("|");
            }
        }
        return sb.toString();
    }

  
    public String toCSV() {
        
        String strX = "\"" + formatArrayChuyenDoi(giaToc.getTrucX()) + "\"";
        String strY = "\"" + formatArrayChuyenDoi(giaToc.getTrucY()) + "\"";
        String strZ = "\"" + formatArrayChuyenDoi(giaToc.getTrucZ()) + "\"";

        return String.format("%s,%.6f,%.6f,%s,%s,%s,%d,%d,%s,%d,%d", 
                idConVat, viTri.getViDo(), viTri.getKinhDo(), 
                strX, strY, strZ, 
                mocThoiGianSuKien, mocThoiGianHeThong, 
                trangThai.name(), t1, doTre);
    }
     
        private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        private String formatMillis(long millis) {
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault())
                                .format(formatter);
        }
    
    @Override
    public String toString() {
        return "BanGhiSinhHoc[" +
               "idConVat=" + idConVat +
               ", viTri=" + viTri + 
               ", giaToc=" + giaToc + 
               ", mocThoiGianSuKien=" + formatMillis(mocThoiGianSuKien) +
               ", mocThoiGianHeThong=" + formatMillis(mocThoiGianHeThong) +
               ", trangThai=" + trangThai +
               ", t1=" + formatMillis(t1) +
               ", doTre=" + doTre + " ms]";
    }
   
}