package org.example.datn.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ThuongHieu")
public class ThuongHieu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String ma;

    @Column(nullable = false)
    private String ten;

    private String moTa;
    private Boolean trangThai;

    @OneToMany(mappedBy = "thuongHieu")
    @JsonIgnore // ❗ Tránh vòng lặp khi trả JSON
    private List<SanPham> sanPhams;
}
