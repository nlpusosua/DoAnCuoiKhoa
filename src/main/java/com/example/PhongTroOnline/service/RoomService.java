package com.example.PhongTroOnline.service;

import com.example.PhongTroOnline.entity.Room;
import com.example.PhongTroOnline.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    public List<Room> getRoom(String tilte, Integer id, String streetDetail  ){
        return roomRepository.findAllByTitleAndIdAndStreetDetail(tilte, id, streetDetail);
    }
}
