package module5_CanhBaoVaDieuPhoi;



import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class BoDocKetQuaModule3 {

    public List<CanhBao> docDanhSachCanhBao(String duongDanFile) {
        List<CanhBao> dsCanhBao = new ArrayList<CanhBao>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(duongDanFile));
            String dong;
            int dem = 1;

            while ((dong = br.readLine()) != null) {
                if (!dong.startsWith("BanGhiKetQua[")) {
                    continue;
                }

                String maDongVat = layGiaTri(dong, "maSoDongVat=", ", mucDoNghiemTrong");
                String mucDoText = layGiaTri(dong, "mucDoNghiemTrong=", ", viDo");
                String viDoText = layGiaTri(dong, "viDo=", ", kinhDo");
                String kinhDoText = layGiaTri(dong, "kinhDo=", ", thoiGianSuKien");
                String lyDo = layGiaTri(dong, "lyDo=", ", idVung");

                double viDo = parseDouble(viDoText);
                double kinhDo = parseDouble(kinhDoText);

                MucDoCanhBao mucDo = chuyenMucDo(mucDoText);
                String diaDiem = suyRaDiaDiem(lyDo, kinhDo);
                String trangThai = mucDo == MucDoCanhBao.NGUY_CAP ? "Dang xu ly" : "Dang theo doi";
                String kiemLam = chonKiemLam(dem);

                CanhBao cb = new CanhBao(
                        "CB-" + String.format("%04d", dem),
                        maDongVat,
                        lyDo,
                        diaDiem,
                        new Diem(kinhDo, viDo),
                        System.currentTimeMillis(),
                        mucDo,
                        trangThai,
                        kiemLam
                );

                dsCanhBao.add(cb);
                dem++;
            }

            br.close();

        } catch (Exception e) {
            System.out.println("[Loi] Khong doc duoc file KetQuaModule3.txt: " + e.getMessage());
        }

        return dsCanhBao;
    }

    private String layGiaTri(String dong, String batDau, String ketThuc) {
        try {
            int start = dong.indexOf(batDau);
            if (start < 0) return "";

            start = start + batDau.length();

            int end = dong.indexOf(ketThuc, start);
            if (end < 0) end = dong.length();

            return dong.substring(start, end).trim();
        } catch (Exception e) {
            return "";
        }
    }

    private MucDoCanhBao chuyenMucDo(String mucDoText) {
        if (mucDoText.equalsIgnoreCase("KHAN_CAP")) {
            return MucDoCanhBao.NGUY_CAP;
        }

        if (mucDoText.equalsIgnoreCase("CANH_BAO")) {
            return MucDoCanhBao.CANH_BAO;
        }

        return MucDoCanhBao.BINH_THUONG;
    }

    private String suyRaDiaDiem(String lyDo, double kinhDo) {
        if (lyDo.contains("khu dÃ¢n cÆ°") || lyDo.contains("NGUY_CAP")) {
            return "Khu dan cu";
        }

        if (kinhDo >= 107.455) {
            return "Khu bao ton trung tam";
        }

        if (kinhDo >= 107.445) {
            return "Bia rung Dong Bac";
        }

        if (kinhDo >= 107.438) {
            return "Tuyen duong du lich";
        }

        if (kinhDo >= 107.430) {
            return "Tram quan sat so 2";
        }

        return "Khu Dong Nam";
    }

    private String chonKiemLam(int stt) {
        String[] dsTen = {
                "Nguyen Thi Hoai Lan",
                "Nguyen Vo Huyen Tran",
                "Nguyen Thi Nhu Huynh",
                "Lam Thi Hoang Nhu"
        };

        return dsTen[(stt - 1) % dsTen.length];
    }

    private double parseDouble(String s) {
        try {
            return Double.parseDouble(s.trim());
        } catch (Exception e) {
            return 0;
        }
    }
}
