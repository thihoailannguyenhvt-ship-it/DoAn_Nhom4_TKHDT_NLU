package module4_GeofenceDongVaNguCanhKhongGian;

public class BoPhanLoaiNguyCo implements INguyCo {

	 public MucDoNguyCo danhGia(double phanTram) {

	        if (phanTram >= 80)
	            return MucDoNguyCo.NGUYCAP;

	        if (phanTram >= 50)
	            return MucDoNguyCo.CANHBAO;

	        if (phanTram >= 20)
	            return MucDoNguyCo.TIENCANHBAO;

	        return MucDoNguyCo.ANTOAN;
	    }

	    public MucDoNguyCo phanLoai(double phanTram, String loaiVung, String hanhVi) {
	        return danhGia(phanTram);
	    }
	}
