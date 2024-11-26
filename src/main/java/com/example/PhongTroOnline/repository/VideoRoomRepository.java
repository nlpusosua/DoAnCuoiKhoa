package com.example.PhongTroOnline.repository;

import com.example.PhongTroOnline.entity.News;
import com.example.PhongTroOnline.entity.VideoRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRoomRepository extends JpaRepository<VideoRoom, Long> {
}
