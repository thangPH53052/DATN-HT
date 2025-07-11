package org.example.datn.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "NhanVien")
public class NhanVien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ma", nullable = false)
    private String ma;

    @Column(name = "HoTen", nullable = false)
    private String ten;

    @Column(name = "SDT")
    private String sdt;

    @Column(name = "Email")
    private String email;

    @Column(name = "DiaChi")
    private String diaChi;

    @Column(name = "GioiTinh")
    private Boolean gioiTinh;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd") // ← Thêm dòng này
    private Date ngaySinh;

    @Column(name = "MatKhau")
    private String matKhau;

    @Column(name = "VaiTro")
    private Integer vaiTro;

    @Column(name = "TrangThai")
    private Integer trangThai;

    @Column(name = "NgayTao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ngayTao;

    @OneToMany(mappedBy = "nhanVien")
    private List<HoaDon> hoaDons;
}
