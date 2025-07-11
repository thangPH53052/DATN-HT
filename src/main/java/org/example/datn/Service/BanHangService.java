package org.example.datn.Service;

import org.example.datn.Entity.*;
import org.example.datn.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BanHangService {

    @Autowired
    private HoaDonRepository hoaDonRepo;
    @Autowired
    private KhachHangRepository khachHangRepo;
    @Autowired
    private SanPhamChiTietRepository spctRepo;
    @Autowired
    private HoaDonChiTietRepository hdctRepo;
    @Autowired
    private VoucherRepository voucherRepo;

    public List<HoaDon> getHoaDonTam() {
        return hoaDonRepo.findTop5ByTrangThai(0);
    }

    public void taoHoaDonTam(String tenKhachHang) {
        HoaDon hoaDon = new HoaDon();
        hoaDon.setNgayTao(new Date());
        hoaDon.setTrangThai(0);
        if (tenKhachHang != null && !tenKhachHang.isBlank()) {
            Optional<KhachHang> khOptional = khachHangRepo.findByTen(tenKhachHang);
            khOptional.ifPresent(hoaDon::setKhachHang);
        }
        hoaDonRepo.save(hoaDon);
    }

    public HoaDon getChiTietHoaDon(Integer id) {
        return hoaDonRepo.findById(id).orElse(null);
    }

    public List<SanPhamChiTiet> getAllSanPham() {
        return spctRepo.findAll();
    }

    public void themSanPhamVaoHoaDon(Integer hoaDonId, Integer spctId, Integer soLuong) {
        Optional<HoaDon> hdOpt = hoaDonRepo.findById(hoaDonId);
        Optional<SanPhamChiTiet> spctOpt = spctRepo.findById(spctId);

        if (hdOpt.isPresent() && spctOpt.isPresent()) {
            HoaDon hoaDon = hdOpt.get();
            SanPhamChiTiet spct = spctOpt.get();

            if (soLuong > spct.getSoLuong()) {
                throw new IllegalArgumentException("Số lượng yêu cầu vượt quá số lượng kho.");
            }

            HoaDonChiTiet chiTiet = new HoaDonChiTiet();
            chiTiet.setHoaDon(hoaDon);
            chiTiet.setSanPhamChiTiet(spct);
            chiTiet.setSoLuong(soLuong);
            chiTiet.setDonGia(spct.getGiaSauKhuyenMai());

            hdctRepo.save(chiTiet);

            spct.setSoLuong(spct.getSoLuong() - soLuong);
            spctRepo.save(spct);

            capNhatTongTienHoaDon(hoaDonId);
        }
    }

    public void apDungVoucher(Integer hoaDonId, Integer voucherId) {
        Optional<HoaDon> hdOpt = hoaDonRepo.findById(hoaDonId);
        Optional<Voucher> voucherOpt = voucherRepo.findById(voucherId);

        if (hdOpt.isPresent() && voucherOpt.isPresent()) {
            HoaDon hoaDon = hdOpt.get();
            hoaDon.setVoucher(voucherOpt.get());
            hoaDonRepo.save(hoaDon);
        }
    }

    @Transactional
    public void xoaHoaDon(Integer hoaDonId) {
        Optional<HoaDon> optional = hoaDonRepo.findById(hoaDonId);
        if (optional.isPresent()) {
            HoaDon hoaDon = optional.get();
            List<HoaDonChiTiet> chiTietList = hdctRepo.findByHoaDonId(hoaDonId);

            for (HoaDonChiTiet ct : chiTietList) {
                SanPhamChiTiet spct = ct.getSanPhamChiTiet();
                spct.setSoLuong(spct.getSoLuong() + ct.getSoLuong());
                spctRepo.save(spct);
            }

            hdctRepo.deleteAll(chiTietList);
            hoaDonRepo.deleteById(hoaDonId);
        }
    }

    public void taoHoaDonTamTheoKhach(Integer khachHangId) {
        HoaDon hoaDon = new HoaDon();
        hoaDon.setNgayTao(new Date());
        hoaDon.setTrangThai(0);
        hoaDon.setTongTien(0.0);
        if (khachHangId != null) {
            khachHangRepo.findById(khachHangId).ifPresent(hoaDon::setKhachHang);
        }
        hoaDonRepo.save(hoaDon);
    }

    public List<Voucher> getVoucherDangHoatDong() {
        Date now = new Date();
        return voucherRepo.findAll().stream()
                .filter(v -> Boolean.TRUE.equals(v.getTrangThai())
                        && v.getNgayBatDau() != null && v.getNgayKetThuc() != null
                        && !v.getNgayBatDau().after(now)
                        && !v.getNgayKetThuc().before(now))
                .toList();
    }

    public List<SanPhamChiTiet> getSanPhamDangBan() {
        List<SanPhamChiTiet> list = spctRepo.findAll();
        return list; // Bạn có thể thêm lọc trạng thái nếu cần
    }

    public void capNhatTongTienHoaDon(Integer hoaDonId) {
        Optional<HoaDon> hdOpt = hoaDonRepo.findById(hoaDonId);
        if (hdOpt.isPresent()) {
            HoaDon hoaDon = hdOpt.get();
            List<HoaDonChiTiet> chiTietList = hdctRepo.findByHoaDonId(hoaDonId);

            double tong = 0.0;
            for (HoaDonChiTiet ct : chiTietList) {
                tong += ct.getDonGia() * ct.getSoLuong();
            }

            hoaDon.setTongTien(tong);
            hoaDonRepo.save(hoaDon);
        }
    }

    public void luuHoaDon(HoaDon hoaDon) {
        hoaDonRepo.save(hoaDon);
    }

    public void boVoucher(Integer hoaDonId) {
        Optional<HoaDon> hoaDonOpt = hoaDonRepo.findById(hoaDonId);
        hoaDonOpt.ifPresent(hoaDon -> {
            hoaDon.setVoucher(null);
            hoaDonRepo.save(hoaDon);
        });
    }
}
