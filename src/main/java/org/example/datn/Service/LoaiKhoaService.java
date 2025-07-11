package org.example.datn.Service;

import org.example.datn.Entity.LoaiKhoa;
import org.example.datn.Repository.LoaiKhoaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoaiKhoaService {
    private final LoaiKhoaRepository loaiKhoaRepository;

    public LoaiKhoaService(LoaiKhoaRepository loaiKhoaRepository) {
        this.loaiKhoaRepository = loaiKhoaRepository;
    }

    public List<LoaiKhoa> getAllLoaiKhoa() {
        return loaiKhoaRepository.findAll();
    }

    public LoaiKhoa getById(Integer id) {
        return loaiKhoaRepository.findById(id).orElse(null);
    }

    public void saveOrUpdate(LoaiKhoa lk) {
        loaiKhoaRepository.save(lk);
    }

    public void deleteById(Integer id) {
        loaiKhoaRepository.deleteById(id);
    }
}
