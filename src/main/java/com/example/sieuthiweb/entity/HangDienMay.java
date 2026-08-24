package com.example.sieuthiweb.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class HangDienMay extends HangHoa {

    private static final double VAT_RATE = 0.1;
    private int thoiGianBaoHanh;
    private double congSuat;

    public HangDienMay(String maHang, String tenHang, int soLuongTon, double donGia, int thoiGianBaoHanh, double congSuat ){
        super(maHang,tenHang,soLuongTon,donGia);
        this.setThoiGianBaoHanh(thoiGianBaoHanh);
        this.setCongSuat(congSuat);
    }

    public void setCongSuat(double congSuat){
        if (congSuat <= 0){
            throw new IllegalArgumentException("Công suất phải lớn hơn 0");
        }
        this.congSuat = congSuat;

    }
    public void setThoiGianBaoHanh(int thang) {
        if (thang <= 0) {
            throw new IllegalArgumentException("Thời gian bảo hành phải lớn hơn 0");
        }
        this.thoiGianBaoHanh = thang;
    }
    @Override
    public double getThueVAT(){
        return getDonGia() * VAT_RATE;
    }

    @Override
    public String getDanhGia(){
        if (getSoLuongTon() < 3){
            return HangHoa.BAN_DUOC;
        }
        return HangHoa.KO_DANH_GIA;
    }
    @Override
    public String toString(){
        String Hh = super.toString();
        return "Hàng điện máy: \n"+ Hh + ", Thời gian bảo hành: " + thoiGianBaoHanh + ", Công suất: " + congSuat + ", Tiền VAT: " + getThueVAT() + ", Đánh giá bán buôn: " + getDanhGia();
    }

}