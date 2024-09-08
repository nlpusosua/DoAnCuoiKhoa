package com.example.PhongTroOnline.repository;

import com.example.PhongTroOnline.entity.Service;
import com.example.PhongTroOnline.entity.ServicePrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicePriceRepository extends JpaRepository<ServicePrice, Integer> {
}

