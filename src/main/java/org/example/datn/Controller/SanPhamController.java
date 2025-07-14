package org.example.datn.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.example.datn.Entity.SanPham;
import org.example.datn.Entity.SanPhamChiTiet;
import org.example.datn.Repository.SanPhamChiTietRepository;
import org.example.datn.Response.SanPhamResponse;
import org.example.datn.Service.*;
import org.example.datn.dto.SanPhamDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/san-pham")
@CrossOrigin(origins = "*")
public class SanPhamController {

    @Autowired
    private SanPhamService sanPhamService;
    @Autowired
    private DanhMucService danhMucService;
    @Autowired
    private ThuongHieuService thuongHieuService;
    @Autowired
    private ChatLieuService chatLieuService;
    @Autowired
    private LoaiKhoaService loaiKhoaService;
    @Autowired
    private KieuDayService kieuDayService;
    @Autowired
    private SanPhamChiTietRepository sanPhamChiTietRepository;

    // Xem danh sách
    @GetMapping("/view")
    public String viewSanPham(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer idDanhMuc,
            @RequestParam(required = false) Integer idThuongHieu,
            @RequestParam(required = false) Integer idChatLieu,
            @RequestParam(required = false) Integer idLoaiKhoa,
            @RequestParam(required = false) Integer idKieuDay,
            Model model,
            @ModelAttribute("successMessage") String successMessage,
            @ModelAttribute("errorMessage") String errorMessage) {

        var sanPhamPage = sanPhamService.searchSanPham(keyword, idDanhMuc, idThuongHieu, idChatLieu, idLoaiKhoa,
                idKieuDay, page);

        model.addAttribute("sanPhams", sanPhamPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", sanPhamPage.getTotalPages());

        model.addAttribute("keyword", keyword);
        model.addAttribute("idDanhMuc", idDanhMuc);
        model.addAttribute("idThuongHieu", idThuongHieu);
        model.addAttribute("idChatLieu", idChatLieu);
        model.addAttribute("idLoaiKhoa", idLoaiKhoa);
        model.addAttribute("idKieuDay", idKieuDay);

        model.addAttribute("danhMucs", danhMucService.getAllDanhMuc());
        model.addAttribute("thuongHieus", thuongHieuService.getAllThuongHieu());
        model.addAttribute("chatLieus", chatLieuService.getAllChatLieu());
        model.addAttribute("loaiKhoas", loaiKhoaService.getAllLoaiKhoa());
        model.addAttribute("kieuDays", kieuDayService.getAllKieuDay());

        model.addAttribute("successMessage", successMessage);
        model.addAttribute("errorMessage", errorMessage);

        return "ViewSanPham/index";
    }

    // Form thêm sản phẩm
    @GetMapping("/add")
    public String showAddForm(Model model,
            @ModelAttribute("errorMessage") String errorMessage,
            @ModelAttribute("successMessage") String successMessage) {
        model.addAttribute("danhMucs", danhMucService.getAllDanhMuc());
        model.addAttribute("thuongHieus", thuongHieuService.getAllThuongHieu());
        model.addAttribute("chatLieus", chatLieuService.getAllChatLieu());
        model.addAttribute("loaiKhoas", loaiKhoaService.getAllLoaiKhoa());
        model.addAttribute("kieuDays", kieuDayService.getAllKieuDay());
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("successMessage", successMessage);
        return "ViewSanPham/add";
    }

    // Lưu sản phẩm mới
    @PostMapping("/save")
    public String saveSanPham(
            @RequestParam("ma") String ma,
            @RequestParam("ten") String ten,
            @RequestParam("idDanhMuc") Integer idDanhMuc,
            @RequestParam("idChatLieu") Integer idChatLieu,
            @RequestParam("idLoaiKhoa") Integer idLoaiKhoa,
            @RequestParam("idKieuDay") Integer idKieuDay,
            @RequestParam("idThuongHieu") Integer idThuongHieu,
            @RequestParam(value = "moTa", required = false) String moTa,
            @RequestParam(value = "canNang", required = false) Float canNang,
            @RequestParam(value = "dungTich", required = false) Float dungTich,
            @RequestParam(value = "kichThuoc", required = false) String kichThuoc,
            @RequestParam("trangThai") Boolean trangThai,
            @RequestParam(value = "hinhAnhs", required = false) MultipartFile[] hinhAnhs,
            RedirectAttributes redirectAttributes) {

        try {
            if (ma == null || ma.trim().isEmpty() || ten == null || ten.trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "⚠️ Vui lòng nhập đầy đủ mã và tên sản phẩm.");
                return "redirect:/san-pham/add";
            }

            if (sanPhamService.existsByMa(ma)) {
                redirectAttributes.addFlashAttribute("errorMessage", "❌ Mã sản phẩm đã tồn tại.");
                return "redirect:/san-pham/add";
            }

            sanPhamService.addSanPham(ma, ten, idDanhMuc, idChatLieu, idLoaiKhoa, idKieuDay,
                    idThuongHieu, moTa, canNang, dungTich, kichThuoc, trangThai, hinhAnhs);

            redirectAttributes.addFlashAttribute("successMessage", "✅ Thêm sản phẩm thành công!");
            return "redirect:/san-pham/view";

        } catch (Exception e) {
            String message = e.getMessage();
            if (message != null && message.toLowerCase().contains("unique")
                    && message.toLowerCase().contains("sanpham")) {
                redirectAttributes.addFlashAttribute("errorMessage", "❌ Mã sản phẩm đã tồn tại trong hệ thống.");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "❌ Đã xảy ra lỗi khi thêm sản phẩm.");
            }
            return "redirect:/san-pham/add";
        }
    }

    // Form cập nhật
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model,
            @ModelAttribute("successMessage") String successMessage,
            @ModelAttribute("errorMessage") String errorMessage) {
        SanPhamDTO sanPham = sanPhamService.getSanPhamDTOById(id);
        if (sanPham == null) {
            return "redirect:/san-pham/view";
        }

        model.addAttribute("sanPham", sanPham);
        model.addAttribute("danhMucs", danhMucService.getAllDanhMuc());
        model.addAttribute("thuongHieus", thuongHieuService.getAllThuongHieu());
        model.addAttribute("chatLieus", chatLieuService.getAllChatLieu());
        model.addAttribute("loaiKhoas", loaiKhoaService.getAllLoaiKhoa());
        model.addAttribute("kieuDays", kieuDayService.getAllKieuDay());
        model.addAttribute("successMessage", successMessage);
        model.addAttribute("errorMessage", errorMessage);

        return "ViewSanPham/update";
    }

    // Cập nhật sản phẩm
    @PostMapping("/update/{id}")
    public String updateSanPham(
            @PathVariable("id") Integer id,
            @RequestParam("ma") String ma,
            @RequestParam("ten") String ten,
            @RequestParam("idDanhMuc") Integer idDanhMuc,
            @RequestParam("idChatLieu") Integer idChatLieu,
            @RequestParam("idLoaiKhoa") Integer idLoaiKhoa,
            @RequestParam("idKieuDay") Integer idKieuDay,
            @RequestParam("idThuongHieu") Integer idThuongHieu,
            @RequestParam(value = "moTa", required = false) String moTa,
            @RequestParam(value = "canNang", required = false) Float canNang,
            @RequestParam(value = "dungTich", required = false) Float dungTich,
            @RequestParam(value = "kichThuoc", required = false) String kichThuoc,
            @RequestParam("trangThai") Boolean trangThai,
            @RequestParam(value = "hinhAnhs", required = false) MultipartFile[] hinhAnhs,
            RedirectAttributes redirectAttributes) {

        try {
            if (sanPhamService.isMaTrungKhiUpdate(id, ma)) {
                redirectAttributes.addFlashAttribute("errorMessage", "❌ Mã sản phẩm đã tồn tại ở sản phẩm khác.");
                return "redirect:/san-pham/edit/" + id;
            }

            sanPhamService.updateSanPham(id, ma, ten, idDanhMuc, idChatLieu, idLoaiKhoa,
                    idKieuDay, idThuongHieu, moTa, canNang, dungTich, kichThuoc, trangThai, hinhAnhs);

            redirectAttributes.addFlashAttribute("successMessage", "✅ Cập nhật sản phẩm thành công!");
            return "redirect:/san-pham/view";

        } catch (Exception e) {
            String message = e.getMessage();
            if (message != null && message.toLowerCase().contains("unique")
                    && message.toLowerCase().contains("sanpham")) {
                redirectAttributes.addFlashAttribute("errorMessage", "❌ Mã sản phẩm đã tồn tại trong hệ thống.");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "❌ Đã xảy ra lỗi khi cập nhật sản phẩm.");
            }
            return "redirect:/san-pham/edit/" + id;
        }
    }

    @GetMapping("/api/dang-ban")
    @ResponseBody
    public List<SanPhamResponse> getSanPhamDangBan() {
        List<SanPhamChiTiet> danhSach = sanPhamChiTietRepository.findAll();

        return danhSach.stream()
                .filter(spct -> spct.getSoLuong() != null && spct.getSoLuong() > 0 &&
                        spct.getSanPham() != null &&
                        Boolean.TRUE.equals(spct.getSanPham().getTrangThai()))
                .map(spct -> {
                    SanPham sanPham = spct.getSanPham();
                    String tenSanPham = sanPham.getTen();
                    String hinhAnh = (sanPham.getHinhAnhList() != null && !sanPham.getHinhAnhList().isEmpty())
                            ? "/uploads/images/" + sanPham.getHinhAnhList().get(0).getUrl()
                            : "img/default.jpg";

                    return new SanPhamResponse(
                            spct.getId(),
                            tenSanPham,
                            spct.getGiaBan(),
                            hinhAnh,
                            spct.getSoLuong());
                })
                .collect(Collectors.toList());
    }

}
