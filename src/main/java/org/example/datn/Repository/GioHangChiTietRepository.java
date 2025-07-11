package org.example.datn.Repository;

import java.util.List;

import org.example.datn.Entity.GioHangChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GioHangChiTietRepository extends JpaRepository<GioHangChiTiet, Integer> {
    List<GioHangChiTiet> findByKhachHangIdAndTrangThaiTrue(Integer khachHangId);
}
