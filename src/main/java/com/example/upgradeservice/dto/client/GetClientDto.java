package com.example.upgradeservice.dto.client;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetClientDto {
    String firstName;
    String lastName;
    String email;
    long money;
}
