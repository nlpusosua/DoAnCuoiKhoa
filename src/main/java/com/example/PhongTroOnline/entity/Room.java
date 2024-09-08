package com.example.PhongTroOnline.entity;

import com.example.PhongTroOnline.model.enums.Gender;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(nullable = false)
    String title;

    @Column(nullable = false, unique = true)
    String slug;

    String description;

    @Enumerated(EnumType.STRING)
    Gender gender;

    Integer price;

    Integer areas;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;

    LocalDateTime expiresAt;

    Boolean status;

    String map;

    String streetDetail;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    Category category;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    Service services;

    @ManyToOne
    @JoinColumn(name = "province_id", nullable = false)
    Province provinces;

    @ManyToOne
    @JoinColumn(name = "district_id", nullable = false)
    District district;

    @ManyToOne
    @JoinColumn(name = "ward_id", nullable = false)
    Ward ward;

}
