package module5_CanhBaoVaDieuPhoi;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class ForestGuardDashboard extends JFrame {

    private CanhBaoVaDieuPhoi controller;

    private JPanel panelCenter;
    private CardLayout cardLayout;

    private Color bg = new Color(28, 31, 26);
    private Color sidebar = new Color(35, 38, 32);
    private Color card = new Color(43, 46, 39);
    private Color active = new Color(57, 64, 52);
    private Color text = new Color(230, 230, 220);

    public ForestGuardDashboard(CanhBaoVaDieuPhoi controller) {
        this.controller = controller;

        setTitle("Forest Guard - He thong dieu phoi");
        setSize(1180, 740);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        khoiTaoGiaoDien();
    }

    private void khoiTaoGiaoDien() {
        getContentPane().removeAll();

        add(taoSidebar(), BorderLayout.WEST);

        cardLayout = new CardLayout();
        panelCenter = new JPanel(cardLayout);

        panelCenter.add(taoDashboard(), "dashboard");
        panelCenter.add(taoManHinhCanhBao(), "canhbao");
        panelCenter.add(taoManHinhLenh(), "lenh");
        panelCenter.add(taoManHinhKiemLam(), "kiemlam");
        panelCenter.add(taoManHinhCaiDat(), "caidat");

        add(panelCenter, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    private JPanel taoSidebar() {
        JPanel p = new JPanel();
        p.setPreferredSize(new Dimension(270, 740));
        p.setBackground(sidebar);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(new EmptyBorder(28, 28, 28, 28));

        p.add(label("Forest Guard", 25, Color.WHITE));
        p.add(label("He thong dieu phoi", 14, new Color(160, 165, 155)));
        p.add(Box.createVerticalStrut(35));

        p.add(menuTitle("TONG QUAN"));
        p.add(menuButton("Bang dieu khien", "dashboard"));

        p.add(menuTitle("CANH BAO"));
        p.add(menuButton("Danh sach canh bao", "canhbao"));

        p.add(menuTitle("DIEU PHOI"));
        p.add(menuButton("Lenh dieu phoi", "lenh"));
        p.add(menuButton("Kiem lam", "kiemlam"));

        p.add(menuTitle("HE THONG"));
        p.add(menuButton("Cai dat hien truong", "caidat"));

        p.add(Box.createVerticalGlue());

        JButton btnReload = menuButton("Lam moi du lieu", "dashboard");
        btnReload.addActionListener(e -> {
            khoiTaoGiaoDien();
            JOptionPane.showMessageDialog(this, "Da lam moi giao dien thanh cong.");
        });
        p.add(btnReload);

        return p;
    }

    private JPanel taoDashboard() {
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(bg);

        main.add(taoHeader("Bang dieu khien"), BorderLayout.NORTH);

        JPanel body = new JPanel(new BorderLayout(22, 22));
        body.setBackground(bg);
        body.setBorder(new EmptyBorder(15, 32, 32, 32));

        JPanel cards = new JPanel(new GridLayout(1, 4, 22, 0));
        cards.setBackground(bg);
        cards.setPreferredSize(new Dimension(0, 125));

        cards.add(cardThongKe("Canh bao dang hoat dong",
                String.valueOf(demCanhBaoDangXuLy()),
                new Color(220, 75, 75)));

        cards.add(cardThongKe("Lenh dieu phoi hom nay",
                String.valueOf(controller.getDsLenh().size()),
                new Color(225, 160, 55)));

        cards.add(cardThongKe("Kiem lam san sang",
                String.valueOf(demKiemLamSanSang()),
                new Color(70, 185, 130)));

        cards.add(cardThongKe("Su co da xu ly",
                String.valueOf(demCanhBaoHoanThanh()),
                Color.WHITE));

        body.add(cards, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(1, 2, 22, 0));
        center.setBackground(bg);

        center.add(taoBangCanhBaoDashboard());
        center.add(taoNhatKy());

        body.add(center, BorderLayout.CENTER);
        main.add(body, BorderLayout.CENTER);

        return main;
    }

    private JPanel taoBangCanhBaoDashboard() {
        JPanel p = khungCard();
        p.setLayout(new BorderLayout(0, 15));

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(card);
        top.add(label("Canh bao gan day", 22, Color.WHITE), BorderLayout.WEST);

        JButton btnTatCa = nutPhu("Xem tat ca");
        btnTatCa.addActionListener(e -> cardLayout.show(panelCenter, "canhbao"));
        top.add(btnTatCa, BorderLayout.EAST);

        p.add(top, BorderLayout.NORTH);
        p.add(taoBangCanhBao(true), BorderLayout.CENTER);

        JPanel actions = new JPanel(new GridLayout(1, 2, 10, 0));
        actions.setBackground(card);

        JButton btnPhatLenh = nutChinh("Phat lenh nhanh");
        btnPhatLenh.addActionListener(e -> phatLenhChoCanhBaoDauTien());

        JButton btnHoanThanh = nutPhu("Hoan thanh CB dau");
        btnHoanThanh.addActionListener(e -> hoanThanhCanhBaoDauTien());

        actions.add(btnPhatLenh);
        actions.add(btnHoanThanh);

        p.add(actions, BorderLayout.SOUTH);

        return p;
    }

    private JScrollPane taoBangCanhBao(boolean nganGon) {
        String[] cols = {"Ma CB", "Dia diem", "Muc do", "Trang thai"};

        List<CanhBao> ds = controller.getDsCanhBao();
        int soDong = nganGon ? Math.min(ds.size(), 5) : ds.size();

        Object[][] data = new Object[soDong][4];

        for (int i = 0; i < soDong; i++) {
            CanhBao cb = ds.get(i);
            data[i][0] = cb.getMaCanhBao();
            data[i][1] = cb.getDiaDiem();
            data[i][2] = cb.getMucDoNguyHiem().getLabel();
            data[i][3] = cb.getTrangThai();
        }

        JTable table = new JTable(new DefaultTableModel(data, cols));
        dinhDangTable(table);

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.getSelectedRow();

                if (row >= 0 && row < controller.getDsCanhBao().size()) {
                    CanhBao cb = controller.getDsCanhBao().get(row);
                    hienThiChiTietCanhBao(cb);
                }
            }
        });

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(null);
        sp.getViewport().setBackground(card);

        return sp;
    }

    private JPanel taoNhatKy() {
        JPanel p = khungCard();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        p.add(label("Nhat ky hoat dong", 22, Color.WHITE));
        p.add(Box.createVerticalStrut(22));

        p.add(log("Canh bao tu Module 3 da duoc tiep nhan", "08:31", new Color(220, 75, 75)));
        p.add(log("Lenh dieu phoi tu dong da duoc tao", "08:28", new Color(55, 190, 140)));
        p.add(log("Kiem lam phu trach da duoc gan theo khu vuc", "08:19", new Color(230, 165, 65)));
        p.add(log("Tin hieu gui den 4 kiem lam vien", "08:17", new Color(55, 190, 140)));
        p.add(log("Du lieu dashboard duoc dong bo tu Controller", "07:55", new Color(55, 190, 140)));

        p.add(Box.createVerticalGlue());

        JButton btn = nutChinh("Mo danh sach lenh");
        btn.addActionListener(e -> cardLayout.show(panelCenter, "lenh"));
        p.add(btn);

        return p;
    }

    private JPanel taoManHinhCanhBao() {
        JPanel p = khungManHinh("Danh sach canh bao");

        p.add(taoBangCanhBao(false), BorderLayout.CENTER);

        JPanel actions = new JPanel(new GridLayout(1, 3, 12, 0));
        actions.setBackground(bg);

        JButton btnChiTiet = nutPhu("Xem chi tiet dau tien");
        btnChiTiet.addActionListener(e -> {
            if (!controller.getDsCanhBao().isEmpty()) {
                hienThiChiTietCanhBao(controller.getDsCanhBao().get(0));
            }
        });

        JButton btnPhatLenh = nutChinh("Phat lenh dieu phoi");
        btnPhatLenh.addActionListener(e -> phatLenhChoCanhBaoDauTien());

        JButton btnHoanThanh = nutPhu("Danh dau hoan thanh");
        btnHoanThanh.addActionListener(e -> hoanThanhCanhBaoDauTien());

        actions.add(btnChiTiet);
        actions.add(btnPhatLenh);
        actions.add(btnHoanThanh);

        p.add(actions, BorderLayout.SOUTH);

        return p;
    }

    private JPanel taoManHinhLenh() {
        JPanel p = khungManHinh("Lenh dieu phoi");

        String[] cols = {"Ma lenh", "Ma canh bao", "Kiem lam", "Thoi gian", "Trang thai"};

        List<LenhDieuPhoi> ds = controller.getDsLenh();
        Object[][] data = new Object[ds.size()][5];

        for (int i = 0; i < ds.size(); i++) {
            LenhDieuPhoi l = ds.get(i);
            data[i][0] = l.getMaLenh();
            data[i][1] = l.getMaCanhBaoLienKet();
            data[i][2] = l.getTenKiemLamNhan();
            data[i][3] = l.getThoiGianPhatHanh();
            data[i][4] = l.getTrangThai();
        }

        JTable table = new JTable(new DefaultTableModel(data, cols));
        dinhDangTable(table);

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(null);
        p.add(sp, BorderLayout.CENTER);

        JButton btn = nutChinh("Xac nhan tat ca lenh da gui");
        btn.addActionListener(e -> JOptionPane.showMessageDialog(
                this,
                "Tat ca lenh da duoc xac nhan gui qua SMS + App + Radio."
        ));

        p.add(btn, BorderLayout.SOUTH);

        return p;
    }

    private JPanel taoManHinhKiemLam() {
        JPanel p = khungManHinh("Danh sach kiem lam");

        String[] cols = {"Ma", "Ten", "Khu vuc", "Trang thai", "Thiet bi"};

        List<KiemLam> ds = controller.getDanhSachKiemLamDangTruc();
        Object[][] data = new Object[ds.size()][5];

        for (int i = 0; i < ds.size(); i++) {
            KiemLam kl = ds.get(i);
            data[i][0] = kl.getMaKiemLam();
            data[i][1] = kl.getTenKiemLam();
            data[i][2] = kl.getKhuVucPhuTrach();
            data[i][3] = kl.getTrangThaiText();
            data[i][4] = kl.getThietBiDauCuoi();
        }

        JTable table = new JTable(new DefaultTableModel(data, cols));
        dinhDangTable(table);

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(null);
        p.add(sp, BorderLayout.CENTER);

        JButton btn = nutChinh("Dat tat ca kiem lam thanh san sang");
        btn.addActionListener(e -> {
            controller.datTatCaKiemLamSanSang();

            JOptionPane.showMessageDialog(
                    this,
                    "Da cap nhat tat ca kiem lam thanh san sang."
            );

            khoiTaoGiaoDien();
            cardLayout.show(panelCenter, "kiemlam");
        });

        p.add(btn, BorderLayout.SOUTH);

        return p;
    }

    private JPanel taoManHinhCaiDat() {
        JPanel p = khungManHinh("Cai dat hien truong");

        JPanel form = khungCard();
        form.setLayout(new GridLayout(4, 2, 15, 15));

        JTextField txtBanKinh = new JTextField(
                controller.getBoLocKiemLam().getBanKinhQuetMacDinh() + ""
        );
        JTextField txtKenh = new JTextField(controller.getCongTruyenTin());
        JTextField txtTanSuat = new JTextField("30 giay");
        JTextField txtNguong = new JTextField("NGUY_CAP");

        form.add(label("Ban kinh quet mac dinh", 15, text));
        form.add(txtBanKinh);

        form.add(label("Cong truyen tin", 15, text));
        form.add(txtKenh);

        form.add(label("Tan suat cap nhat GPS", 15, text));
        form.add(txtTanSuat);

        form.add(label("Nguong canh bao uu tien", 15, text));
        form.add(txtNguong);

        p.add(form, BorderLayout.CENTER);

        JButton btnSave = nutChinh("Luu cau hinh");
        btnSave.addActionListener(e -> {
            try {
                double bk = Double.parseDouble(txtBanKinh.getText());

                controller.capNhatCauHinh(
                        bk,
                        txtKenh.getText()
                );

                JOptionPane.showMessageDialog(
                        this,
                        "Da luu cau hinh thanh cong."
                );

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Ban kinh quet phai la so."
                );
            }
        });

        p.add(btnSave, BorderLayout.SOUTH);

        return p;
    }

    private void phatLenhChoCanhBaoDauTien() {
        LenhDieuPhoi lenh = controller.phatLenhChoCanhBaoDauTien();

        if (lenh == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Khong the phat lenh. Khong co canh bao hoac kiem lam phu hop."
            );
            return;
        }

        JOptionPane.showMessageDialog(
                this,
                "Da phat lenh " + lenh.getMaLenh() + "\n"
                        + "Canh bao: " + lenh.getMaCanhBaoLienKet() + "\n"
                        + "Kiem lam nhan lenh: " + lenh.getTenKiemLamNhan()
        );

        khoiTaoGiaoDien();
    }

    private void hoanThanhCanhBaoDauTien() {
        CanhBao cb = controller.hoanThanhCanhBaoDauTien();

        if (cb == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Khong co canh bao de cap nhat."
            );
            return;
        }

        JOptionPane.showMessageDialog(
                this,
                "Da danh dau " + cb.getMaCanhBao() + " la Hoan thanh."
        );

        khoiTaoGiaoDien();
    }

    private void hienThiChiTietCanhBao(CanhBao cb) {
        String msg = controller.taoNoiDungChiTietCanhBao(cb);

        JOptionPane.showMessageDialog(
                this,
                msg,
                "Chi tiet canh bao",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private JPanel taoHeader(String title) {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(bg);
        header.setBorder(new EmptyBorder(28, 32, 18, 32));

        header.add(label(title, 25, Color.WHITE), BorderLayout.WEST);
        header.add(label("08:42, 18/06/2026        Thong bao: 3", 17, Color.WHITE), BorderLayout.EAST);

        return header;
    }

    private JPanel khungManHinh(String title) {
        JPanel p = new JPanel(new BorderLayout(20, 20));
        p.setBackground(bg);
        p.setBorder(new EmptyBorder(30, 32, 32, 32));
        p.add(label(title, 26, Color.WHITE), BorderLayout.NORTH);

        return p;
    }

    private JPanel cardThongKe(String title, String value, Color valueColor) {
        JPanel p = khungCard();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        p.add(label(title, 15, new Color(180, 185, 175)));
        p.add(Box.createVerticalStrut(12));
        p.add(label(value, 35, valueColor));

        return p;
    }

    private JPanel khungCard() {
        JPanel p = new JPanel();
        p.setBackground(card);
        p.setBorder(new EmptyBorder(22, 22, 22, 22));

        return p;
    }

    private JButton menuButton(String text, String cardName) {
        JButton b = new JButton(text);

        b.setMaximumSize(new Dimension(230, 48));
        b.setAlignmentX(Component.LEFT_ALIGNMENT);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setBackground(active);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Arial", Font.BOLD, 15));

        b.addActionListener(e -> cardLayout.show(panelCenter, cardName));

        return b;
    }

    private JButton nutChinh(String text) {
        JButton b = new JButton(text);

        b.setFocusPainted(false);
        b.setBackground(new Color(55, 150, 110));
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Arial", Font.BOLD, 14));

        return b;
    }

    private JButton nutPhu(String text) {
        JButton b = new JButton(text);

        b.setFocusPainted(false);
        b.setBackground(new Color(70, 75, 65));
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Arial", Font.BOLD, 14));

        return b;
    }

    private JLabel menuTitle(String s) {
        JLabel l = label(s, 13, new Color(130, 135, 125));
        l.setBorder(new EmptyBorder(18, 0, 8, 0));

        return l;
    }

    private JLabel label(String s, int size, Color color) {
        JLabel l = new JLabel(s);

        l.setFont(new Font("Arial", Font.BOLD, size));
        l.setForeground(color);

        return l;
    }

    private JLabel log(String noiDung, String thoiGian, Color mau) {
        JLabel l = new JLabel(
                "<html><span style='color:rgb("
                        + mau.getRed() + "," + mau.getGreen() + "," + mau.getBlue()
                        + ")'>• " + noiDung + "</span><br><span style='color:gray'>"
                        + thoiGian + "</span></html>"
        );

        l.setFont(new Font("Arial", Font.BOLD, 14));
        l.setBorder(new EmptyBorder(8, 0, 10, 0));

        return l;
    }

    private void dinhDangTable(JTable table) {
        table.setRowHeight(50);
        table.setBackground(card);
        table.setForeground(text);
        table.setGridColor(new Color(70, 75, 65));
        table.setFont(new Font("Arial", Font.BOLD, 13));

        table.getTableHeader().setBackground(new Color(35, 38, 32));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
    }

    private int demKiemLamSanSang() {
        int dem = 0;

        for (KiemLam kl : controller.getDanhSachKiemLamDangTruc()) {
            if (kl.isDangRanh()) {
                dem++;
            }
        }

        return dem;
    }

    private int demCanhBaoDangXuLy() {
        int dem = 0;

        for (CanhBao cb : controller.getDsCanhBao()) {
            if (!cb.getTrangThai().equalsIgnoreCase("Hoan thanh")
                    && !cb.getTrangThai().equalsIgnoreCase("Hoàn thành")) {
                dem++;
            }
        }

        return dem;
    }

    private int demCanhBaoHoanThanh() {
        int dem = 0;

        for (CanhBao cb : controller.getDsCanhBao()) {
            if (cb.getTrangThai().equalsIgnoreCase("Hoan thanh")
                    || cb.getTrangThai().equalsIgnoreCase("Hoàn thành")) {
                dem++;
            }
        }

        return dem;
    }
}
