package com.example.upgradeservice.dto.technician;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;


@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetTechnicianDto {

    String firstName;
    String lastName;
    String email;
    double point;
}
