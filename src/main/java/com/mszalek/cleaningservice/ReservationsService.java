package com.mszalek.cleaningservice;

import com.mszalek.cleaningservice.model.CleaningComplexity;
import com.mszalek.cleaningservice.model.CleaningRequest;
import com.mszalek.cleaningservice.model.Reservation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationsService {

    private LocalDateTime stringToDate(String string) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(string, formatter);
    }

    public List<Reservation> getReservations() {
        final String uri = "http://127.0.0.1:8000/booking/api/getBookings";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Reservation[]> responseEntity = restTemplate.getForEntity(uri, Reservation[].class);
        Reservation[] objects = responseEntity.getBody();
        return Arrays.asList(objects);

    }
}
