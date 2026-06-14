package module2_PhanTichHanhVi;

import java.util.LinkedList;
import java.util.Queue;

public class CuaSoTruot implements IBoNhoDem {
	 // kích thước tối đa của cửa sổ
    private int kichThuocToiDa;

    // hàng đợi lưu dữ liệu
    private Queue<BangGhiSinhHoc> danhSachDem;
           

  
	public CuaSoTruot(int kichThuocToiDa) {
		super();
		this.kichThuocToiDa = kichThuocToiDa;
		this.danhSachDem = new LinkedList<>();
	}

	@Override
	public void tiepNhan(BangGhiSinhHoc data) {
		  // Nếu đầy thì loại phần tử cũ nhất
        if (danhSachDem.size() >= kichThuocToiDa) {
            danhSachDem.poll();
        }
        danhSachDem.add(data);
	}

	@Override
	public void quetVaLoaiBo(long mocThoiGianGioiHan) {

	    while (!danhSachDem.isEmpty()
	            && danhSachDem.peek().getMocThoiGianSuKien()
	                    < mocThoiGianGioiHan) {

	        danhSachDem.poll();
	    }
	}
	public Queue<BangGhiSinhHoc> layDanhSachHienTai() {
        return danhSachDem;
    }

	public int getKichThuocToiDa() {
		return kichThuocToiDa;
	}

	public void setKichThuocToiDa(int kichThuocToiDa) {
		this.kichThuocToiDa = kichThuocToiDa;
	}

	public Queue<BangGhiSinhHoc> getDanhSachDem() {
		return danhSachDem;
	}

	public void setDanhSachDem(Queue<BangGhiSinhHoc> danhSachDem) {
		this.danhSachDem = danhSachDem;
	}
	

	
}
