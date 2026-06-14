package module5_DieuPhoi;
package giaodien;

import mohinh.BangCanhBaoModel;
import mohinh.CanhBao;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.function.Consumer;

/**
 * Panel trái — hiển thị JTable danh sách cảnh báo.
 * Khi user chọn hàng → gọi callback lamKhiChonCanhBao để thông báo cho MainFrame.
 */
public class PanelDanhSachCanhBao extends JPanel {

    private final BangCanhBaoModel  bangModel;
    private final JTable            bangCanhBao;
    private Consumer<CanhBao>       lamKhiChonCanhBao; // callback

    // Màu chủ đạo
    private static final Color MAU_XANH_RUNG    = new Color(46, 107, 79);
    private static final Color MAU_XANH_NHAT    = new Color(200, 237, 222);
    private static final Color MAU_NEN_TOOLBAR   = new Color(248, 247, 245);
    private static final Color MAU_VIEN          = new Color(220, 220, 215);

    public PanelDanhSachCanhBao(BangCanhBaoModel bangModel) {
        this.bangModel = bangModel;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        add(taoThanhCongCu(), BorderLayout.NORTH);

        bangCanhBao = taoBang();
        JScrollPane cuonBang = new JScrollPane(bangCanhBao);
        cuonBang.setBorder(BorderFactory.createEmptyBorder());
        cuonBang.getViewport().setBackground(Color.WHITE);
        add(cuonBang, BorderLayout.CENTER);
    }

    // ---- Thanh công cụ phía trên ----
    private JPanel taoThanhCongCu() {
        JPanel thanhCongCu = new JPanel(new BorderLayout());
        thanhCongCu.setBackground(MAU_NEN_TOOLBAR);
        thanhCongCu.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, MAU_VIEN),
            new EmptyBorder(10, 14, 10, 14)
        ));

        JLabel tieuDe = new JLabel("Cảnh báo đang hoạt động");
        tieuDe.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tieuDe.setForeground(new Color(40, 40, 40));
        thanhCongCu.add(tieuDe, BorderLayout.WEST);

        // Các nút lọc
        JPanel nhomLoc = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        nhomLoc.setOpaque(false);
        nhomLoc.add(taoNutLoc("Tất cả", true));
        nhomLoc.add(taoNutLoc("Nguy cấp", false));
        nhomLoc.add(taoNutLoc("Cảnh báo", false));
        thanhCongCu.add(nhomLoc, BorderLayout.EAST);

        return thanhCongCu;
    }

    private JButton taoNutLoc(String nhan, boolean dangChon) {
        JButton nut = new JButton(nhan);
        nut.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        nut.setFocusPainted(false);
        nut.setBorderPainted(true);
        nut.setContentAreaFilled(dangChon);
        if (dangChon) {
            nut.setBackground(MAU_XANH_RUNG);
            nut.setForeground(MAU_XANH_NHAT);
            nut.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(MAU_XANH_RUNG, 1, true),
                new EmptyBorder(3, 10, 3, 10)
            ));
        } else {
            nut.setBackground(Color.WHITE);
            nut.setForeground(new Color(80, 80, 80));
            nut.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(MAU_VIEN, 1, true),
                new EmptyBorder(3, 10, 3, 10)
            ));
        }
        return nut;
    }

    // ---- Tạo JTable ----
    private JTable taoBang() {
        JTable bang = new JTable(bangModel);
        bang.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        bang.setRowHeight(40);
        bang.setShowHorizontalLines(true);
        bang.setShowVerticalLines(false);
        bang.setGridColor(new Color(235, 235, 230));
        bang.setSelectionBackground(new Color(200, 237, 222));
        bang.setSelectionForeground(Color.DARK_GRAY);
        bang.setFillsViewportHeight(true);
        bang.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        bang.getTableHeader().setBackground(MAU_NEN_TOOLBAR);
        bang.getTableHeader().setForeground(new Color(100, 100, 95));
        bang.getTableHeader().setBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, MAU_VIEN)
        );

        // Gắn renderer màu
        ToMauCanhBao toMau = new ToMauCanhBao(bangModel);
        for (int i = 0; i < bangModel.getColumnCount(); i++) {
            bang.getColumnModel().getColumn(i).setCellRenderer(toMau);
        }

        // Độ rộng cột
        int[] doRong = {90, 280, 160, 160, 70, 110};
        for (int i = 0; i < doRong.length; i++) {
            bang.getColumnModel().getColumn(i).setPreferredWidth(doRong[i]);
        }

        // Lắng nghe chọn hàng
        bang.getSelectionModel().addListSelectionListener(su -> {
            if (!su.getValueIsAdjusting() && lamKhiChonCanhBao != null) {
                int hang = bang.getSelectedRow();
                CanhBao cb = bangModel.layCanhBaoTheoHang(hang);
                if (cb != null) lamKhiChonCanhBao.accept(cb);
            }
        });

        return bang;
    }

    // ---- API cho Controller ----
    public void datCallbackChonCanhBao(Consumer<CanhBao> callback) {
        this.lamKhiChonCanhBao = callback;
    }

    public JTable layBang() { return bangCanhBao; }
}
