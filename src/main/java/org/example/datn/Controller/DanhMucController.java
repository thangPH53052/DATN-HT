package org.example.datn.Controller;

import org.example.datn.Entity.DanhMuc;
import org.example.datn.Service.DanhMucService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/thuoc-tinh/danh-muc")
public class DanhMucController {
    @Autowired
    private DanhMucService danhMucService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("listDanhMuc", danhMucService.getAllDanhMuc());
        return "thuoc_tinh/DanhMuc";
    }

    @GetMapping("/get/{id}")
    @ResponseBody
    public ResponseEntity<DanhMuc> getById(@PathVariable Integer id) {
        DanhMuc dm = danhMucService.getById(id);
        return (dm != null) ? ResponseEntity.ok(dm) : ResponseEntity.notFound().build();
    }

    @PostMapping("/save")
    @ResponseBody
    public ResponseEntity<String> save(@RequestBody DanhMuc danhMuc) {
        if (danhMuc.getMa() == null || danhMuc.getTen() == null) {
            return ResponseEntity.badRequest().body("Thiếu mã hoặc tên!");
        }
        danhMucService.saveOrUpdate(danhMuc);
        return ResponseEntity.ok(danhMuc.getId() == null ? "Thêm mới thành công" : "Cập nhật thành công");
    }
}
