package com.mszalek.cleaningservice;


import com.mszalek.cleaningservice.model.Reservation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ReservationsService {
    Logger log = LoggerFactory.getLogger(ReservationsService.class);


    public List<Reservation> getReservations() {
        log.info("[Cleaning]: Requesting reservations from booking service....");
        final String uri = "http://host.docker.internal:8000/booking/api/getBookings";
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<Reservation[]> responseEntity = restTemplate.getForEntity(uri, Reservation[].class);
            Reservation[] objects = responseEntity.getBody();
            return Arrays.asList(objects);
        } catch (Exception e) {
            log.error("[Cleaning]: Couldn't get reservations for booking service");
            return new ArrayList<>();
        }


    }
}
