package org.example.datn.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SanPhamResponse {
    private Integer id;
    private String tenSanPham;
    private Double giaBan;
    private String hinhAnh;
    private Integer soLuongTon;
}
