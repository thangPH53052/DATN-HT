package org.example.datn.Service;

import org.example.datn.Entity.HinhAnhSanPham;
import org.example.datn.Entity.SanPham;
import org.example.datn.Repository.*;
import org.example.datn.dto.SanPhamDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SanPhamService {

    @Autowired
    private SanPhamRepository sanPhamRepository;
    @Autowired
    private DanhMucRepository danhMucRepository;
    @Autowired
    private ChatLieuRepository chatLieuRepository;
    @Autowired
    private LoaiKhoaRepository loaiKhoaRepository;
    @Autowired
    private KieuDayRepository kieuDayRepository;
    @Autowired
    private ThuongHieuRepository thuongHieuRepository;
    @Autowired
    private HinhAnhSanPhamRepository hinhAnhSanPhamRepository;

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/images/";
    private Map<Integer, SanPham> sanPhamMap = new HashMap<>();

    public List<SanPham> getAllSanPham() {
        return sanPhamRepository.findAll();
    }

    public Page<SanPhamDTO> getAllSanPham(int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return sanPhamRepository.findAll(pageable).map(this::mapToDTO);
    }

    public SanPhamDTO getSanPhamDTOById(Integer id) {
        return sanPhamRepository.findById(id).map(this::mapToDTO).orElse(null);
    }

    public Integer addSanPham(String ma, String ten, Integer idDanhMuc, Integer idChatLieu,
            Integer idLoaiKhoa, Integer idKieuDay, Integer idThuongHieu,
            String moTa, Float canNang, Float dungTich,
            String kichThuoc, Boolean trangThai, MultipartFile[] hinhAnhs) {

        SanPham sp = new SanPham();
        sp.setMa(ma);
        sp.setTen(ten);
        sp.setMoTa(moTa);
        sp.setCanNang(canNang);
        sp.setDungTich(dungTich);
        sp.setKichThuoc(kichThuoc);
        sp.setTrangThai(trangThai);

        sp.setDanhMuc(danhMucRepository.findById(idDanhMuc).orElse(null));
        sp.setChatLieu(chatLieuRepository.findById(idChatLieu).orElse(null));
        sp.setLoaiKhoa(loaiKhoaRepository.findById(idLoaiKhoa).orElse(null));
        sp.setKieuDay(kieuDayRepository.findById(idKieuDay).orElse(null));
        sp.setThuongHieu(thuongHieuRepository.findById(idThuongHieu).orElse(null));

        sanPhamRepository.save(sp);
        saveImagesForProduct(sp, hinhAnhs);
        return sp.getId();
    }

    public void updateSanPham(Integer id, String ma, String ten, Integer idDanhMuc, Integer idChatLieu,
            Integer idLoaiKhoa, Integer idKieuDay, Integer idThuongHieu,
            String moTa, Float canNang, Float dungTich,
            String kichThuoc, Boolean trangThai, MultipartFile[] hinhAnhs) {

        SanPham sp = sanPhamRepository.findByIdWithImages(id);
        if (sp == null)
            return;

        sp.setMa(ma);
        sp.setTen(ten);
        sp.setMoTa(moTa);
        sp.setCanNang(canNang);
        sp.setDungTich(dungTich);
        sp.setKichThuoc(kichThuoc);
        sp.setTrangThai(trangThai);

        sp.setDanhMuc(danhMucRepository.findById(idDanhMuc).orElse(null));
        sp.setChatLieu(chatLieuRepository.findById(idChatLieu).orElse(null));
        sp.setLoaiKhoa(loaiKhoaRepository.findById(idLoaiKhoa).orElse(null));
        sp.setKieuDay(kieuDayRepository.findById(idKieuDay).orElse(null));
        sp.setThuongHieu(thuongHieuRepository.findById(idThuongHieu).orElse(null));

        if (hinhAnhs != null && hinhAnhs.length > 0 && !hinhAnhs[0].isEmpty()) {
            List<HinhAnhSanPham> oldImages = sp.getHinhAnhList();
            for (HinhAnhSanPham old : oldImages) {
                File file = new File(UPLOAD_DIR + old.getUrl());
                if (file.exists()) {
                    boolean deleted = file.delete();
                    System.out.println("🗑 Xóa file: " + file.getPath() + " → " + deleted);
                }
            }
            sp.getHinhAnhList().clear();
            sanPhamRepository.save(sp);
            saveImagesForProduct(sp, hinhAnhs);
        } else {
            sanPhamRepository.save(sp);
        }
    }

    private void saveImagesForProduct(SanPham sp, MultipartFile[] hinhAnhs) {
        if (hinhAnhs == null)
            return;

        for (MultipartFile file : hinhAnhs) {
            if (!file.isEmpty()) {
                String fileName = saveFile(file);
                HinhAnhSanPham img = new HinhAnhSanPham();
                img.setSanPham(sp);
                img.setUrl(fileName);
                hinhAnhSanPhamRepository.save(img);
            }
        }
    }

    private String saveFile(MultipartFile file) {
        File dir = new File(UPLOAD_DIR);
        if (!dir.exists())
            dir.mkdirs();

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        File dest = new File(UPLOAD_DIR + fileName);

        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileName;
    }

    private SanPhamDTO mapToDTO(SanPham sp) {
        SanPhamDTO dto = new SanPhamDTO();
        dto.setId(sp.getId());
        dto.setMa(sp.getMa());
        dto.setTen(sp.getTen());
        dto.setMoTa(sp.getMoTa());
        dto.setCanNang(sp.getCanNang());
        dto.setDungTich(sp.getDungTich());
        dto.setKichThuoc(sp.getKichThuoc());
        dto.setTrangThai(sp.getTrangThai());

        dto.setDanhMucId(sp.getDanhMuc() != null ? sp.getDanhMuc().getId() : null);
        dto.setChatLieuId(sp.getChatLieu() != null ? sp.getChatLieu().getId() : null);
        dto.setLoaiKhoaId(sp.getLoaiKhoa() != null ? sp.getLoaiKhoa().getId() : null);
        dto.setKieuDayId(sp.getKieuDay() != null ? sp.getKieuDay().getId() : null);
        dto.setThuongHieuId(sp.getThuongHieu() != null ? sp.getThuongHieu().getId() : null);

        dto.setTenDanhMuc(sp.getDanhMuc() != null ? sp.getDanhMuc().getTen() : null);
        dto.setTenChatLieu(sp.getChatLieu() != null ? sp.getChatLieu().getTen() : null);
        dto.setTenLoaiKhoa(sp.getLoaiKhoa() != null ? sp.getLoaiKhoa().getTen() : null);
        dto.setTenKieuDay(sp.getKieuDay() != null ? sp.getKieuDay().getTen() : null);
        dto.setTenThuongHieu(sp.getThuongHieu() != null ? sp.getThuongHieu().getTen() : null);

        if (sp.getHinhAnhList() != null) {
            dto.setHinhAnhUrls(sp.getHinhAnhList().stream()
                    .map(HinhAnhSanPham::getUrl)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    public Page<SanPhamDTO> searchSanPham(String keyword, Integer idDanhMuc, Integer idThuongHieu,
            Integer idChatLieu, Integer idLoaiKhoa, Integer idKieuDay, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return sanPhamRepository
                .searchSanPhamByFilters(keyword, idDanhMuc, idThuongHieu, idChatLieu, idLoaiKhoa, idKieuDay, pageable)
                .map(this::mapToDTO);
    }

    public boolean isMaTrungKhiUpdate(Integer id, String maMoi) {
        for (SanPham sp : sanPhamMap.values()) {
            if (sp.getMa().equals(maMoi) && !sp.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public boolean existsByMa(String ma) {
        return sanPhamMap.values().stream()
                .anyMatch(sp -> sp.getMa().equalsIgnoreCase(ma));
    }



}