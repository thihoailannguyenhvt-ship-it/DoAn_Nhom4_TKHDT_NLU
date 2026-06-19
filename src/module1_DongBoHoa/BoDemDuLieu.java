package module1_DongBoHoa;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


public class BoDemDuLieu {

    
    private final Queue<BanGhiGiaTocBD> hangDoiGiaToc = new ConcurrentLinkedQueue<>();
    
   
    private final int kichThuocToiDa;

   
    public BoDemDuLieu(int kichThuocToiDa) {
        if (kichThuocToiDa <= 0) {
            throw new IllegalArgumentException("Kich thuoc bo dem phai lon hon 0. Gia tri nhan duoc: " + kichThuocToiDa);
        }
        this.kichThuocToiDa = kichThuocToiDa;
    }

  
    public void luuTam(BanGhiGiaTocBD duLieu) {
        
        if (duLieu == null) return;

      
        hangDoiGiaToc.add(duLieu);

        
        while (hangDoiGiaToc.size() > kichThuocToiDa) {
            hangDoiGiaToc.poll(); 
        }
    }

  
    public List<BanGhiGiaTocBD> layVaLamSachBoDem() {
        List<BanGhiGiaTocBD> ketQua = new ArrayList<>();
       
        BanGhiGiaTocBD item;
        while ((item = hangDoiGiaToc.poll()) != null) {
            ketQua.add(item);
        }
        return ketQua;
    }

   
    public int getSize() {
        return hangDoiGiaToc.size();
    }
}