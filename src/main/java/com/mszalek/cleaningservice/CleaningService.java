package com.mszalek.cleaningservice;

import com.mszalek.cleaningservice.model.*;
import com.mszalek.cleaningservice.repository.CleaningLogRepository;
import com.mszalek.cleaningservice.repository.CleaningRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Component
public class CleaningService {

    @Autowired
    CleaningLogRepository logRepository;
    @Autowired
    CleaningRequestRepository requestRepository;

    public CleaningRequest addCleaningRequest(CleaningRequest request) {
        request.setId(null);
        request.setCreateTime(LocalDateTime.now());
        request.setHandled(false);
        if (request.getCleaningComplexity() == null) {
            request.setCleaningComplexity(CleaningComplexity.SPECIAL);
        }
        return requestRepository.save(request);
    }

    public CleaningRequest updateCleaningRequest(CleaningRequest request) {
        Optional<CleaningRequest> optionalRequest = requestRepository.findById(request.getId());
        if (optionalRequest.isPresent()) {
            CleaningRequest reqFromDb = optionalRequest.get();
            if (request.getDeadline() != null)
                reqFromDb.setDeadline(request.getDeadline());
            if (request.getNote() != null)
                reqFromDb.setNote(request.getNote());
            if (request.getRoomNumber() != 0)
                reqFromDb.setRoomNumber(request.getRoomNumber());
            return requestRepository.save(reqFromDb);
        } else {
            throw new CleaningException("Not valid request");
        }
    }

    public boolean cancelCleaningRequest(Long id) {
        requestRepository.deleteById(id);
        return true;
    }

    public Collection<CleaningRequest> getAllCurrentRequests(List<Reservation> reservations) {
        List<CleaningRequest> specialRequests = requestRepository.findCleaningRequestsByHandled(false);
        List<CleaningRequest> generalRequests = getRequestsOfStartingReservations(reservations);
        List<CleaningRequest> dailyRequests = getDailyReservations(reservations);
        List<CleaningLog> cleaningsDoneToday = logRepository.findAll().stream().filter((log) -> LocalDate.now().compareTo(log.getTimeOfFinish().toLocalDate()) == 0).collect(Collectors.toList());
        List<CleaningRequest> allRequests = new ArrayList<>();
        allRequests.addAll(specialRequests);
        allRequests.addAll(generalRequests);
        allRequests.addAll(dailyRequests);
        return allRequests.stream().filter((request) -> cleaningsDoneToday.stream().noneMatch((cleaningLog -> cleaningLog.getRoomNumber() == request.getRoomNumber()))).collect(Collectors.toList());
    }

    private List<CleaningRequest> getDailyReservations(List<Reservation> reservations) {
        return reservations.stream().filter((reservation -> {
            LocalDateTime startDate = stringToDate(reservation.getCheck_in());
            LocalDateTime endDate = stringToDate(reservation.getCheck_out());
            return LocalDate.now().isAfter(startDate.toLocalDate()) && LocalDate.now().isBefore(endDate.toLocalDate());
        })).map((reservation -> new CleaningRequest(
                -1L,
                reservation.getRoom(),
                LocalDateTime.now(),
                LocalDateTime.now().withHour(18),
                CleaningComplexity.DAILY,
                null,
                false
        ))).collect(Collectors.toList());
    }

    private List<CleaningRequest> getRequestsOfStartingReservations(List<Reservation> reservations) {
        return reservations.stream().filter((reservation -> {
            LocalDateTime date = stringToDate(reservation.getCheck_in());
            return LocalDate.now().compareTo(date.toLocalDate()) == 0;
        })).map((reservation -> new CleaningRequest(
                -1L,
                reservation.getRoom(),
                LocalDateTime.now(),
                stringToDate(reservation.getCheck_in()),
                CleaningComplexity.GENERAL,
                null,
                false
        ))).collect(Collectors.toList());
    }

    private LocalDateTime stringToDate(String string) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(string, formatter);
    }

    public CleaningLog confirmCleaning(CleaningLog cleaningLog) {
        cleaningLog.setId(null);
        if (cleaningLog.getTimeOfFinish() == null) {
            cleaningLog.setTimeOfFinish(LocalDateTime.now());
        }
        requestRepository.findCleaningRequestsByRoomNumber(cleaningLog.getRoomNumber()).forEach(cleaningRequest -> {
            cleaningRequest.setHandled(true);
            requestRepository.save(cleaningRequest);
        });
        return logRepository.save(cleaningLog);
    }

    public CleaningReport createReport(LocalDate date) {
        Collection<CleaningLog> cleaningsDone = logRepository
                .findAll()
                .stream()
                .filter(log -> log.getTimeOfFinish().toLocalDate().isEqual(date))
                .collect(Collectors.toList());
        return new CleaningReport(date, cleaningsDone);
    }
}
