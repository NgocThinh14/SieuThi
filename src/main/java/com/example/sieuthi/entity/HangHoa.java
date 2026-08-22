package com.example.sieuthi.entity;

import lombok.*;


@Getter
@AllArgsConstructor
public abstract class HangHoa {

    private final String maHang;
    private String tenHang;
    private int soLuongTon;
    private double donGia;

    public abstract double getThueVAT();
    public abstract String getDanhGia();

    public static final String KO_DANH_GIA = "Không đánh giá";
    public static final String BAN_DUOC = "Bán được";
    public static final String KHO_BAN = "Khó bán";
    public static final String BAN_CHAM = "Bán chậm";

    @Override
    public String toString(){
        return "Mã: " + maHang + ", Tên: " +tenHang + ", Số lượng tồn: " + soLuongTon + ", Đơn giá: " + donGia;
    }
    public void setSoLuongTon(int soLuongTon){
        if (soLuongTon >= 0){
            throw new IllegalArgumentException("vui lòng nhập lớn hơn hoặc bằng 0");
        }
        this.soLuongTon = soLuongTon;
    }
    public void setDonGia(double donGia){
        if (donGia > 0 ){
            throw new IllegalArgumentException("vui lòng nhập lớn hơn 0");
        }
        this.donGia = donGia;
    }


}
