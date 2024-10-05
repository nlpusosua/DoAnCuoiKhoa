package com.example.PhongTroOnline.entity;

import com.example.PhongTroOnline.model.enums.PriceType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "serviceprices")
public class ServicePrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    PriceType type;
    @Column(nullable = false)
    Integer price;
    Integer discountPrice;
    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    Service service;
    @Column(nullable = false)
    Integer minDays = 3;
    @Column(nullable = false)
    Boolean isFlexible = false;
}
