package org.example.datn.Controller;

import org.example.datn.Entity.ChatLieu;
import org.example.datn.Service.ChatLieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/thuoc-tinh/chat-lieu")
public class ChatLieuController {

    @Autowired
    private ChatLieuService chatLieuService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("listChatLieu", chatLieuService.getAllChatLieu());
        return "thuoc_tinh/ChatLieu";
    }

    @GetMapping("/get/{id}")
    @ResponseBody
    public ResponseEntity<ChatLieu> getById(@PathVariable Integer id) {
        ChatLieu cl = chatLieuService.getById(id);
        return (cl != null) ? ResponseEntity.ok(cl) : ResponseEntity.notFound().build();
    }

    @PostMapping("/save")
    @ResponseBody
    public ResponseEntity<String> save(@RequestBody ChatLieu chatLieu) {
        if (chatLieu.getMa() == null || chatLieu.getTen() == null) {
            return ResponseEntity.badRequest().body("Thiếu mã hoặc tên!");
        }

        chatLieuService.saveOrUpdate(chatLieu);
        return ResponseEntity.ok(chatLieu.getId() == null ? "Thêm mới thành công" : "Cập nhật thành công");
    }
}
