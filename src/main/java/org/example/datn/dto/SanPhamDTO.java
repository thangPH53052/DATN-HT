package org.example.datn.dto;

import lombok.*;
import java.util.List;
import org.example.datn.Entity.SanPham;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SanPhamDTO {
    private Integer id;
    private String ma;
    private String ten;

    // ID các thực thể liên kết
    private Integer danhMucId;
    private Integer chatLieuId;
    private Integer loaiKhoaId;
    private Integer kieuDayId;
    private Integer thuongHieuId;

    // Tên các thực thể liên kết
    private String tenDanhMuc;
    private String tenChatLieu;
    private String tenLoaiKhoa;
    private String tenKieuDay;
    private String tenThuongHieu;

    private String moTa;
    private Float canNang;
    private Float dungTich;
    private String kichThuoc;
    private Boolean trangThai;

    private Double giaBan;              // đồng bộ kiểu Double
    private Integer soLuong;           // nếu bạn dùng field này thì hãy gán nó ở constructor
    private Integer soLuongTon;        // thêm biến bị thiếu
    private String hinhAnhUrl;         // thêm biến bị thiếu
    private List<String> hinhAnhUrls;

    public SanPhamDTO(SanPham sp) {
        this.id = sp.getId();
        this.ten = sp.getTen();

        // Lấy giá bán rẻ nhất
        this.giaBan = sp.getSanPhamChiTietList().stream()
                .mapToDouble(ct -> ct.getGiaBan())
                .min().orElse(0.0);

        // Tổng tồn kho
        this.soLuongTon = sp.getSanPhamChiTietList().stream()
                .mapToInt(ct -> ct.getSoLuong())
                .sum();

        // Gán ảnh đầu tiên nếu có
        if (sp.getHinhAnhList() != null && !sp.getHinhAnhList().isEmpty()) {
            this.hinhAnhUrl = "/images/" + sp.getHinhAnhList().get(0).getId();
        } else {
            this.hinhAnhUrl = "/img/no-image.png"; // fallback ảnh mặc định
        }
    }
}
