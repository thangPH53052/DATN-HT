package org.example.datn.Repository;

import org.example.datn.Entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  VoucherRepository extends JpaRepository<Voucher, Integer> {
    
}
