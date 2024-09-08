package com.example.PhongTroOnline.repository;

import com.example.PhongTroOnline.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Integer> {
    boolean existsBySlug(String slug);
}
