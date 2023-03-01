package com.example.upgradeservice.dto.technician;

import com.example.upgradeservice.model.users.TecStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TechnicianDto {

    @NotBlank(message = "khalie")
    String firstName;

    @NotBlank(message = "khalie")
    String lastName;

    @Email
    String email;

    @Pattern(regexp ="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$" , message = "format pass ghalat ast")
    String pass;

    TecStatus tecStatus;
}
