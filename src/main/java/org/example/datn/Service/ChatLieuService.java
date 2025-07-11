package org.example.datn.Service;

import org.example.datn.Entity.ChatLieu;
import org.example.datn.Repository.ChatLieuRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatLieuService {

    private final ChatLieuRepository chatLieuRepository;

    public ChatLieuService(ChatLieuRepository chatLieuRepository) {
        this.chatLieuRepository = chatLieuRepository;
    }

    // Lấy tất cả chất liệu
    public List<ChatLieu> getAllChatLieu() {
        return chatLieuRepository.findAll();
    }

    // Lấy chất liệu theo ID (dùng cho cập nhật)
    public ChatLieu getById(Integer id) {
        return chatLieuRepository.findById(id).orElse(null);
    }

    // Thêm mới hoặc cập nhật
    public void saveOrUpdate(ChatLieu chatLieu) {
        chatLieuRepository.save(chatLieu);
    }
}
