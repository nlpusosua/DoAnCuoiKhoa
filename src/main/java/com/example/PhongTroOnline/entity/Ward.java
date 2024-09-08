package com.example.PhongTroOnline.entity;

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
@Table(name = "wards")
public class Ward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district;
}
