package com.example.sieuthiweb.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HangHoaDTO {
    private String maHang;
    private String tenHang;
    private int soLuongTon;
    private double donGia;
    private String loaiHang;

    private double tienVat;
    private String danhGia;

    private LocalDate ngaySanXuat;
    private LocalDate ngayHetHan;
    private String nhaCungCap;
    private Integer thoiGianBaoHanh;
    private Double congSuat;
    private String nhaSanXuat;
    private LocalDate ngayNhapKho;


}
