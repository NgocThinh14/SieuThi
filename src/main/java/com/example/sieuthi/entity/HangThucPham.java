package com.example.sieuthi.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter


public class HangThucPham extends HangHoa {

    private static final double VAT_RATE = 0.05;

    private LocalDate ngaySanXuat;
    private LocalDate ngayHetHan;
    private String nhaCungCap;

    public HangThucPham(String maHang, String tenHang, int soLuongTon, double donGia,LocalDate ngaySanXuat, LocalDate ngayHetHan, String nhaCungCap){
        super(maHang,tenHang, soLuongTon, donGia);
        this.ngaySanXuat = ngaySanXuat;
        this.ngayHetHan = ngayHetHan;
        this.nhaCungCap = nhaCungCap;
    }
    @Override
    public double getThueVAT(){
        return getDonGia()*VAT_RATE;
    }
    @Override
    public String getDanhGia(){
        if (getSoLuongTon()>0 && LocalDate.now().isAfter(ngayHetHan) ){
            return HangHoa.KHO_BAN;
        }
            return HangHoa.KO_DANH_GIA;
    }

    @Override
    public String toString(){
        String Hh = super.toString();
        return "Hàng thực phẩm: \n"+ Hh + ", Ngày sản xuất: " + ngaySanXuat + ", Ngày hết hạn: " + ngayHetHan + ", Nhà cung cấp: " + nhaCungCap + ", Tiền VAT: " + getThueVAT() + ", Đánh giá bán buôn: " + getDanhGia();
    }
}


