package com.example.sieuthiweb.service;

import com.example.sieuthiweb.DTO.HangHoaDTO;
import com.example.sieuthiweb.entity.HangDienMay;
import com.example.sieuthiweb.entity.HangHoa;
import com.example.sieuthiweb.entity.HangSanhSu;
import com.example.sieuthiweb.entity.HangThucPham;
import com.example.sieuthiweb.repository.HangHoaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HangHoaService {

    @Autowired
    private HangHoaRepository repository;

    public List<HangHoaDTO> getAllHangHoa(){
        return repository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    @Transactional
    public HangHoa saveHangHoa(HangHoa hh) {
        if (repository.existsById(hh.getMaHang())) {
            throw new RuntimeException("Lỗi: Mã hàng '" + hh.getMaHang() + "' đã tồn tại!");
        }
        return repository.save(hh);
    }

    private HangHoaDTO convertToDTO(HangHoa hh) {
        HangHoaDTO dto = new HangHoaDTO();
        dto.setMaHang(hh.getMaHang());
        dto.setTenHang(hh.getTenHang());
        dto.setSoLuongTon(hh.getSoLuongTon());
        dto.setDonGia(hh.getDonGia());
        dto.setTienVat(hh.getThueVAT());
        dto.setDanhGia(hh.getDanhGia());

        if (hh instanceof HangThucPham) {
            HangThucPham tp = (HangThucPham) hh;
            dto.setLoaiHang("FOOD");
            dto.setNgaySanXuat(tp.getNgaySanXuat());
            dto.setNgayHetHan(tp.getNgayHetHan());
            dto.setNhaCungCap(tp.getNhaCungCap());
        } else if (hh instanceof HangDienMay) {
            HangDienMay dm = (HangDienMay) hh;
            dto.setLoaiHang("ELECTRIC");
            dto.setThoiGianBaoHanh(dm.getThoiGianBaoHanh());
            dto.setCongSuat(dm.getCongSuat());
        } else if (hh instanceof HangSanhSu) {
            HangSanhSu ss = (HangSanhSu) hh;
            dto.setLoaiHang("CERAMIC");
            dto.setNhaSanXuat(ss.getNhaSanXuat());
            dto.setNgayNhapKho(ss.getNgayNhapKho());
        }
        return dto;
    }
    public HangHoaDTO findById(String maHang) {
        return repository.findById(maHang)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy mã hàng: " + maHang));
    }
    // Hàm cập nhật số lượng tồn kho chuyên nghiệp
    @Transactional
    public HangHoaDTO updateSoLuongTon(String maHang, int delta) {
        // 1. Tìm hàng hóa trong DB (Nếu không thấy sẽ ném lỗi)
        HangHoa hh = repository.findById(maHang)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy mã hàng: " + maHang));

        // 2. Tính toán số lượng mới
        int soLuongMoi = hh.getSoLuongTon() + delta;

        // 3. Kiểm tra ràng buộc (Không được xuất quá số lượng đang có)
        if (soLuongMoi < 0) {
            throw new RuntimeException("Lỗi: Số lượng xuất vượt quá tồn kho hiện có!");
        }

        // 4. Cập nhật số lượng (Dùng hàm setter đã có logic kiểm tra của bạn)
        hh.setSoLuongTon(soLuongMoi);

        // 5. Lưu lại vào DB
        HangHoa updated = repository.save(hh);

        // 6. Trả về DTO để hiển thị kết quả mới lên giao diện
        return convertToDTO(updated);
    }
}
