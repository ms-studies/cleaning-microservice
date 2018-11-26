package com.mszalek.cleaningservice;

import com.mszalek.cleaningservice.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
public class CleaningRestController {

    @Autowired
    CleaningService cleaningService;

    @Autowired
    ReservationsService reservationsService;

    @GetMapping("cleaning/todo")
    Collection<CleaningRequest> todoCleaning() {
        //ask reservation service for list of reserved rooms
        //check in own repo if any of that rooms was cleaned already
        //check for special requests
        //return list of rooms to clean
        List<Reservation> reservations = reservationsService.getReservations();

        Collection<CleaningRequest> requests = cleaningService.getAllCurrentRequests(reservations);
        return requests;
    }

    @PostMapping("cleaning/request")
    ResponseEntity requestCleaning(@RequestBody CleaningRequest request) {
        try {
            CleaningRequest result = cleaningService.addCleaningRequest(request);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("something failed", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("cleaning/request")
    ResponseEntity updateRequest(@RequestBody CleaningRequest request) {
        try {
            CleaningRequest result = cleaningService.updateCleaningRequest(request);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("something failed", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("cleaning/request")
    ResponseEntity cancelRequest(@RequestParam(name = "requestId") Long id) {
        try {
            cleaningService.cancelCleaningRequest(id);
            return new ResponseEntity<>("ok", HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>("no id", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("cleaning/confirm")
    ResponseEntity confirmCleaning(@RequestBody CleaningLog log) {
        try {
            cleaningService.confirmCleaning(log);
            return new ResponseEntity<>("ok", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("invalid cleaning log", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("cleaning/report")
    CleaningReport getReport() {
        return cleaningService.createReport(LocalDate.now());
    }

}
