package org.example.datn.Repository;



import java.util.Optional;

import org.example.datn.Entity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang, Integer> {
    Optional<KhachHang> findByTen(String ten);
}
