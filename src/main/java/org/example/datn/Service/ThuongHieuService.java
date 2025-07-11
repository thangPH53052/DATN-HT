package org.example.datn.Service;

import org.example.datn.Entity.ThuongHieu;
import org.example.datn.Repository.ThuongHieuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThuongHieuService {
    @Autowired
    private ThuongHieuRepository thuongHieuRepository;

    public List<ThuongHieu> getAllThuongHieu() {
        return thuongHieuRepository.findAll();
    }

    public ThuongHieu getById(Integer id) {
        return thuongHieuRepository.findById(id).orElse(null);
    }

    public void saveOrUpdate(ThuongHieu thuongHieu) {
        thuongHieuRepository.save(thuongHieu);
    }

    public void deleteById(Integer id) {
        thuongHieuRepository.deleteById(id);
    }
}
