package com.example.upgradeservice.dto.ordered;
import com.example.upgradeservice.model.order.OrderedStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderedDto {

    @NotNull(message = "khalie")
    long price;

    String text;

    @NotNull(message = "khalie")
    long date;

    String address;

    @Enumerated(EnumType.STRING)
    OrderedStatus orderedStatus;
}
