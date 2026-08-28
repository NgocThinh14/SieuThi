package com.example.sieuthi.ui;

import com.example.sieuthi.entity.*;
import com.example.sieuthi.Repository.DanhSachHangHoa;

import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class main {
    private static Scanner sc = new Scanner(System.in);
    private static DanhSachHangHoa khoHang = new DanhSachHangHoa(100);
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("--- QUẢN LÝ SIÊU THỊ ---");
            System.out.println("1. Thêm hàng hóa");
            System.out.println("2. Hiển thị danh sách");
            System.out.println("0. Thoát");
            System.out.print("Lựa chọn: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    menuThemHang();
                    break;
                case 2:
                    khoHang.inDanhSach();
                    break;
            }
        } while (choice != 0);
    }
    private static HangSanhSu nhapSanhSu(){
        try{
            System.out.print("Nhập mã hàng: ");
            String ma = sc.nextLine().trim();
            if (ma.isEmpty()) throw new RuntimeException("Mã hàng không được để trống");
            if (khoHang.timViTri(ma) != -1) throw new RuntimeException("Mã hàng đã tồn tại");

            System.out.print("Nhập tên hàng: ");
            String ten = sc.nextLine().trim();
            if (ten.isEmpty()) throw new RuntimeException("Tên hàng không được để trống");

            System.out.println("Mời nhập số lượng tồn: ");
            int ton = sc.nextInt();
            System.out.println("Mời nhập đơn giá: ");
            double gia = sc.nextDouble();
            sc.nextLine();

            System.out.println("Mời nhập nhà sản xuất: ");
            String NSX = sc.nextLine();

            System.out.println("Mời nhập ngày tháng nhập ko (dd/mm/yyyy)");
            String ngaystr = sc.nextLine();

            LocalDate ngayNhap = LocalDate.parse(ngaystr, formatter);

            return new HangSanhSu(ma, ten, ton, gia, NSX, ngayNhap);
        }catch (Exception e){
            System.out.println("vui lòng nhập đúng ngày tháng theo mẫu ");
            return null;
        }
    }
    private static HangThucPham nhapHangThucPham() {
        try {
            System.out.print("Nhập mã hàng: ");
            String ma = sc.nextLine().trim();
            if (ma.isEmpty()) throw new RuntimeException("Mã hàng không được để trống");
            if (khoHang.timViTri(ma) != -1) throw new RuntimeException("Mã hàng đã tồn tại");

            System.out.print("Nhập tên hàng: ");
            String ten = sc.nextLine().trim();
            if (ten.isEmpty()) throw new RuntimeException("Tên hàng không được để trống");

            System.out.print("Nhập số lượng tồn: ");
            int ton = sc.nextInt();
            System.out.print("Nhập đơn giá: ");
            double gia = sc.nextDouble();
            sc.nextLine();

            System.out.print("Ngày sản xuất (dd/MM/yyyy): ");
            LocalDate ngSX = LocalDate.parse(sc.nextLine(), formatter);

            System.out.print("Ngày hết hạn (dd/MM/yyyy): ");
            LocalDate ngHH = LocalDate.parse(sc.nextLine(), formatter);

            System.out.print("Nhà cung cấp: ");
            String ncc = sc.nextLine();

            return new HangThucPham(ma, ten, ton, gia, ngSX, ngHH, ncc);

        } catch (Exception e) {
            System.out.println(">> LỖI: " + e.getMessage() + " hoặc sai định dạng ngày!");
            return null;
        }
    }
    private static HangDienMay nhapHangDienMay(){
        try{
            System.out.print("Nhập mã hàng: ");
            String ma = sc.nextLine().trim();
            if (ma.isEmpty()) throw new RuntimeException("Mã hàng không được để trống");
            if (khoHang.timViTri(ma) != -1) throw new RuntimeException("Mã hàng đã tồn tại");

            System.out.print("Nhập tên hàng: ");
            String ten = sc.nextLine().trim();
            if (ten.isEmpty()) throw new RuntimeException("Tên hàng không được để trống");

            System.out.print("Nhập số lượng tồn: ");
            int ton = sc.nextInt();
            System.out.print("Nhập đơn giá: ");
            double gia = sc.nextDouble();
            sc.nextLine();

            System.out.println("Nhập thời gian bảo hành: (nhập số tháng) ");
            int tgbh = sc.nextInt();
            sc.nextLine();

            System.out.println("Nhập công suất: ");
            double cs = sc.nextDouble();
            sc.nextLine();

            return new HangDienMay(ma, ten, ton, gia, tgbh, cs);

        }catch (Exception e){
            System.out.println(">> LỖI: " + e.getMessage() + " hoặc sai định dạng ngày!");
            return null;
        }
    }
    private static void menuThemHang() {
        System.out.println("1. Thực phẩm  2. Điện máy  3. Sành sứ");
        int loai = sc.nextInt();
        sc.nextLine();
        HangHoa newHangHoa = null;
        switch (loai){
            case 1:
                newHangHoa = nhapHangThucPham();
                break;
            case 2:
                newHangHoa = nhapHangDienMay();
                break;
            case 3:
                newHangHoa = nhapSanhSu();
                break;
            default:
                System.out.println("loại hàng ko hợp lệ");
                return;
        }
        if (newHangHoa != null){
            try {
                khoHang.add(newHangHoa);
                System.out.println("Thêm hàng thành công tên là: " + newHangHoa.getTenHang());
            }catch (Exception e){
                System.out.println("thất bại" + e.getMessage());
            }
        }
    }
    private static HangHoa nhapThongTinChung(int loai) {
        System.out.print("Nhập mã hàng: ");
        String ma = sc.nextLine();

        if (khoHang.timViTri(ma) != -1) {
            throw new RuntimeException("Mã hàng đã tồn tại trong hệ thống!");
        }

        System.out.print("Nhập tên hàng: ");
        String ten = sc.nextLine();
        System.out.print("Nhập số lượng tồn: ");
        int ton = sc.nextInt();
        System.out.print("Nhập đơn giá: ");
        double gia = sc.nextDouble();
        sc.nextLine();

        return null;
    }
}