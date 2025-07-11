package org.example.datn.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Table(name = "HoaDon")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HoaDon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Date ngayTao;
    private Integer trangThai;
    private Double tongTien;

    @ManyToOne
    @JoinColumn(name = "IDKhachHang")
    private KhachHang khachHang;

    @ManyToOne
    @JoinColumn(name = "IDNhanVien")
    private NhanVien nhanVien;

    @ManyToOne
    @JoinColumn(name = "IDVoucher")
    private Voucher voucher;

    @OneToMany(mappedBy = "hoaDon")
    private List<HoaDonChiTiet> chiTietList;
}
