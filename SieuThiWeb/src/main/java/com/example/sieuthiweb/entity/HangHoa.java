package com.example.sieuthiweb.entity;

import jakarta.persistence.*;
import lombok.*;


@Table(name = "HANG_HOA", indexes = {
        @Index(name = "idx_ma_hang", columnList = "MA_HANG"),
        @Index(name = "idx_ngay_nhap_kho", columnList = "ngayNhapKho")
})@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "LOAI_HANG", discriminatorType = DiscriminatorType.STRING)
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class HangHoa {
    @Id
    @Column(name = "MA_HANG")
    private String maHang;

    @Column(name = "TEN_HANG", nullable = false)
    private String tenHang;

    @Column(name = "SO_LUONG_TON")
    private int soLuongTon;

    @Column(name = "DON_GIA")
    private double donGia;


    public abstract double getThueVAT();
    public abstract String getDanhGia();

    public static final String KO_DANH_GIA = "Không đánh giá";
    public static final String BAN_DUOC = "Bán được";
    public static final String KHO_BAN = "Khó bán";
    public static final String BAN_CHAM = "Bán chậm";


    public void setSoLuongTon(int soLuongTon){
        if (soLuongTon < 0){
            throw new IllegalArgumentException("Số lượng tồn không được nhỏ hơn 0");
        }
        this.soLuongTon = soLuongTon;
    }
    public void setDonGia(double donGia){
        if (donGia <= 0 ){
            throw new IllegalArgumentException("Đơn giá phải lớn hơn 0");
        }
        this.donGia = donGia;
    }
//    @Override
//    public String toString(){
//        return "Mã: " + maHang + ", Tên: " +tenHang + ", Số lượng tồn: " + soLuongTon + ", Đơn giá: " + donGia;
//    }

}