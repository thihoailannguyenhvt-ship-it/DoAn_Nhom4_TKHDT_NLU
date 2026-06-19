package module5_CanhBaoVaDieuPhoi;

import javax.swing.SwingUtilities;

public class MainApp {

    public static void main(String[] args) {

        CanhBaoVaDieuPhoi controller = new CanhBaoVaDieuPhoi();

        controller.khoiTaoKiemLamMacDinh();

        controller.khoiTaoDuLieuTuModule3("Data/Output/KetQuaModule3.txt");

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ForestGuardDashboard(controller).setVisible(true);
            }
        });
    }
}
