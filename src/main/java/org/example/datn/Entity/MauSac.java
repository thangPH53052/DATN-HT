package org.example.datn.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "MauSac")
public class MauSac {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String ma;

    @Column(nullable = false)
    private String ten;

    private String maMau;

    private Boolean trangThai;

    @OneToMany(mappedBy = "mauSac")
    @JsonIgnore // ⚠️ Thêm dòng này để tránh lỗi vòng lặp khi trả JSON
    private List<SanPhamChiTiet> chiTietList;

    public MauSac(Integer id) {
        this.id = id;
    }
}
