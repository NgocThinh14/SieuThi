package com.example.sieuthiweb.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hang-hoa")
public class HangHoaController {

    @GetMapping("/chao-mung")
    public String welcome() {
        return "Chào mừng bạn đến với hệ thống quản lý Siêu thị Web!";
    }
}