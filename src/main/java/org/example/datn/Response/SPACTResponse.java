package org.example.datn.Response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SPACTResponse {
    private Integer id;
    private String tenSanPham;
    private Double giaBan;
    private Double giaSauKhuyenMai;
    private String moTa;
    private String kichThuoc;
    private Float canNang;
    private Float dungTich;
    private String thuongHieu;
    private String chatLieu;
    private String loaiKhoa;
    private String kieuDay;
    private String danhMuc;
    private String mauSac;
    private String kichThuocPhanLoai;
    private Integer soLuong;
    private List<String> hinhAnhUrls;
}
