package com.example.PhongTroOnline.entity;

import com.example.PhongTroOnline.model.enums.UserRole;
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
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(nullable = false)
    String name;
    @Column(nullable = false, unique = true)
    String email;
    String avatar;
    @Column(nullable = false)
    String password;
    @Enumerated(EnumType.STRING)
    UserRole role;
    Integer account_balance;
    String phone;
    @Column(nullable = false)
    private String roomType;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
