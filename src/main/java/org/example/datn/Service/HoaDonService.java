package org.example.datn.Service;

import org.example.datn.Entity.HoaDon;
import org.example.datn.Repository.HoaDonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HoaDonService {

    @Autowired
    private HoaDonRepository hoaDonRepository;

    public List<HoaDon> getAll() {
        return hoaDonRepository.findAllWithJoins();
    }

    public HoaDon getById(Integer id) {
        return hoaDonRepository.findById(id).orElse(null);
    }

    public HoaDon save(HoaDon hoaDon) {
        return hoaDonRepository.save(hoaDon);
    }

    public void delete(Integer id) {
        hoaDonRepository.deleteById(id);
    }
}
