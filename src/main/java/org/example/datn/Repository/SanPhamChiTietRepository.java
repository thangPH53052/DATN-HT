package org.example.datn.Repository;

import org.example.datn.Entity.SanPhamChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SanPhamChiTietRepository extends JpaRepository<SanPhamChiTiet, Integer> {
    List<SanPhamChiTiet> findBySanPham_Id(Integer idSanPham);

    List<SanPhamChiTiet> findByKhuyenMaiIsNotNull();

    List<SanPhamChiTiet> findByKhuyenMaiIsNull();

    @Query("""
                SELECT spct FROM SanPhamChiTiet spct
                JOIN FETCH spct.sanPham sp
                LEFT JOIN FETCH sp.hinhAnhList
                JOIN FETCH spct.mauSac
                JOIN FETCH spct.kichThuoc
                WHERE sp.trangThai = true
            """)
    List<SanPhamChiTiet> getSanPhamDangBanFull();

    boolean existsBySanPham_IdAndMauSac_IdAndKichThuoc_Id(Integer idSanPham, Integer idMauSac, Integer idKichThuoc);

}
