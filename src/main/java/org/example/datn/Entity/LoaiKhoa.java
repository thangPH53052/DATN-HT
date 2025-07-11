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
@Table(name = "LoaiKhoa")
public class LoaiKhoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String ma;

    @Column(nullable = false)
    private String ten;

    private String moTa;
    private Boolean trangThai;

    @OneToMany(mappedBy = "loaiKhoa")
    @JsonIgnore
    private List<SanPham> sanPhams;
}
