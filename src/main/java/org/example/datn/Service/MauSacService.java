package org.example.datn.Service;

import org.example.datn.Entity.MauSac;
import org.example.datn.Repository.MauSacRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MauSacService {

    @Autowired
    private MauSacRepository mauSacRepository;

    public List<MauSac> getAllMauSac() {
        return mauSacRepository.findAll();
    }

    public MauSac getById(Integer id) {
        return mauSacRepository.findById(id).orElse(null);
    }

    public MauSac saveOrUpdate(MauSac mauSac) {
        return mauSacRepository.save(mauSac);
    }

    public void deleteById(Integer id) {
        mauSacRepository.deleteById(id);
    }
}
