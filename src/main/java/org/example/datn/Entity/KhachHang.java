package org.example.datn.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "KhachHang")
public class KhachHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String ma;

    @Column(name = "HoTen")
    private String ten;

    private String sdt;
    private String email;
    private String diaChi;
    private Boolean gioiTinh;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column(name = "NgaySinh")
    private Date ngaySinh;
    private String matKhau;
    private Boolean trangThai;

    @OneToMany(mappedBy = "khachHang")
    private List<HoaDon> hoaDons;

    @OneToMany(mappedBy = "khachHang")
    private List<GioHangChiTiet> gioHangs;

    @OneToMany(mappedBy = "khachHang")
    private List<DanhGia> danhGias;

    public KhachHang(Integer id) {
        this.id = id;
    }
}
