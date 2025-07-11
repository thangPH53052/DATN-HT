package org.example.datn.Controller;

import org.example.datn.Entity.LoaiKhoa;
import org.example.datn.Service.LoaiKhoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/thuoc-tinh/loai-khoa")
public class LoaiKhoaController {
    @Autowired
    private LoaiKhoaService loaiKhoaService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("listLoaiKhoa", loaiKhoaService.getAllLoaiKhoa());
        return "thuoc_tinh/LoaiKhoa";
    }

    @GetMapping("/get/{id}")
    @ResponseBody
    public ResponseEntity<LoaiKhoa> getById(@PathVariable Integer id) {
        LoaiKhoa lk = loaiKhoaService.getById(id);
        return (lk != null) ? ResponseEntity.ok(lk) : ResponseEntity.notFound().build();
    }

    @PostMapping("/save")
    @ResponseBody
    public ResponseEntity<String> save(@RequestBody LoaiKhoa loaiKhoa) {
        if (loaiKhoa.getMa() == null || loaiKhoa.getTen() == null) {
            return ResponseEntity.badRequest().body("Thiếu mã hoặc tên!");
        }
        loaiKhoaService.saveOrUpdate(loaiKhoa);
        return ResponseEntity.ok(loaiKhoa.getId() == null ? "Thêm thành công" : "Cập nhật thành công");
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        loaiKhoaService.deleteById(id);
        return ResponseEntity.ok("Đã xóa thành công");
    }
}
