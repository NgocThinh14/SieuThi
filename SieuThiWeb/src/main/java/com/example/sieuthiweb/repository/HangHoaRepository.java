package com.example.sieuthiweb.repository;


import com.example.sieuthiweb.entity.HangHoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HangHoaRepository extends JpaRepository<HangHoa, String> {

    Optional<HangHoa> findByMaHang(String maHang);

    boolean existsByMaHang(String maHang);

}
