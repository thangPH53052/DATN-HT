package org.example.datn.Repository;

import java.util.List;

import org.example.datn.Entity.HoaDonChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HoaDonChiTietRepository extends JpaRepository<HoaDonChiTiet, Integer> {
    List<HoaDonChiTiet> findByHoaDonId(Integer hoaDonId);
}
