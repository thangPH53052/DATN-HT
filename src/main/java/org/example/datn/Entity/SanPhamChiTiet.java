package org.example.datn.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SanPhamChiTiet")
public class SanPhamChiTiet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 0, message = "Số lượng phải lớn hơn hoặc bằng 0")
    private Integer soLuong;

    @NotNull(message = "Giá bán không được để trống")
    @DecimalMin(value = "1000.0", inclusive = true, message = "Giá bán phải >= 1000")
    private Double giaBan;

    @NotNull(message = "Giá nhập không được để trống")
    @DecimalMin(value = "1000.0", inclusive = true, message = "Giá nhập phải >= 1000")
    private Double giaNhap;

    @NotNull(message = "Trạng thái không được để trống")
    private Boolean trangThai = true;

    @ManyToOne
    @JoinColumn(name = "idSanPham", nullable = false)
    @NotNull(message = "Sản phẩm không được để trống")
    private SanPham sanPham;

    @ManyToOne
    @JoinColumn(name = "idMauSac", nullable = false)
    @NotNull(message = "Màu sắc không được để trống")
    private MauSac mauSac;

    @ManyToOne
    @JoinColumn(name = "idKichThuoc", nullable = false)
    @NotNull(message = "Kích thước không được để trống")
    private KichThuoc kichThuoc;

    @ManyToOne
    @JoinColumn(name = "idKhuyenMai")
    private KhuyenMai khuyenMai;

    /**
     * Tính giá sau khuyến mãi nếu khuyến mãi hợp lệ
     */
    @Transient
    public Double getGiaSauKhuyenMai() {
        if (khuyenMai != null
                && Boolean.TRUE.equals(khuyenMai.getTrangThai())
                && khuyenMai.getNgayBatDau() != null
                && khuyenMai.getNgayKetThuc() != null) {

            Date now = new Date();
            if (!khuyenMai.getNgayBatDau().after(now) && !khuyenMai.getNgayKetThuc().before(now)) {
                return giaBan * (1 - khuyenMai.getPhanTramGiam() / 100.0);
            }
        }
        return giaBan;
    }
}
