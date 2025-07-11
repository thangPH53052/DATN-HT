package org.example.datn.Controller;

import org.example.datn.Entity.NhanVien;
import org.example.datn.Service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/nhan-vien")
public class NhanVienController {

    @Autowired
    private NhanVienService nhanVienService;

    @GetMapping("/view")
    public String hienThi(Model model) {
        model.addAttribute("list", nhanVienService.getAllNhanVien());
        return "NhanVien/index";
    }

    @GetMapping("/add")
    public String hienThiFormThem(Model model) {
        model.addAttribute("nv", new NhanVien());
        return "NhanVien/add";
    }

    @PostMapping("/add")
    public String them(@ModelAttribute NhanVien nv) {
        nv.setTrangThai(1);
        nhanVienService.save(nv);
        return "redirect:/nhan-vien/view";
    }

    @GetMapping("/update/{id}")
    public String formUpdate(@PathVariable("id") Integer id, Model model) {
        NhanVien nv = nhanVienService.findById(id);
        model.addAttribute("nv", nv);
        return "NhanVien/update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute NhanVien nv) {
        nhanVienService.save(nv);
        return "redirect:/nhan-vien/view";
    }

    @GetMapping("/doi-trang-thai/{id}")
    public String doiTrangThai(@PathVariable("id") Integer id) {
        nhanVienService.chuyenTrangThai(id);
        return "redirect:/nhan-vien/view";
    }
}
