package org.example.datn.Controller;

import org.example.datn.Entity.KhachHang;
import org.example.datn.Service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/khach-hang")
public class KhachHangController {

    @Autowired
    private KhachHangService service;

    // Hiển thị danh sách khách hàng
    @GetMapping("view")
    public String index(Model model) {
        model.addAttribute("list", service.getAllKhachHang());
        return "KhachHang/index"; // KHÔNG đổi đường dẫn, theo yêu cầu của bạn
    }

    // Hiển thị form thêm khách hàng
    @GetMapping("/add")
    public String viewAdd(Model model) {
        model.addAttribute("khachHang", new KhachHang());
        return "KhachHang/add"; // Trả về view thêm
    }

    // Xử lý thêm khách hàng
    @PostMapping("/add")
    public String add(@ModelAttribute("khachHang") KhachHang kh) {
        service.save(kh);
        return "redirect:/khach-hang/view";
    }

    // Hiển thị form cập nhật khách hàng
    @GetMapping("/update/{id}")
    public String viewUpdate(@PathVariable Integer id, Model model) {
        KhachHang kh = service.getById(id).orElse(null);
        model.addAttribute("khachHang", kh);
        return "KhachHang/update"; // Trả về view sửa
    }

    // Xử lý cập nhật
    @PostMapping("/update")
    public String update(@ModelAttribute("khachHang") KhachHang kh) {
        service.save(kh);
        return "redirect:/khach-hang/view";
    }

    // Đổi trạng thái hoạt động
    @GetMapping("/doi-trang-thai/{id}")
    public String doiTrangThai(@PathVariable Integer id) {
        service.doiTrangThai(id);
        return "redirect:/khach-hang/view";
    }
}
