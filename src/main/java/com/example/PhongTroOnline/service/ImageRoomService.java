package com.example.PhongTroOnline.service;

import com.example.PhongTroOnline.entity.ImageRoom;
import com.example.PhongTroOnline.repository.ImageRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class ImageRoomService {
    private final ImageRoomRepository imageRoomRepository;

    // Đảm bảo phương thức này là public
    public List<ImageRoom> findByRoomId(Integer roomId) {
        return imageRoomRepository.findByRoomId(roomId);
    }
}

