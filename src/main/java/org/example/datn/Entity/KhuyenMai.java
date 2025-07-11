package org.example.datn.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "KhuyenMai")
public class KhuyenMai {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Mã khuyến mãi không được để trống")
    private String ma;

    @NotBlank(message = "Tên khuyến mãi không được để trống")
    private String ten;

    @NotNull(message = "Ngày bắt đầu không được để trống")
    @Temporal(TemporalType.DATE)
    private Date ngayBatDau;

    @NotNull(message = "Ngày kết thúc không được để trống")
    @Temporal(TemporalType.DATE)
    private Date ngayKetThuc;

    @NotNull(message = "Phần trăm giảm không được để trống")
    @Min(value = 1, message = "Phần trăm giảm phải >= 1")
    @Max(value = 100, message = "Phần trăm giảm phải <= 100")
    private Integer phanTramGiam;

    @NotNull
    private Boolean trangThai = true;

    @OneToMany(mappedBy = "khuyenMai")
    @JsonIgnore
    private List<SanPhamChiTiet> chiTietList;

    public KhuyenMai(Integer id) {
        this.id = id;
    }

    @Transient
    public boolean isHetHan() {
        return ngayKetThuc != null && ngayKetThuc.before(new Date());
    }
}
