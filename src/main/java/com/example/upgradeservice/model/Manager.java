package com.example.upgradeservice.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import jakarta.persistence.*;


@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Entity
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
}
