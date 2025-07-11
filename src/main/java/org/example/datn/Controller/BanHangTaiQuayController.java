package org.example.datn.Controller;

import java.util.List;
import java.util.Optional;

import org.example.datn.Entity.HoaDon;
import org.example.datn.Entity.HoaDonChiTiet;
import org.example.datn.Entity.KhachHang;
import org.example.datn.Entity.SanPhamChiTiet;
import org.example.datn.Repository.HoaDonChiTietRepository;
import org.example.datn.Repository.HoaDonRepository;
import org.example.datn.Repository.KhachHangRepository;
import org.example.datn.Repository.SanPhamChiTietRepository;
import org.example.datn.Service.BanHangService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ban-hang")
public class BanHangTaiQuayController {

    @Autowired
    private BanHangService banHangService;
    @Autowired
    private KhachHangRepository khachHangRepo;

    @Autowired
    private HoaDonChiTietRepository hdctRepo;
    @Autowired
    private HoaDonRepository hoaDonRepo;
    @Autowired
    private SanPhamChiTietRepository spctRepo;

    // Giao diện bước 1: danh sách hóa đơn + tạo mới
    @GetMapping("/view")
    public String hienThiBanHang(Model model) {
        model.addAttribute("hoaDonTamList", banHangService.getHoaDonTam());
        model.addAttribute("khachHangList", khachHangRepo.findAll()); // ✅ THÊM DÒNG NÀY
        return "BanHang/ban-hang-tai-quay";
    }

    @PostMapping("/tao-hoa-don")
    public String taoHoaDonTam(@RequestParam(required = false) Integer khachHangId) {
        banHangService.taoHoaDonTamTheoKhach(khachHangId);
        return "redirect:/ban-hang/view";
    }

    // Giao diện bước 2: chi tiết hóa đơn đã chọn
    @GetMapping("/chon-hoa-don/{id}")
    public String chonHoaDon(@PathVariable Integer id, Model model) {
        List<SanPhamChiTiet> danhSach = banHangService.getSanPhamDangBan();

        System.out.println("👉 BẮT ĐẦU CHỌN HÓA ĐƠN ID: " + id);
        System.out.println("🟨 Số lượng sản phẩm lấy được: " + danhSach.size());
        for (SanPhamChiTiet sp : danhSach) {
            System.out.println("🔹 " + sp.getSanPham().getTen() + " | Trạng thái: " + sp.getSanPham().getTrangThai());
        }

        model.addAttribute("hoaDon", banHangService.getChiTietHoaDon(id));
        model.addAttribute("sanPhamList", danhSach);
        model.addAttribute("voucherList", banHangService.getVoucherDangHoatDong());

        if (danhSach == null || danhSach.isEmpty()) {
            model.addAttribute("loiSanPham", "Không tìm thấy sản phẩm nào đang được bán.");
        }

        return "BanHang/chi-tiet-don-hang";
    }

    @PostMapping("/them-san-pham")
    public String themSanPhamVaoHoaDon(@RequestParam Integer hoaDonId,
            @RequestParam(required = false) Integer sanPhamChiTietId,
            @RequestParam(required = false) Integer soLuong,
            Model model) {
        if (sanPhamChiTietId == null || soLuong == null || soLuong <= 0) {
            model.addAttribute("hoaDon", banHangService.getChiTietHoaDon(hoaDonId));
            model.addAttribute("sanPhamList", banHangService.getSanPhamDangBan());
            model.addAttribute("voucherList", banHangService.getVoucherDangHoatDong());
            model.addAttribute("themSanPhamLoi", "Vui lòng chọn sản phẩm và nhập số lượng hợp lệ.");
            model.addAttribute("moModal", true);
            return "BanHang/chi-tiet-don-hang";
        }

        banHangService.themSanPhamVaoHoaDon(hoaDonId, sanPhamChiTietId, soLuong);

        // ❗ Cập nhật lại tổng tiền sau khi thêm
        banHangService.capNhatTongTienHoaDon(hoaDonId);

        return "redirect:/ban-hang/chon-hoa-don/" + hoaDonId;
    }

    @PostMapping("/chon-voucher")
    public String chonVoucher(@RequestParam Integer hoaDonId,
            @RequestParam(required = false) Integer voucherId) {

        if (voucherId != null) {
            // Áp dụng voucher như cũ
            banHangService.apDungVoucher(hoaDonId, voucherId);
        } else {
            // ✅ Bỏ áp dụng voucher
            banHangService.boVoucher(hoaDonId);
        }

        return "redirect:/ban-hang/chon-hoa-don/" + hoaDonId;
    }

    @PostMapping("/xoa-chi-tiet")
    public String xoaChiTietHoaDon(@RequestParam Integer chiTietId) {
        Optional<HoaDonChiTiet> opt = hdctRepo.findById(chiTietId);
        if (opt.isPresent()) {
            HoaDonChiTiet ct = opt.get();
            Integer hoaDonId = ct.getHoaDon().getId();

            // ✅ Trả lại số lượng vào kho
            SanPhamChiTiet spct = ct.getSanPhamChiTiet();
            spct.setSoLuong(spct.getSoLuong() + ct.getSoLuong());
            spctRepo.save(spct);

            // Xóa chi tiết hóa đơn
            hdctRepo.deleteById(chiTietId);

            // Cập nhật tổng tiền hóa đơn
            banHangService.capNhatTongTienHoaDon(hoaDonId);

            return "redirect:/ban-hang/chon-hoa-don/" + hoaDonId;
        }

        return "redirect:/ban-hang/view";
    }

    @PostMapping("/them-khach-hang")
    public String themKhachHangMoi(@RequestParam String ten, @RequestParam String sdt) {
        KhachHang kh = new KhachHang();
        kh.setTen(ten);
        kh.setSdt(sdt);
        kh.setTrangThai(true);
        khachHangRepo.save(kh);
        return "redirect:/ban-hang/view";

    }

    @GetMapping("/xoa-hoa-don/{id}")
    public String xoaHoaDon(@PathVariable("id") Integer id) {
        banHangService.xoaHoaDon(id);
        return "redirect:/ban-hang/view";
    }

   @PostMapping("/thanh-toan")
public String thanhToanHoaDon(@RequestParam Integer hoaDonId,
                               @RequestParam(required = false) Double tienKhachDua,
                               @RequestParam("phuongThuc") String phuongThuc,
                               Model model) {

    HoaDon hoaDon = banHangService.getChiTietHoaDon(hoaDonId);

    if (hoaDon == null || hoaDon.getTongTien() == null) {
        model.addAttribute("loiThanhToan", "Hóa đơn không tồn tại hoặc chưa có tổng tiền.");
        return "redirect:/ban-hang/chon-hoa-don/" + hoaDonId;
    }

    double tongSauGiam = hoaDon.getTongTien();
    if (hoaDon.getVoucher() != null && hoaDon.getVoucher().getPhanTramGiam() != null) {
        tongSauGiam *= (1 - hoaDon.getVoucher().getPhanTramGiam() / 100.0);
    }

    if ("TIEN_MAT".equals(phuongThuc)) {
        if (tienKhachDua == null || tienKhachDua < tongSauGiam) {
            model.addAttribute("hoaDon", hoaDon);
            model.addAttribute("sanPhamList", banHangService.getSanPhamDangBan());
            model.addAttribute("voucherList", banHangService.getVoucherDangHoatDong());
            model.addAttribute("loiThanhToan", "Vui lòng nhập số tiền khách đưa hợp lệ.");
            return "BanHang/chi-tiet-don-hang";
        }
    }

    hoaDon.setTrangThai(1); // Đã thanh toán
    banHangService.luuHoaDon(hoaDon);

    if ("TIEN_MAT".equals(phuongThuc)) {
        double tienThua = tienKhachDua - tongSauGiam;
        model.addAttribute("tienThua", tienThua);
    }

    return "redirect:/ban-hang/view";
}

    @PostMapping("/cap-nhat-so-luong")
    public String capNhatSoLuong(
            @RequestParam Integer chiTietId,
            @RequestParam Integer soLuongMoi,
            Model model) {
        Optional<HoaDonChiTiet> opt = hdctRepo.findById(chiTietId);
        if (opt.isPresent()) {
            HoaDonChiTiet ct = opt.get();
            SanPhamChiTiet spct = ct.getSanPhamChiTiet();
            int soLuongHienTai = ct.getSoLuong();
            int soLuongKho = spct.getSoLuong() + soLuongHienTai;

            if (soLuongMoi <= 0 || soLuongMoi > soLuongKho) {
                model.addAttribute("loiCapNhatSoLuong", "Số lượng vượt quá tồn kho!");
                return "redirect:/ban-hang/chon-hoa-don/" + ct.getHoaDon().getId();
            }

            // Trả lại số cũ vào kho, trừ số mới
            spct.setSoLuong(soLuongKho - soLuongMoi);
            ct.setSoLuong(soLuongMoi);
            spctRepo.save(spct);
            hdctRepo.save(ct);

            banHangService.capNhatTongTienHoaDon(ct.getHoaDon().getId());
            return "redirect:/ban-hang/chon-hoa-don/" + ct.getHoaDon().getId();
        }

        return "redirect:/ban-hang/view";
    }

}
