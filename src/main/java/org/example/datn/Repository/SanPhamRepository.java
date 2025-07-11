package org.example.datn.Repository;

import java.util.List;

import org.example.datn.Entity.SanPham;
import org.example.datn.Entity.SanPhamChiTiet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SanPhamRepository extends JpaRepository<SanPham, Integer> {

    @Override
    @EntityGraph(attributePaths = "hinhAnhList")
    Page<SanPham> findAll(Pageable pageable);

    @Query("SELECT s FROM SanPhamChiTiet s LEFT JOIN FETCH s.khuyenMai WHERE s.sanPham.trangThai = true")
    List<SanPhamChiTiet> getSanPhamDangBanCoKM();

    @Query("SELECT sp FROM SanPham sp LEFT JOIN FETCH sp.hinhAnhList WHERE sp.id = :id")
    SanPham findByIdWithImages(@Param("id") Integer id);

    @Query("""
                SELECT sp FROM SanPham sp
                LEFT JOIN FETCH sp.danhMuc dm
                LEFT JOIN FETCH sp.thuongHieu th
                LEFT JOIN FETCH sp.chatLieu cl
                LEFT JOIN FETCH sp.loaiKhoa lk
                LEFT JOIN FETCH sp.kieuDay kd
                WHERE (:keyword IS NULL OR sp.ten LIKE %:keyword%)
                  AND (:idDanhMuc IS NULL OR dm.id = :idDanhMuc)
                  AND (:idThuongHieu IS NULL OR th.id = :idThuongHieu)
                  AND (:idChatLieu IS NULL OR cl.id = :idChatLieu)
                  AND (:idLoaiKhoa IS NULL OR lk.id = :idLoaiKhoa)
                  AND (:idKieuDay IS NULL OR kd.id = :idKieuDay)
            """)
    Page<SanPham> searchSanPhamByFilters(@Param("keyword") String keyword,
            @Param("idDanhMuc") Integer idDanhMuc,
            @Param("idThuongHieu") Integer idThuongHieu,
            @Param("idChatLieu") Integer idChatLieu,
            @Param("idLoaiKhoa") Integer idLoaiKhoa,
            @Param("idKieuDay") Integer idKieuDay,
            Pageable pageable);

    boolean existsByMa(String ma);
}
