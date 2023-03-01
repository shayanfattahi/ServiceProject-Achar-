package com.example.upgradeservice.model.services;

import com.example.upgradeservice.model.users.Technician;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Entity
public class UnderService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(unique = true)
    String name;
    Long prices;
    String text;
    @ManyToOne
    Services services;
    @ManyToMany(mappedBy = "underServices")
    Set<Technician> technician = new HashSet<>();
}
