package org.example.datn.Controller;

import org.example.datn.Entity.ThuongHieu;
import org.example.datn.Service.ThuongHieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/thuoc-tinh/thuong-hieu")
public class ThuongHieuController {
    @Autowired
    private ThuongHieuService thuongHieuService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("listThuongHieu", thuongHieuService.getAllThuongHieu());
        return "thuoc_tinh/ThuongHieu";
    }

    @GetMapping("/get/{id}")
    @ResponseBody
    public ResponseEntity<ThuongHieu> getById(@PathVariable Integer id) {
        ThuongHieu th = thuongHieuService.getById(id);
        return (th != null) ? ResponseEntity.ok(th) : ResponseEntity.notFound().build();
    }

    @PostMapping("/save")
    @ResponseBody
    public ResponseEntity<String> save(@RequestBody ThuongHieu thuongHieu) {
        if (thuongHieu.getMa() == null || thuongHieu.getTen() == null) {
            return ResponseEntity.badRequest().body("Thiếu mã hoặc tên!");
        }

        thuongHieuService.saveOrUpdate(thuongHieu);
        return ResponseEntity.ok(thuongHieu.getId() == null ? "Thêm mới thành công" : "Cập nhật thành công");
    }
}
