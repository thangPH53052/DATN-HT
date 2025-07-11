package org.example.datn.Controller;

import org.example.datn.Entity.KieuDay;
import org.example.datn.Service.KieuDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/thuoc-tinh/kieu-day")
public class KieuDayController {

    @Autowired
    private KieuDayService kieuDayService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("listKieuDay", kieuDayService.getAllKieuDay());
        return "thuoc_tinh/KieuDay";
    }

    @GetMapping("/get/{id}")
    @ResponseBody
    public ResponseEntity<KieuDay> getById(@PathVariable Integer id) {
        KieuDay kd = kieuDayService.getById(id);
        return (kd != null) ? ResponseEntity.ok(kd) : ResponseEntity.notFound().build();
    }

    @PostMapping("/save")
    @ResponseBody
    public ResponseEntity<String> save(@RequestBody KieuDay kieuDay) {
        if (kieuDay.getMa() == null || kieuDay.getTen() == null) {
            return ResponseEntity.badRequest().body("Thiếu mã hoặc tên!");
        }
        kieuDayService.saveOrUpdate(kieuDay);
        return ResponseEntity.ok(kieuDay.getId() == null ? "Thêm thành công" : "Cập nhật thành công");
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        kieuDayService.deleteById(id);
        return ResponseEntity.ok("Đã xóa thành công");
    }
}
