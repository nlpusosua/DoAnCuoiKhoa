package com.example.PhongTroOnline.service;

import com.example.PhongTroOnline.entity.Room;
import com.example.PhongTroOnline.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    public List<Room> getRoomByName(String name ){
        return roomRepository.findByTitle(name);
    }
}
