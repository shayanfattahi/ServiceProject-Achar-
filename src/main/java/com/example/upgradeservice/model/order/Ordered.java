package com.example.upgradeservice.model.order;

import com.example.upgradeservice.model.services.UnderService;
import com.example.upgradeservice.model.users.Client;
import com.example.upgradeservice.model.users.Technician;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;


@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Entity
public class Ordered {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    long price;
    String text;
    long date;
    long time;
    long startedTime;
    long finishTime;
    String address;
    @Enumerated(EnumType.STRING)
    OrderedStatus orderedStatus;

    @JsonIgnore
    @ManyToOne
    UnderService underService;

    @JsonIgnore
    @ManyToOne
    Client client;

    @JsonIgnore
    @ManyToOne
    Technician technician;
}
