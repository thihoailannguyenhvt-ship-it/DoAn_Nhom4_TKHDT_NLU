package module5_CanhBaoVaDieuPhoi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ManHinhCaiDatHienTruong extends JPanel{
	// Các ô nhập liệu để giả lập cấu hình thông số hiện trường giống HTML
    private JTextField txtNhietDo;
    private JTextField txtTanSuatGPS;
    private JComboBox<String> cbKenhThongBao;

    // Constructor khởi dựng giao diện cho Tab "Cài đặt hiện trường"
    public ManHinhCaiDatHienTruong() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Khung Panel chứa các trường nhập liệu (Form)
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder("Cấu hình thông số trạm cảm biến hiện trường"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Dòng 1: Ngưỡng nhiệt độ
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Ngưỡng cảnh báo nhiệt độ cháy (°C):"), gbc);
        gbc.gridx = 1;
        txtNhietDo = new JTextField("45", 15);
        formPanel.add(txtNhietDo, gbc);

        // Dòng 2: Tần suất gửi gói tin
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Tần suất đồng bộ dữ liệu GPS (giây):"), gbc);
        gbc.gridx = 1;
        txtTanSuatGPS = new JTextField("30", 15);
        formPanel.add(txtTanSuatGPS, gbc);

        // Dòng 3: Kênh truyền thông báo
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Kênh phát tín hiệu khẩn cấp mặc định:"), gbc);
        gbc.gridx = 1;
        cbKenhThongBao = new JComboBox<>(new String[]{"Đồng bộ SMS + App", "Chỉ gửi App", "Chỉ gửi SMS"});
        formPanel.add(cbKenhThongBao, gbc);

        // Nút Lưu cấu hình
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnSave = new JButton("Lưu cấu hình hệ thống");
        btnSave.setBackground(new Color(15, 110, 86)); // Tông màu xanh của hệ thống Forest Guard
        btnSave.setForeground(Color.WHITE);
        btnSave.setFocusPainted(false);
        pnlButtons.add(btnSave);
        formPanel.add(pnlButtons, gbc);

        add(formPanel, BorderLayout.NORTH);
    }

    /**
     * Phương thức hiển thị hồ sơ thông tin động vật thực địa 
     * đúng chính xác 100% theo tên hàm và tham số yêu cầu từ bạn.
     * @param thongBao Đối tượng chứa chi tiết thông tin thông báo từ kiểm lâm
     */
    public void hienThiHoSoConVat(ThongBaoKiemLam thongBao) {
        System.out.println("[View - Hiện trường] Đang trích xuất dữ liệu từ ThongBaoKiemLam sang màn hình cài đặt...");
        // Hiển thị một hộp thoại thông báo kiểm tra luồng kết nối thành công giữa 2 màn hình View
        JOptionPane.showMessageDialog(this, 
            "Đang hiển thị hồ sơ tích hợp từ mã thông báo: " + thongBao.getMaThongBaoGoc(),
            "Hệ thống đồng bộ dữ liệu", 
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    // --- Các hàm Getter bổ trợ truy xuất thông số nhập liệu ---
    public String getNhietDoCaiDat() { return txtNhietDo.getText(); }
    public String getTanSuatGPSCaiDat() { return txtTanSuatGPS.getText(); }
    public String getKenhThongBaoCaiDat() { return (String) cbKenhThongBao.getSelectedItem(); }
}

