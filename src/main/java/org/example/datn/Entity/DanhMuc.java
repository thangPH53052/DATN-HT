package org.example.datn.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DanhMuc")
public class DanhMuc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String ma;

    @Column(nullable = false)
    private String ten;

    private String moTa;
    private Date ngayTao;
    private Date ngayCapNhat;
    private Boolean trangThai;

    @OneToMany(mappedBy = "danhMuc")
    @JsonIgnore
    private List<SanPham> sanPhams;
}
