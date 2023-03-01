package com.example.upgradeservice.dto.ordered;


import com.example.upgradeservice.model.order.OrderedStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetOrderedDto {

    long price;
    String text;
    long date;
    long time;
    long startedTime;
    long finishTime;
    String address;
    @Enumerated(EnumType.STRING)
    OrderedStatus orderedStatus;
}
