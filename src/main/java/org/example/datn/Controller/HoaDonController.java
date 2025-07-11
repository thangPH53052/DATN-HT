package org.example.datn.Controller;

import java.util.List;

import org.example.datn.Entity.HoaDon;
import org.example.datn.Service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/hoa-don")
public class HoaDonController {

    @Autowired
    private HoaDonService hoaDonService;

    @GetMapping("/view")
    public String index(Model model) {
        List<HoaDon> hoaDons = hoaDonService.getAll();

        for (HoaDon hd : hoaDons) {
            System.out.println("Hóa đơn ID: " + hd.getId());
            System.out.println("Khách hàng: " + (hd.getKhachHang() != null ? hd.getKhachHang().getTen() : "NULL"));
            System.out.println("Nhân viên: " + (hd.getNhanVien() != null ? hd.getNhanVien().getTen() : "NULL"));
            System.out.println("Voucher: " + (hd.getVoucher() != null ? hd.getVoucher().getMa() : "NULL"));
        }

        model.addAttribute("hoaDons", hoaDons);
        return "HoaDon/index";
    }

    @GetMapping("/chi-tiet/{id}")
    public String getChiTietHoaDon(@PathVariable("id") Integer id, Model model) {
        HoaDon hoaDon = hoaDonService.getById(id);
        model.addAttribute("chiTiets", hoaDon.getChiTietList());
        return "HoaDon/fragmentChiTiet :: chiTietTable";
    }

    @PostMapping("/cap-nhat-trang-thai")
    @ResponseBody
    public String capNhatTrangThai(@RequestParam Integer hoaDonId, @RequestParam Integer trangThai) {
        HoaDon hoaDon = hoaDonService.getById(hoaDonId);
        if (hoaDon != null) {
            hoaDon.setTrangThai(trangThai);
            hoaDonService.save(hoaDon);
            return "Đã cập nhật trạng thái.";
        }
        return "Không tìm thấy hóa đơn.";
    }

    @GetMapping("/chi-tiet-form/{id}")
    public String xemChiTietForm(@PathVariable("id") Integer id, Model model) {
        HoaDon hoaDon = hoaDonService.getById(id);

        double tongTien = hoaDon.getChiTietList()
                .stream()
                .mapToDouble(ct -> ct.getSanPhamChiTiet().getGiaBan() * ct.getSoLuong())
                .sum();

        model.addAttribute("hoaDon", hoaDon);
        model.addAttribute("tongTien", tongTien); // ✅ Gửi giá trị này ra view

        return "HoaDon/fragmentChiTiet";
    }

}
