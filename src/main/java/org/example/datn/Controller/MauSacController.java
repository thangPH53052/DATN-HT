package org.example.datn.Controller;

import org.example.datn.Entity.MauSac;
import org.example.datn.Service.MauSacService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/thuoc-tinh/mau-sac")
public class MauSacController {

    @Autowired
    private MauSacService mauSacService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("listMauSac", mauSacService.getAllMauSac());
        return "thuoc_tinh/MauSac";
    }

    @GetMapping("/get/{id}")
    @ResponseBody
    public ResponseEntity<MauSac> getById(@PathVariable Integer id) {
        MauSac ms = mauSacService.getById(id);
        return (ms != null) ? ResponseEntity.ok(ms) : ResponseEntity.notFound().build();
    }

    @PostMapping("/save")
    @ResponseBody
    public ResponseEntity<String> save(@RequestBody MauSac mauSac) {
        if (mauSac.getMa() == null || mauSac.getTen() == null) {
            return ResponseEntity.badRequest().body("Thiếu mã hoặc tên!");
        }

        boolean isNew = (mauSac.getId() == null);
        mauSacService.saveOrUpdate(mauSac);
        return ResponseEntity.ok(isNew ? "Thêm mới thành công" : "Cập nhật thành công");
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        mauSacService.deleteById(id);
        return ResponseEntity.ok("Xoá thành công");
    }
}
