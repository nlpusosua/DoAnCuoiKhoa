package com.example.PhongTroOnline.repository;

import com.example.PhongTroOnline.entity.ImageRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRoomRepository extends JpaRepository<ImageRoom, Long> {
     List<ImageRoom> findByRoomId(Integer roomId) ;


}
