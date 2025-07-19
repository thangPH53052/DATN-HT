package org.example.datn.Repository;

import java.util.List;
import java.util.Optional;

import org.example.datn.Entity.HinhAnhSanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HinhAnhRepository extends JpaRepository<HinhAnhSanPham, Integer> {
    List<HinhAnhSanPham> findBySanPham_Id(Integer sanPhamId);

    Optional<HinhAnhSanPham> findByUrl(String url);

}
