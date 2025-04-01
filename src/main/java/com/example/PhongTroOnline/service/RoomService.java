package com.example.PhongTroOnline.service;

import com.example.PhongTroOnline.entity.Room;
import com.example.PhongTroOnline.repository.FavoriteRepository;
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
    private final FavoriteRepository favoriteRepository;
    public Page<Room> getRoom(Boolean status,Integer page, Integer pageSize  ){
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
        return roomRepository.findAllByStatus(status, pageRequest);
    }
    public Room getRoom(Integer id, String slug, Boolean status) {
        return roomRepository.findByIdAndSlugAndStatus(id, slug, status).orElse(null);
    }
    public Page<Room> getRoomsWithMostFavorites(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);  // Sử dụng Pageable đúng cách
        Page<Object[]> resultPage = (Page<Object[]>) favoriteRepository.findRoomsWithMostFavorites(pageable);

        // Chuyển đổi kết quả Object[] sang danh sách Room
        return resultPage.map(result -> (Room) result[0]);
    }
    public Page<Room> searchByKeyword(String keyword, Pageable pageable) {
        return roomRepository.searchByKeyword(keyword, pageable);
    }

}
