package org.example.datn.Controller;

import org.example.datn.Entity.SanPhamChiTiet;
import org.example.datn.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/san-pham-chi-tiet")
public class SanPhamChiTietController {

    @Autowired
    private SanPhamChiTietService chiTietService;

    @Autowired
    private SanPhamService sanPhamService;

    @Autowired
    private MauSacService mauSacService;

    @Autowired
    private KichThuocService kichThuocService;

    @Autowired
    private KhuyenMaiService khuyenMaiService;

    @GetMapping("/view")
    public String hienThi(Model model) {
        model.addAttribute("list", chiTietService.getFullInfo());
        model.addAttribute("sanPhamList", sanPhamService.getAllSanPham());
        model.addAttribute("mauSacList", mauSacService.getAllMauSac());
        model.addAttribute("kichThuocList", kichThuocService.getAllKichThuoc());
        model.addAttribute("khuyenMaiList", khuyenMaiService.getAllKhuyenMai());
        return "ViewSanPhamChiTiet/index"; // ✅ Trỏ đúng tới templates/ViewSanPhamChiTiet/index.html
    }

    @GetMapping("/add")
    public String hienThiFormThem(Model model) {
        model.addAttribute("spct", new SanPhamChiTiet());
        model.addAttribute("sanPhamList", sanPhamService.getAllSanPham());
        model.addAttribute("mauSacList", mauSacService.getAllMauSac());
        model.addAttribute("kichThuocList", kichThuocService.getAllKichThuoc());
        model.addAttribute("khuyenMaiList", khuyenMaiService.getAllKhuyenMai());
        return "ViewSanPhamChiTiet/add";
    }

    @PostMapping("/add")
    public String themChiTiet(@Valid @ModelAttribute("spct") SanPhamChiTiet spct,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            napDanhSach(model);
            return "ViewSanPhamChiTiet/add";
        }

        if (chiTietService.isExist(
                spct.getSanPham().getId(),
                spct.getMauSac().getId(),
                spct.getKichThuoc().getId())) {
            model.addAttribute("errorMessage", "Sản phẩm chi tiết đã tồn tại!");
            napDanhSach(model);
            return "ViewSanPhamChiTiet/add";
        }

        chiTietService.add(
                spct.getSanPham().getId(),
                spct.getMauSac().getId(),
                spct.getKichThuoc().getId(),
                spct.getKhuyenMai() != null ? spct.getKhuyenMai().getId() : null,
                spct.getGiaBan(), spct.getGiaNhap(), spct.getSoLuong(), spct.getTrangThai());

        return "redirect:/san-pham-chi-tiet/view";
    }

    @GetMapping("/update/{id}")
    public String hienThiFormCapNhat(@PathVariable("id") Integer id, Model model) {
        var chiTiet = chiTietService.findById(id); // Bạn phải có phương thức này trong Service
        model.addAttribute("chiTiet", chiTiet);

        model.addAttribute("sanPhamList", sanPhamService.getAllSanPham());
        model.addAttribute("mauSacList", mauSacService.getAllMauSac());
        model.addAttribute("kichThuocList", kichThuocService.getAllKichThuoc());
        model.addAttribute("khuyenMaiList", khuyenMaiService.getAllKhuyenMai());

        return "ViewSanPhamChiTiet/update"; // Trỏ tới update.html
    }

    @PostMapping("/update")
    public String capNhat(@Valid @ModelAttribute("chiTiet") SanPhamChiTiet spct,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            napDanhSach(model);
            return "ViewSanPhamChiTiet/update";
        }

        var current = chiTietService.findById(spct.getId());
        boolean isSame = current.getSanPham().getId().equals(spct.getSanPham().getId())
                && current.getMauSac().getId().equals(spct.getMauSac().getId())
                && current.getKichThuoc().getId().equals(spct.getKichThuoc().getId());

        if (!isSame && chiTietService.isExist(
                spct.getSanPham().getId(),
                spct.getMauSac().getId(),
                spct.getKichThuoc().getId())) {
            model.addAttribute("errorMessage", "Sản phẩm chi tiết đã tồn tại!");
            napDanhSach(model);
            return "ViewSanPhamChiTiet/update";
        }

        chiTietService.update(
                spct.getId(),
                spct.getSanPham().getId(),
                spct.getMauSac().getId(),
                spct.getKichThuoc().getId(),
                spct.getKhuyenMai() != null ? spct.getKhuyenMai().getId() : null,
                spct.getGiaBan(), spct.getGiaNhap(), spct.getSoLuong(), spct.getTrangThai());

        return "redirect:/san-pham-chi-tiet/view";
    }

    @GetMapping("/chuyen-trang-thai/{id}")
    public String chuyenTrangThai(@PathVariable Integer id) {
        chiTietService.chuyenTrangThai(id);
        return "redirect:/san-pham-chi-tiet/view";
    }

    private void napDanhSach(Model model) {
        model.addAttribute("sanPhamList", sanPhamService.getAllSanPham());
        model.addAttribute("mauSacList", mauSacService.getAllMauSac());
        model.addAttribute("kichThuocList", kichThuocService.getAllKichThuoc());
        model.addAttribute("khuyenMaiList", khuyenMaiService.getAllKhuyenMai());
    }

}
