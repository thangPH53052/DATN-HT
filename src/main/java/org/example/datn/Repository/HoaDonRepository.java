package org.example.datn.Repository;

import java.util.List;

import org.example.datn.Entity.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, Integer> {
        @Query("SELECT h FROM HoaDon h " +
                        "LEFT JOIN FETCH h.khachHang " +
                        "LEFT JOIN FETCH h.nhanVien " +
                        "LEFT JOIN FETCH h.voucher")
        List<HoaDon> findAllWithJoins();

        @Query(value = "SELECT CONVERT(date, ngayTao) AS ngay, SUM(tongTien) AS doanhThu " +
                        "FROM HoaDon WHERE trangThai = 1 " +
                        "GROUP BY CONVERT(date, ngayTao) " +
                        "ORDER BY ngay", nativeQuery = true)
        List<Object[]> thongKeTheoNgay();

        @Query(value = "SELECT MONTH(ngayTao) AS thang, YEAR(ngayTao) AS nam, SUM(tongTien) AS doanhThu " +
                        "FROM HoaDon WHERE trangThai = 1 " +
                        "GROUP BY MONTH(ngayTao), YEAR(ngayTao) " +
                        "ORDER BY nam, thang", nativeQuery = true)
        List<Object[]> thongKeTheoThang();

        @Query(value = "SELECT YEAR(ngayTao) AS nam, SUM(tongTien) AS doanhThu " +
                        "FROM HoaDon WHERE trangThai = 1 " +
                        "GROUP BY YEAR(ngayTao) " +
                        "ORDER BY nam", nativeQuery = true)
        List<Object[]> thongKeTheoNam();

        long countByKhachHangId(Integer khachHangId);

        List<HoaDon> findTop5ByKhachHangIdAndTrangThai(Integer khachHangId, Integer trangThai);

        long countByKhachHangIdAndTrangThai(Integer khachHangId, Integer trangThai);

        long countByTrangThai(Integer trangThai);

        List<HoaDon> findTop5ByTrangThai(Integer trangThai);
}
