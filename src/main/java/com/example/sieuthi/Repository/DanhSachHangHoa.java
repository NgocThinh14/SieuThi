package com.example.sieuthi.Repository;

import com.example.sieuthi.entity.HangHoa;

public class DanhSachHangHoa {

    private HangHoa[] danhSach;
    private int count;

    public DanhSachHangHoa(int n){
        this.danhSach = new HangHoa[n];
        this.count = 0;
    }
    public int timViTri(String maHang){
        for (int i = 0; i < count; i++){
            if (danhSach[i].getMaHang().equals(maHang)){
                return i;
            }
        }
        return -1;
    }
    public void add(HangHoa h){
            if (count >= danhSach.length){
                throw new RuntimeException("kho đã đầy");
            }
            if (timViTri(h.getMaHang()) != -1 ){
                throw new RuntimeException("hàng hóa đã tồn tại");
            }
            danhSach[count] = h;
                count++;
    }
    public void inDanhSach(){
        for (int i = 0; i < count; i++){
            System.out.println(danhSach[i].toString());
        }
    }
}
