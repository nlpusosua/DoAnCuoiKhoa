package com.example.PhongTroOnline.entity;

import com.example.PhongTroOnline.model.enums.Gender;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

    String poster;

    String trailer;

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
    Province province;

    @ManyToOne
    @JoinColumn(name = "district_id", nullable = false)
    District district;

    @ManyToOne
    @JoinColumn(name = "ward_id", nullable = false)
    Ward ward;

    // Thêm danh sách các ảnh và video liên kết
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ImageRoom> imageRooms = new ArrayList<>();

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    List<VideoRoom> videoRooms = new ArrayList<>();
}

