package com.example.PhongTroOnline.repository;

import com.example.PhongTroOnline.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface RoomRepository extends JpaRepository<Room, Integer> {
    boolean existsBySlug(String slug);
//    Page<Room> findByTitle(String title, Pageable pageable);
    List<Room> findAllByTitleAndIdAndStreetDetail(String title, Integer id, String streetDetail);


}
