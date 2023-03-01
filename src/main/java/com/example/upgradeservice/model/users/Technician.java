package com.example.upgradeservice.model.users;

import com.example.upgradeservice.model.PhotoTec;
import com.example.upgradeservice.model.Users;
import com.example.upgradeservice.model.services.UnderService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Entity
public class Technician extends Users {

    ArrayList<String> reviews;

    double point;

    @Enumerated(EnumType.STRING)
    TecStatus tecStatus;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    Set<UnderService> underServices = new HashSet<>();

    @OneToOne
    PhotoTec photoTec;
}
