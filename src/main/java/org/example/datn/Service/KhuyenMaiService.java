package org.example.datn.Service;

import org.example.datn.Entity.KhuyenMai;
import org.example.datn.Entity.SanPhamChiTiet;
import org.example.datn.Repository.KhuyenMaiRepository;
import org.example.datn.Repository.SanPhamChiTietRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class KhuyenMaiService {

    @Autowired
    private KhuyenMaiRepository khuyenMaiRepository;

    @Autowired
    private SanPhamChiTietRepository sanPhamChiTietRepository;

    public List<KhuyenMai> getAllKhuyenMai() {
        return khuyenMaiRepository.findAll();
    }

    public KhuyenMai getById(Integer id) {
        return khuyenMaiRepository.findById(id).orElse(null);
    }

    public KhuyenMai getByMa(String ma) {
        return khuyenMaiRepository.findByMa(ma);
    }

    public boolean isMaKhuyenMaiTrung(String ma, Integer id) {
        KhuyenMai existing = khuyenMaiRepository.findByMa(ma);
        return existing != null && !existing.getId().equals(id);
    }

    public KhuyenMai saveOrUpdate(KhuyenMai khuyenMai) {
        // Tự động chuyển trạng thái về false nếu hết hạn
        if (khuyenMai.getNgayKetThuc() != null && khuyenMai.getNgayKetThuc().before(new Date())) {
            khuyenMai.setTrangThai(false);
        }
        return khuyenMaiRepository.save(khuyenMai);
    }

    public void deleteById(Integer id) {
        khuyenMaiRepository.deleteById(id);
    }

    public List<SanPhamChiTiet> getSanPhamCoKhuyenMai() {
        return sanPhamChiTietRepository.findByKhuyenMaiIsNotNull();
    }

    public List<SanPhamChiTiet> getSanPhamKhongCoKhuyenMai() {
        return sanPhamChiTietRepository.findByKhuyenMaiIsNull();
    }

    public List<SanPhamChiTiet> getTatCaSanPhamVaKhuyenMai() {
        return sanPhamChiTietRepository.findAll();
    }
}
