package module1_DongBoHoa;


import java.util.concurrent.locks.ReentrantLock;


public class QuanLyTrangThai {

    private ToaDo viTriCuoiCung;
    private long thoiDiemGpsCuoi = -1; 
    private final ReentrantLock lock = new ReentrantLock();

   
    public void luuVet(ToaDo viTri, long thoiDiem) {
        lock.lock();
        try {
          
            if (viTri != null && thoiDiem > this.thoiDiemGpsCuoi) {
                this.viTriCuoiCung = viTri;
                this.thoiDiemGpsCuoi = thoiDiem;
            }
        } finally {
            lock.unlock();
        }
    }

   
    public ToaDo layViTriCuoi() {
        lock.lock();
        try {
            return this.viTriCuoiCung;
        } finally {
            lock.unlock();
        }
    }

   
    public boolean daCoDuLieu() {
        return this.thoiDiemGpsCuoi != -1;
    }

    public long getThoiDiemGpsCuoi() {
        return thoiDiemGpsCuoi;
    }
}