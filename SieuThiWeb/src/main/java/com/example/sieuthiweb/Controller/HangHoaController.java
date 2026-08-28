package com.example.sieuthiweb.Controller;

import com.example.sieuthiweb.DTO.HangHoaDTO;
import com.example.sieuthiweb.entity.HangDienMay;
import com.example.sieuthiweb.entity.HangSanhSu;
import com.example.sieuthiweb.entity.HangThucPham;
import com.example.sieuthiweb.service.HangHoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hang-hoa")
@CrossOrigin(origins = "*")
public class HangHoaController {

    @Autowired
    private HangHoaService service;

    @GetMapping
    public ResponseEntity<List<HangHoaDTO>> getAll(){
        return ResponseEntity.ok(service.getAllHangHoa());
    }
    @PostMapping("/thuc-pham")
    public ResponseEntity<?> addThucPham(@RequestBody HangThucPham tp){
        try {
            return ResponseEntity.ok(service.saveHangHoa(tp));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/dien-may")
    public ResponseEntity<?> addDienMay(@RequestBody HangDienMay dm){
        try {
            return ResponseEntity.ok(service.saveHangHoa(dm));
        }
        catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/sanh-su")
    public ResponseEntity<?> addSanhSu(@RequestBody HangSanhSu ss){
        try {
            return ResponseEntity.ok(service.saveHangHoa(ss));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/{maHang}")
    public ResponseEntity<?> getById(@PathVariable String maHang) {
        try {
            return ResponseEntity.ok(service.findById(maHang));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PatchMapping("/{maHang}/cap-nhat-ton")
    public ResponseEntity<?> updateStock(@PathVariable String maHang, @RequestParam int delta) {
        try {
            HangHoaDTO updated = service.updateSoLuongTon(maHang, delta);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}