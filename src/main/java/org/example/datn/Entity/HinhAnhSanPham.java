package org.example.datn.Entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "HinhAnhSanPham")
public class HinhAnhSanPham {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String url;

    @Lob
    @Column(name = "duLieuAnh")
    private byte[] duLieuAnh;

    @ManyToOne
    @JoinColumn(name = "idSanPham")
    private SanPham sanPham;
}
