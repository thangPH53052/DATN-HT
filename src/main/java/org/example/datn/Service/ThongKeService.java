package org.example.datn.Service;

import org.example.datn.Repository.HoaDonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThongKeService {

    @Autowired
    private HoaDonRepository hoaDonRepository;

    public List<Object[]> thongKeTheoNgay() {
        return hoaDonRepository.thongKeTheoNgay();
    }

    public List<Object[]> thongKeTheoThang() {
        return hoaDonRepository.thongKeTheoThang();
    }

    public List<Object[]> thongKeTheoNam() {
        return hoaDonRepository.thongKeTheoNam();
    }
}
