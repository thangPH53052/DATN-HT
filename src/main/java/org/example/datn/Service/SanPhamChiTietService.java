package org.example.datn.Service;

import org.example.datn.Entity.*;
import org.example.datn.Repository.*;
import org.example.datn.Response.SanPhamResponse;
import org.example.datn.dto.SanPhamDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SanPhamChiTietService {

    @Autowired
    private SanPhamChiTietRepository repository;

    @Autowired
    private MauSacRepository mauSacRepository;

    @Autowired
    private KichThuocRepository kichThuocRepository;

    @Autowired
    private KhuyenMaiRepository khuyenMaiRepository;
    @Autowired
    private SanPhamRepository sanPhamRepository;

    public List<SanPhamChiTiet> getAll() {
        return repository.findAll();
    }

    public List<SanPhamChiTiet> getFullInfo() {
        return repository.getSanPhamDangBanFull();
    }

    public SanPhamChiTiet getById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public void add(Integer idSanPham, Integer idMauSac, Integer idKichThuoc,
            Integer idKhuyenMai, Double giaBan, Double giaNhap,
            Integer soLuong, Boolean trangThai) {

        SanPhamChiTiet ct = new SanPhamChiTiet();
        ct.setSanPham(new SanPham(idSanPham));
        ct.setMauSac(new MauSac(idMauSac));
        ct.setKichThuoc(new KichThuoc(idKichThuoc));
        ct.setKhuyenMai(idKhuyenMai != null ? new KhuyenMai(idKhuyenMai) : null);
        ct.setGiaBan(giaBan);
        ct.setGiaNhap(giaNhap);
        ct.setSoLuong(soLuong);
        ct.setTrangThai(trangThai != null ? trangThai : true);

        repository.save(ct);
    }

    public void update(Integer id, Integer idMauSac, Integer idKichThuoc,
            Integer idKhuyenMai, Double giaBan, Double giaNhap,
            Integer soLuong, Boolean trangThai) {

        SanPhamChiTiet ct = repository.findById(id).orElse(null);
        if (ct == null)
            return;

        ct.setMauSac(mauSacRepository.findById(idMauSac).orElse(null));
        ct.setKichThuoc(kichThuocRepository.findById(idKichThuoc).orElse(null));
        ct.setKhuyenMai(idKhuyenMai != null ? khuyenMaiRepository.findById(idKhuyenMai).orElse(null) : null);
        ct.setGiaBan(giaBan);
        ct.setGiaNhap(giaNhap);
        ct.setSoLuong(soLuong);
        ct.setTrangThai(trangThai != null ? trangThai : true);

        repository.save(ct);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

    public SanPhamChiTiet findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public void update(Integer id, Integer idSanPham, Integer idMauSac, Integer idKichThuoc,
            Integer idKhuyenMai, Double giaBan, Double giaNhap, Integer soLuong, Boolean trangThai) {

        SanPhamChiTiet ct = repository.findById(id).orElse(null);
        if (ct != null) {
            ct.setSanPham(sanPhamRepository.findById(idSanPham).orElse(null));
            ct.setMauSac(mauSacRepository.findById(idMauSac).orElse(null));
            ct.setKichThuoc(kichThuocRepository.findById(idKichThuoc).orElse(null));
            ct.setKhuyenMai(idKhuyenMai != null ? khuyenMaiRepository.findById(idKhuyenMai).orElse(null) : null);
            ct.setGiaBan(giaBan);
            ct.setGiaNhap(giaNhap);
            ct.setSoLuong(soLuong);
            ct.setTrangThai(trangThai);
            repository.save(ct);
        }
    }

    public void chuyenTrangThai(Integer id) {
        SanPhamChiTiet ct = repository.findById(id).orElse(null);
        if (ct != null) {
            ct.setTrangThai(!ct.getTrangThai()); // Đảo trạng thái
            repository.save(ct);
        }
    }

    public boolean isExist(Integer idSanPham, Integer idMauSac, Integer idKichThuoc) {
        return repository.existsBySanPham_IdAndMauSac_IdAndKichThuoc_Id(idSanPham, idMauSac, idKichThuoc);
    }

    public SanPhamResponse layChiTietSanPham(Integer id) {
        SanPhamChiTiet spct = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm chi tiết với id: " + id));

        SanPham sp = spct.getSanPham();
        if (sp == null) {
            throw new RuntimeException("Sản phẩm cha không tồn tại.");
        }

        String hinhAnh = (sp.getHinhAnhList() != null && !sp.getHinhAnhList().isEmpty())
                ? "/uploads/images/" + sp.getHinhAnhList().get(0).getUrl()
                : "img/default.jpg";

        return new SanPhamResponse(
                spct.getId(),
                sp.getTen(),
                spct.getGiaBan(),
                hinhAnh,
                spct.getSoLuong());
    }

}
