package com.mszalek.cleaningservice.model;

import lombok.Data;

@Data
public class Reservation {
    String check_in;
    String check_out;
    int people;
    int room;
}
