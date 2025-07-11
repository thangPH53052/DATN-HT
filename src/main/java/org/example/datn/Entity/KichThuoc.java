package org.example.datn.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "KichThuoc")
public class KichThuoc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String ma;

    @Column(nullable = false)
    private String ten;
    private String moTa;
    private Boolean trangThai;

    @OneToMany(mappedBy = "kichThuoc")
    @JsonIgnore
    private List<SanPhamChiTiet> chiTietList;

    public KichThuoc(Integer id) {
    this.id = id;
}

}
