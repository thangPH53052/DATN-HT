package org.example.datn.Controller;

import org.example.datn.Service.ThongKeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/thong-ke/view")
public class ThongKeController {

    @Autowired
    private ThongKeService thongKeService;

    @GetMapping
public String hienThiThongKe(Model model) {
    var ngay = thongKeService.thongKeTheoNgay();
    var thang = thongKeService.thongKeTheoThang();
    var nam = thongKeService.thongKeTheoNam();

    System.out.println("⏱ Doanh thu theo ngày: " + ngay.size());
    System.out.println("⏱ Doanh thu theo tháng: " + thang.size());
    System.out.println("⏱ Doanh thu theo năm: " + nam.size());

    model.addAttribute("theoNgay", ngay);
    model.addAttribute("theoThang", thang);
    model.addAttribute("theoNam", nam);
    return "ThongKe/thong-ke";
}
}
