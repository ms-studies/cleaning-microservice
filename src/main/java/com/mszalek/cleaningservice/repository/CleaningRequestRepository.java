package com.mszalek.cleaningservice.repository;

import com.mszalek.cleaningservice.model.CleaningRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CleaningRequestRepository extends JpaRepository<CleaningRequest, Long> {
    List<CleaningRequest> findCleaningRequestsByHandled(boolean handled);
    List<CleaningRequest> findCleaningRequestsByRoomNumber(int roomNumber);
}
