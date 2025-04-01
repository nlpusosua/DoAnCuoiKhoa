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
    @Query("SELECT r FROM Room r " +
            "JOIN r.district d " +  // Nối với District
            "JOIN d.provinces p " + // Nối tiếp với Province
            "WHERE LOWER(r.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(d.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR CAST(r.price AS string) LIKE CONCAT('%', :keyword, '%')")
    Page<Room> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
