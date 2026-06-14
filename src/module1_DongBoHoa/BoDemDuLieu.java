package module1_DongBoHoa;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// Lop quan ly bo dem du lieu gia toc
public class BoDemDuLieu {

    // Hang doi luu ban ghi gia toc
    private Queue<BanGhiGiaTocBD> hangDoiGiaToc = new LinkedList<>();

    // Luu ban ghi gia toc vao bo dem
    public void luuTam(BanGhiGiaTocBD duLieu) {

        // Neu du lieu null thi khong luu
        if (duLieu == null) {
            return;
        }

        // Them du lieu vao hang doi
        hangDoiGiaToc.add(duLieu);

        // Chi giu toi da 100 ban ghi
        if (hangDoiGiaToc.size() > 100) {
            hangDoiGiaToc.poll();
        }
    }

    // Lay du lieu gia toc da luu ra danh sach
    public List<BanGhiGiaTocBD> layDuLieuRa() {

        // Tra ve danh sach copy tu hang doi
        return new ArrayList<>(hangDoiGiaToc);
    }
}