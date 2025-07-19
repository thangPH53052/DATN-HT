package org.example.datn.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HinhAnhSanPhamDTO {
    private Integer id;
    private String url;
    private Integer idSanPham;

    public HinhAnhSanPhamDTO(Integer id, Integer idSanPham) {
        this.id = id;
        this.idSanPham = idSanPham;
        this.url = "/images/" + id;
    }
}
