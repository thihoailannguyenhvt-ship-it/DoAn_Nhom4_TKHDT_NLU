package module5_CanhBaoVaDieuPhoi;

import java.awt.Color;

public enum MucDoCanhBao {
	BINH_THUONG("Bình thường", new Color(68, 68, 65), new Color(241, 239, 232)),
    CANH_BAO("Cảnh báo", new Color(99, 56, 6), new Color(250, 238, 218)),
    NGUY_CAP("Nguy cấp", new Color(121, 31, 31), new Color(252, 235, 235));

    private final String label;
    private final Color textColor;
    private final Color bgColor;

    MucDoCanhBao(String label, Color textColor, Color bgColor) {
        this.label = label;
        this.textColor = textColor;
        this.bgColor = bgColor;
    }

    public String getLabel() { return label; }
    public Color getTextColor() { return textColor; }
    public Color getBgColor() { return bgColor; }
}

