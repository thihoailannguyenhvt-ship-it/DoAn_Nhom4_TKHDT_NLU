package module1_DongBoHoa;

import dinhDanh.*;


public class DieuPhoiDongBo {

    private final BoChuyenDoiDuLieu boChuyenDoi;
    private final BoDemDuLieu boDem;
    private final BoNoiSuyGPS boNoiSuy;
    private final QuanLyTrangThai quanLyTrangThai;
    private final CauHinhDongBo cauHinh;

    public DieuPhoiDongBo(BoChuyenDoiDuLieu boChuyenDoi,
                          BoDemDuLieu boDem,
                          BoNoiSuyGPS boNoiSuy,
                          QuanLyTrangThai quanLyTrangThai,
                          CauHinhDongBo cauHinh) {
        this.boChuyenDoi = boChuyenDoi;
        this.boDem = boDem;
        this.boNoiSuy = boNoiSuy;
        this.quanLyTrangThai = quanLyTrangThai;
        this.cauHinh = cauHinh;
    }

    public void thucHienDongBo(String rawLine) {
        try {
         
            BanGhiGiaTocBD giaToc = boChuyenDoi.chuyenDoiGiaToc(rawLine);
            
            if (!BoKiemTraDuLieu.laDuLieuGiaTocHopLe(giaToc.getTrucX(), giaToc.getTrucY(), giaToc.getTrucZ())) {
                throw new NgoaiLeDongBo.InvalidDataFormatException("Dữ liệu gia tốc không hợp lệ (Mảng rỗng hoặc sai lệch).");
            }

            
            ToaDo toaDo;
            TrangThaiDuLieu trangThai;

            if (boChuyenDoi.coGPS(rawLine)) {
                toaDo = boChuyenDoi.chuyenDoiToaDoTuDongCSV(rawLine);
                trangThai = TrangThaiDuLieu.THUC_TE;
                quanLyTrangThai.luuVet(toaDo, giaToc.getMocThoiGianSuKien());
            } else {
                if (!quanLyTrangThai.daCoDuLieu()) {
                    throw new NgoaiLeDongBo.ColdStartException("Chưa có tọa độ GPS ban đầu.");
                }
                toaDo = boNoiSuy.noiSuyToaDoTheoThoiGian(giaToc.getMocThoiGianSuKien());
                trangThai = TrangThaiDuLieu.NOI_SUY;
            }

          
            BanGhiSinhHoc banGhi = new BanGhiSinhHoc(
                    giaToc.getIdConVat(),
                    toaDo,
                    giaToc,
                    giaToc.getMocThoiGianSuKien(),
                    QuanLyThoiGian.layThoiGianHeThongHienTai(),
                    trangThai,
                    QuanLyThoiGian.layThoiGianHeThongHienTai()
            );
       
            long tre = QuanLyThoiGian.tinhKhoangCach(giaToc.getMocThoiGianSuKien(), QuanLyThoiGian.layThoiGianHeThongHienTai());
            if (tre > cauHinh.getGioiHanTreNghiemTrong()) {
                System.out.println("[WARNING] Độ trễ dữ liệu: " + tre + "ms");
            }
            

            
            boDem.luuTam(giaToc); 
            
            
            
        } catch (NgoaiLeDongBo.ColdStartException e) {
            System.out.println("[WARNING] " + e.getMessage());
        } catch (NgoaiLeDongBo.InvalidDataFormatException | NumberFormatException e) {
            System.err.println("[ERROR] Bỏ qua bản ghi lỗi: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("[CRITICAL] Lỗi hệ thống: " + e.getMessage());
        }
    }
}