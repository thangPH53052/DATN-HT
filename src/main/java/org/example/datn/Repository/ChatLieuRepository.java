package org.example.datn.Repository;

import org.example.datn.Entity.ChatLieu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatLieuRepository extends JpaRepository<ChatLieu, Integer> {
    
}
