package org.example.datn.Controller;

import java.util.Date;
import java.util.List;

import org.example.datn.Entity.KhuyenMai;
import org.example.datn.Repository.SanPhamChiTietRepository;
import org.example.datn.Service.KhuyenMaiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/khuyen-mai/view")
public class KhuyenMaiController {

    private final SanPhamChiTietRepository sanPhamChiTietRepository;
    private final KhuyenMaiService khuyenMaiService;

    public KhuyenMaiController(KhuyenMaiService khuyenMaiService,
                               SanPhamChiTietRepository sanPhamChiTietRepository) {
        this.khuyenMaiService = khuyenMaiService;
        this.sanPhamChiTietRepository = sanPhamChiTietRepository;
    }

    @GetMapping
    public String listPage(Model model, @RequestParam(value = "error", required = false) String error) {
        model.addAttribute("listKhuyenMai", khuyenMaiService.getAllKhuyenMai());
        model.addAttribute("listCoKM", khuyenMaiService.getSanPhamCoKhuyenMai());
        model.addAttribute("listKhongKM", khuyenMaiService.getSanPhamKhongCoKhuyenMai());
        if ("expired".equals(error)) {
            model.addAttribute("errorMessage", "❌ Không thể chuyển sang trạng thái hoạt động vì khuyến mãi đã hết hạn.");
        }
        return "khuyenmai/list";
    }

    @GetMapping("/edit/{id}")
    @ResponseBody
    public KhuyenMai getById(@PathVariable("id") Integer id) {
        return khuyenMaiService.getById(id);
    }

    @PostMapping("/save")
    @ResponseBody
    public String save(@RequestBody KhuyenMai khuyenMai) {
        // RÀNG BUỘC RỖNG & HỢP LỆ
        if (khuyenMai.getMa() == null || khuyenMai.getMa().trim().isEmpty()) {
            return "❌ Mã khuyến mãi không được để trống!";
        }

        if (khuyenMaiService.isMaKhuyenMaiTrung(khuyenMai.getMa(), khuyenMai.getId())) {
            return "⚠️ Mã khuyến mãi đã tồn tại!";
        }

        if (khuyenMai.getTen() == null || khuyenMai.getTen().trim().isEmpty()) {
            return "❌ Tên khuyến mãi không được để trống!";
        }

        if (khuyenMai.getNgayBatDau() == null || khuyenMai.getNgayKetThuc() == null) {
            return "❌ Ngày bắt đầu và kết thúc không được để trống!";
        }

        if (khuyenMai.getNgayKetThuc().before(khuyenMai.getNgayBatDau())) {
            return "❌ Ngày kết thúc phải sau ngày bắt đầu!";
        }

        if (khuyenMai.getPhanTramGiam() == null ||
                khuyenMai.getPhanTramGiam() < 1 ||
                khuyenMai.getPhanTramGiam() > 100) {
            return "❌ Phần trăm giảm phải nằm trong khoảng 1–100!";
        }

        // TỰ ĐỘNG TẮT TRẠNG THÁI NẾU HẾT HẠN
        if (khuyenMai.getNgayKetThuc().before(new Date())) {
            khuyenMai.setTrangThai(false);
        }

        boolean isNew = khuyenMai.getId() == null;
        khuyenMaiService.saveOrUpdate(khuyenMai);
        return isNew ? "✅ Thêm khuyến mãi thành công!" : "✅ Cập nhật khuyến mãi thành công!";
    }

    @GetMapping("/toggle-status/{id}")
    public String toggle(@PathVariable("id") Integer id) {
        KhuyenMai km = khuyenMaiService.getById(id);
        if (km != null) {
            if (km.getNgayKetThuc() != null && km.getNgayKetThuc().before(new Date())) {
                return "redirect:/khuyen-mai/view?error=expired";
            }
            km.setTrangThai(!Boolean.TRUE.equals(km.getTrangThai()));
            khuyenMaiService.saveOrUpdate(km);
        }
        return "redirect:/khuyen-mai/view";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        khuyenMaiService.deleteById(id);
        return "redirect:/khuyen-mai/view";
    }

    @GetMapping("/gan-km")
    public String ganKhuyenMaiForm(Model model) {
        model.addAttribute("listKhuyenMai", khuyenMaiService.getAllKhuyenMai());
        model.addAttribute("listKhongKM", khuyenMaiService.getSanPhamKhongCoKhuyenMai());
        return "khuyenmai/gan-km";
    }

    @PostMapping("/gan-khuyen-mai")
    public String ganKhuyenMaiFormSubmit(@RequestParam("kmId") Integer kmId,
                                         @RequestParam("spctIds") List<Integer> spctIds) {
        KhuyenMai khuyenMai = khuyenMaiService.getById(kmId);
        if (khuyenMai != null) {
            sanPhamChiTietRepository.findAllById(spctIds).forEach(sp -> {
                sp.setKhuyenMai(khuyenMai);
                sanPhamChiTietRepository.save(sp);
            });
        }
        return "redirect:/khuyen-mai/view";
    }

    @GetMapping("/bo-km/{id}")
    public String boKhuyenMai(@PathVariable("id") Integer spctId) {
        sanPhamChiTietRepository.findById(spctId).ifPresent(spct -> {
            spct.setKhuyenMai(null);
            sanPhamChiTietRepository.save(spct);
        });
        return "redirect:/khuyen-mai/view";
    }
}
