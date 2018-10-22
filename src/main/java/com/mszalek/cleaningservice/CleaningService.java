package com.mszalek.cleaningservice;

import com.mszalek.cleaningservice.model.CleaningComplexity;
import com.mszalek.cleaningservice.model.CleaningLog;
import com.mszalek.cleaningservice.model.CleaningReport;
import com.mszalek.cleaningservice.model.CleaningRequest;
import com.mszalek.cleaningservice.repository.CleaningLogRepository;
import com.mszalek.cleaningservice.repository.CleaningRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
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
            reqFromDb.setDeadline(request.getDeadline());
            reqFromDb.setNote(request.getNote());
            reqFromDb.setRoomNumber(request.getRoomNumber());
            return requestRepository.save(reqFromDb);
        } else {
            //TODO: Throw Exception?
            return null;
        }
    }

    public boolean cancelCleaningRequest(Long id) {
        requestRepository.deleteById(id);
        return true;
    }

    public Collection<CleaningRequest> getAllCurrentRequests() {
        return requestRepository.findAll();
    }

    public CleaningLog confirmCleaning(CleaningLog cleaningLog) {
        cleaningLog.setId(null);
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
