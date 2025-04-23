package com.example.PhongTroOnline.repository;

import com.example.PhongTroOnline.entity.Favorite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
    @Query("SELECT f.room, COUNT(f) as favCount " +
            "FROM Favorite f " +
            "GROUP BY f.room " +
            "ORDER BY favCount DESC")
    Page<Object[]> findRoomsWithMostFavorites(Pageable pageable);
    Optional<Favorite> findByUserIdAndRoomId(int userId, int roomId);

}
