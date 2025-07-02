package com.example.PhongTroOnline.repository;

import com.example.PhongTroOnline.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface RoomRepository extends JpaRepository<Room, Integer> {
    boolean existsBySlug(String slug);
//    Page<Room> findByTitle(String title, Pageable pageable);
    Page<Room> findAllByStatus(Boolean status, Pageable pageable);

    Optional<Room> findByIdAndSlugAndStatus(Integer id, String slug, Boolean status);
    @Query("SELECT DISTINCT r FROM Room r " +
            "LEFT JOIN FETCH r.district " +
            "LEFT JOIN FETCH r.province " +
            "WHERE LOWER(r.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR CAST(r.price AS string) LIKE CONCAT('%', :keyword, '%') " +
            "OR LOWER(r.district.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(r.province.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Room> search(@Param("keyword") String keyword, Pageable pageable);
}
