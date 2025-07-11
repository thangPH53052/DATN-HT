package org.example.datn.Service;

import org.example.datn.Entity.KieuDay;
import org.example.datn.Repository.KieuDayRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KieuDayService {
    private final KieuDayRepository kieuDayRepository;

    public KieuDayService(KieuDayRepository kieuDayRepository) {
        this.kieuDayRepository = kieuDayRepository;
    }

    public List<KieuDay> getAllKieuDay() {
        return kieuDayRepository.findAll();
    }

    public KieuDay getById(Integer id) {
        return kieuDayRepository.findById(id).orElse(null);
    }

    public void saveOrUpdate(KieuDay kd) {
        kieuDayRepository.save(kd);
    }

    public void deleteById(Integer id) {
        kieuDayRepository.deleteById(id);
    }
}
