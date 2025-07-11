package org.example.datn.Service;

import org.example.datn.Entity.DanhMuc;
import org.example.datn.Repository.DanhMucRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DanhMucService {
    private final DanhMucRepository danhMucRepository;

    public DanhMucService(DanhMucRepository danhMucRepository) {
        this.danhMucRepository = danhMucRepository;
    }

    public List<DanhMuc> getAllDanhMuc() {
        return danhMucRepository.findAll();
    }

    public DanhMuc getById(Integer id) {
        return danhMucRepository.findById(id).orElse(null);
    }

    public void saveOrUpdate(DanhMuc dm) {
        if (dm.getId() == null) {
            dm.setNgayTao(new Date());
        } else {
            dm.setNgayCapNhat(new Date());
        }
        danhMucRepository.save(dm);
    }
}
