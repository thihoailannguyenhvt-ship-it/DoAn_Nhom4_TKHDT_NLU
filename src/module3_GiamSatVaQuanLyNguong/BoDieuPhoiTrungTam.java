package module3_GiamSatVaQuanLyNguong;



import dinhDanh.LoaiHanhDong;
//1. Import các "vũ khí lõi" nằm trong package của chính Module 3
import module3_GiamSatVaQuanLyNguong.NhanHanhVi.*;
import module3_GiamSatVaQuanLyNguong.In.*;

import module3_GiamSatVaQuanLyNguong.NhanVung.*;
import module3_GiamSatVaQuanLyNguong.CongHuongRuiRo.*;
//2. Import các đối tượng dữ liệu và Enum định danh dùng chung của toàn hệ thống
import dinhDanh.*;

//(Có thể dùng import dinhDanh.*; nếu bạn muốn nạp nhanh tất cả Enum nhãn, vùng, mức độ)

/**
* Lớp đóng vai trò "Mặt tiền" (Facade), tiếp nhận gói tin từ bên ngoài 
* và phối hợp các bộ lọc độc lập bên trong package .core
*/
/**
 * Bộ điều phối trung tâm: Nơi thực hiện thu nạp dữ liệu song song 
 * và đưa ra quyết định dựa trên các điều kiện kích hoạt nhanh hoặc Ma trận rủi ro.
 */
public class BoDieuPhoiTrungTam {
    private final BoDanhGiaHanhVi boLuongHanhVi;
    private final BoXuLyNguongVung boLuongKhongGian;
    private final MaTranRuiRo maTranRuiRo;

    public BoDieuPhoiTrungTam(BoDanhGiaHanhVi hanhVi, BoXuLyNguongVung khongGian, MaTranRuiRo maTran) {
        this.boLuongHanhVi = hanhVi;
        this.boLuongKhongGian = khongGian;
        this.maTranRuiRo = maTran;
    }

    public KetQuaDanhGia xuLyGoiTin(BanGhiSinhHoc goiTin) {
        
        
        // BƯỚC 1: ÉP BUỘC CẢ 2 LUỒNG ĐỘC LẬP ĐỀU PHẢI CHẠY KHÔNG ĐƯỢC NGẮT TRUNG CHÂN
      
        // Luồng hành vi xử lý (Kiểm tra SUY_KIET ăn ngay, hoặc tính liên tục của DINH_BAY)
        KetQuaDanhGia kqHanhVi = boLuongHanhVi.danhGia(goiTin);
        
        // Luồng không gian xử lý (Kiểm tra khu dân cư ăn ngay, hoặc chuỗi leo thang rủi ro)
        KetQuaDanhGia kqKhongGian = boLuongKhongGian.danhGia(goiTin);


        // ─────────────────────────────────────────────────────────────────
        // BƯỚC 2: PHÂN TÍCH CÁC ĐIỀU KIỆN ĐỦ SỰ THUYẾT PHỤC ĐỂ GỬI THẲNG M5
        // ─────────────────────────────────────────────────────────────────
        boolean hanhViGuiM5 = (kqHanhVi.getHanhDong() == LoaiHanhDong.GUI_THANG_M5);
        boolean khongGianGuiM5 = (kqKhongGian.getHanhDong() == LoaiHanhDong.GUI_THANG_M5);

        if (hanhViGuiM5 || khongGianGuiM5) {
            // Tình huống đặc biệt: Nếu cả 2 luồng độc lập đều đồng thời đòi gửi thẳng M5,
            // ta chọn kết quả có mức độ nghiêm trọng cao hơn để tối ưu cảnh báo (KHAN_CAP > CANH_BAO)
            if (hanhViGuiM5 && khongGianGuiM5) {
                return (kqHanhVi.getMucDoNghiemTrong().ordinal() >= kqKhongGian.getMucDoNghiemTrong().ordinal()) 
                        ? kqHanhVi : kqKhongGian;
            }
            // Trả về luồng nào đạt đủ điều kiện thuyết phục kích hoạt thẳng M5
            return hanhViGuiM5 ? kqHanhVi : kqKhongGian;
        }


        // ─────────────────────────────────────────────────────────────────
        // BƯỚC 3: "CÒN NẾU KHÔNG NHƯ THẾ THÌ LẠI TIẾP TỤC ĐẾN VỚI MA TRẬN RỦI RO"
        // ─────────────────────────────────────────────────────────────────
        // Rơi vào trường hợp này bao gồm:
        // - Nhãn hành vi bị nhiễu (không đồng nhất) trong 15 phút qua -> Cần thêm nhãn không gian để thẩm định.
        // - Luồng không gian chưa đủ chuỗi leo thang, chưa vào khu dân cư -> Đang trong diện nghi vấn.
        // - Cả 2 luồng đều đang trả về DANG_THEO_DOI (chưa đủ thời gian 15 phút tích lũy).
        // Tất cả các trạng thái lấp lửng này đều được bàn giao cho Ma trận rủi ro tra cứu để lấy quyết định phối hợp.
        return maTranRuiRo.traCuu(goiTin.layNhanTrangThai(), goiTin.layTinhTrangVung());
    }
}
