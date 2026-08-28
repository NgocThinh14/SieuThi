package com.example.sieuthiweb.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue("CERAMIC")
@Entity
public class HangSanhSu extends HangHoa {

    private static final double VAT_RATE = 0.1;

    private String nhaSanXuat;
    private LocalDate ngayNhapKho;

    public HangSanhSu(String maHang, String tenHang, int soLuongTon, double donGia, String nhaSanXuat, LocalDate ngayNhapKho){
        super(maHang, tenHang, soLuongTon, donGia);
        this.setNhaSanXuat(nhaSanXuat);
        this.setNgayNhapKho(ngayNhapKho);
    }

    public void setNhaSanXuat(String nhaSanXuat){
        if (nhaSanXuat == null || nhaSanXuat.trim().isEmpty()){
            throw new IllegalArgumentException("Nhà sản xuất không được để trống");
        }
        this.nhaSanXuat = nhaSanXuat;
    }
    public void setNgayNhapKho(LocalDate ngayNhapKho){
        if (ngayNhapKho == null || ngayNhapKho.isAfter(LocalDate.now())){
            throw new IllegalArgumentException("Ngày nhập kho không hợp lệ");
        }
        this.ngayNhapKho = ngayNhapKho;
    }
    @Override
    public double getThueVAT(){
        return getDonGia() * VAT_RATE;
    }
    @Override
    public String getDanhGia(){
        long thoiGianLuuKho = ChronoUnit.DAYS.between(this.ngayNhapKho, LocalDate.now());

        if (getSoLuongTon() > 50 && thoiGianLuuKho > 10){
            return HangHoa.BAN_CHAM;
        }
        return HangHoa.KO_DANH_GIA;
    }
//    @Override
//    public String toString(){
//        String Hh = super.toString();
//        return "Hàng sành sứ: \n" + Hh + ", Nhà sản xuất: " + nhaSanXuat + ", Ngày nhập kho: " + ngayNhapKho + ", Tiền VAT: " + getThueVAT() + ", Đánh giá bán buôn: " + getDanhGia();
//    }
}
