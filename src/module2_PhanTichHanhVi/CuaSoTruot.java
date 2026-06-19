package module2_PhanTichHanhVi;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Collections; // Import để bảo vệ dữ liệu

public class CuaSoTruot implements IBoNhoDem {

    private int kichThuocToiDa;
    private final Queue<BangGhiSinhHoc> danhSachDem;

    public CuaSoTruot(int kichThuocToiDa) {
        this.kichThuocToiDa = kichThuocToiDa;
        this.danhSachDem = new LinkedList<>();
    }

    @Override
    public void tiepNhan(BangGhiSinhHoc data) {
        if (danhSachDem.size() >= kichThuocToiDa) {
            danhSachDem.poll(); 
        }
        danhSachDem.add(data);
    }

    @Override
    public void quetVaLoaiBo(long mocThoiGianGioiHan) {
      
        while (!danhSachDem.isEmpty() && danhSachDem.peek().getMocThoiGianSuKien() < mocThoiGianGioiHan) {
            danhSachDem.poll();
        }
    }

   
    public Queue<BangGhiSinhHoc> layDanhSachHienTai() {
        return danhSachDem; 
    }

    public int getKichThuocToiDa() {
        return kichThuocToiDa;
    }

    
}