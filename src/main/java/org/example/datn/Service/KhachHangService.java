package org.example.datn.Service;

import org.example.datn.Entity.KhachHang;
import org.example.datn.Repository.KhachHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KhachHangService {

    @Autowired
    private KhachHangRepository khachHangRepository;

    public List<KhachHang> getAllKhachHang() {
        return khachHangRepository.findAll();
    }

    public Optional<KhachHang> getById(Integer id) {
        return khachHangRepository.findById(id);
    }

    public void save(KhachHang khachHang) {
        khachHangRepository.save(khachHang);
    }

    public void doiTrangThai(Integer id) {
        Optional<KhachHang> optional = khachHangRepository.findById(id);
        optional.ifPresent(kh -> {
            kh.setTrangThai(!kh.getTrangThai());
            khachHangRepository.save(kh);
        });
    }
}
