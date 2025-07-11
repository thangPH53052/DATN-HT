package org.example.datn.Controller;

import org.example.datn.Entity.KichThuoc;
import org.example.datn.Service.KichThuocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/thuoc-tinh/kich-thuoc")
public class KichThuocController {

    @Autowired
    private KichThuocService kichThuocService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("listKichThuoc", kichThuocService.getAllKichThuoc());
        return "thuoc_tinh/KichThuoc";
    }

    @GetMapping("/get/{id}")
    @ResponseBody
    public ResponseEntity<KichThuoc> getById(@PathVariable Integer id) {
        KichThuoc kt = kichThuocService.getById(id);
        return (kt != null) ? ResponseEntity.ok(kt) : ResponseEntity.notFound().build();
    }

    @PostMapping("/save")
    @ResponseBody
    public ResponseEntity<String> save(@RequestBody KichThuoc kichThuoc) {
        if (kichThuoc.getMa() == null || kichThuoc.getTen() == null) {
            return ResponseEntity.badRequest().body("Thiếu mã hoặc tên!");
        }
        kichThuocService.saveOrUpdate(kichThuoc);
        return ResponseEntity.ok(kichThuoc.getId() == null ? "Thêm thành công" : "Cập nhật thành công");
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        kichThuocService.deleteById(id);
        return ResponseEntity.ok("Đã xóa thành công");
    }
}
