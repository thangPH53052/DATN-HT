package org.example.datn.Service;

import java.util.List;

import org.example.datn.Entity.NhanVien;
import org.example.datn.Repository.NhanVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NhanVienService {

    @Autowired
    private NhanVienRepository nhanVienRepository;

    public List<NhanVien> getAllNhanVien() {
        return nhanVienRepository.findAll();
    }

    public void save(NhanVien nv) {
        nhanVienRepository.save(nv);
    }

    public NhanVien findById(Integer id) {
        return nhanVienRepository.findById(id).orElse(null);
    }

    public void chuyenTrangThai(Integer id) {
        NhanVien nv = findById(id);
        if (nv != null) {
            nv.setTrangThai(nv.getTrangThai() == 1 ? 0 : 1);
            save(nv);
        }
    }
}
