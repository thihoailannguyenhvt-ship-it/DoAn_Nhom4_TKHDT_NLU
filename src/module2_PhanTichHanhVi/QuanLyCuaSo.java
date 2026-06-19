package module2_PhanTichHanhVi;

import java.util.HashMap;
import java.util.Map;

//Tạo class này để chứa tất cả các cửa sổ
public class QuanLyCuaSo {
 private Map<String, CuaSoTruot> dsCuaSo = new HashMap<>();
 
 public void add(String loai, CuaSoTruot cuaSo) { dsCuaSo.put(loai, cuaSo); }
 public CuaSoTruot get(String loai) { return dsCuaSo.get(loai); }
}