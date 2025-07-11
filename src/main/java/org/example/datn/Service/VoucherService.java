package org.example.datn.Service;

import org.example.datn.Entity.Voucher;
import org.example.datn.Repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public List<Voucher> getAllVoucher() {
        return voucherRepository.findAll();
    }

    public Voucher getVoucherById(Integer id) {
        return voucherRepository.findById(id).orElse(null);
    }

    public void saveVoucher(Voucher voucher) {
        voucherRepository.save(voucher);
    }

    public void toggleStatus(Integer id) {
        Voucher voucher = getVoucherById(id);
        if (voucher != null) {
            voucher.setTrangThai(!Boolean.TRUE.equals(voucher.getTrangThai()));
            voucherRepository.save(voucher);
        }
    }
}
