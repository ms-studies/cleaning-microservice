package com.mszalek.cleaningservice;

import com.mszalek.cleaningservice.model.CleaningComplexity;
import com.mszalek.cleaningservice.model.CleaningLog;
import com.mszalek.cleaningservice.model.CleaningReport;
import com.mszalek.cleaningservice.model.CleaningRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedList;

@RestController
public class CleaningRestController {

    @Autowired
    CleaningService cleaningService;

    @GetMapping("cleaning/todo")
    Collection<CleaningRequest> todoCleaning(@RequestParam(name = "date", required = false) LocalDate date,
                                             @RequestParam(name = "complexity", required = false) CleaningComplexity complexity) {
        //ask reservation service for list of reserved rooms
        //check in own repo if any of that rooms was cleaned already
        //check for special requests
        //return list of rooms to clean
        return cleaningService.getAllCurrentRequests();
    }

    @PostMapping("cleaning/request")
    CleaningRequest requestCleaning(@RequestBody CleaningRequest request) {
        return cleaningService.addCleaningRequest(request);
    }

    @PutMapping("cleaning/request")
    CleaningRequest updateRequest(@RequestBody CleaningRequest request) {
        return cleaningService.updateCleaningRequest(request);
    }

    @DeleteMapping("cleaning/request")
    boolean cancelRequest(@RequestParam(name = "requestId") Long id) {
        return cleaningService.cancelCleaningRequest(id);
    }

    @PostMapping("cleaning/confirm")
    CleaningLog confirmCleaning(@RequestBody CleaningLog log) {
        return cleaningService.confirmCleaning(log);
    }

    @GetMapping("cleaning/report")
    CleaningReport getReport() {
        return cleaningService.createReport(LocalDate.now());
    }

}
