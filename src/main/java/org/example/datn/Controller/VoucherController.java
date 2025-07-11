package org.example.datn.Controller;

import org.example.datn.Entity.Voucher;
import org.example.datn.Service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/voucher/view")
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    // Trang chính hiển thị form + danh sách
    @GetMapping
    public String listVoucher(Model model) {
        model.addAttribute("listVoucher", voucherService.getAllVoucher());
        return "voucher/list";
    }

    // Trả về dữ liệu JSON cho 1 voucher (dùng trong fetch)
    @GetMapping("/edit/{id}")
    @ResponseBody
    public Voucher getVoucher(@PathVariable("id") Integer id) {
        return voucherService.getVoucherById(id);
    }

    // Lưu voucher từ JSON (AJAX gửi JSON)
    @PostMapping("/save")
    @ResponseBody
    public String saveVoucher(@RequestBody Voucher voucher) {
        if (voucher.getMa() == null || voucher.getMa().trim().isEmpty()) {
            return "Mã voucher không được để trống!";
        }
        voucherService.saveVoucher(voucher);
        return "Lưu voucher thành công!";
    }

    // Toggle trạng thái voucher (dừng / kích hoạt)
    @GetMapping("/toggle-status/{id}")
    public String toggleStatus(@PathVariable("id") Integer id) {
        voucherService.toggleStatus(id);
        return "redirect:/voucher/view";
    }
}
