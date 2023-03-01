package com.example.upgradeservice.model;

import com.example.upgradeservice.model.users.Technician;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import jakarta.persistence.*;


@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Entity
public class PhotoTec {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    byte[] image;
    String imagePath;
    @OneToOne
    Technician technician;
}
