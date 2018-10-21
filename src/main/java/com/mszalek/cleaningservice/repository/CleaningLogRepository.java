package com.mszalek.cleaningservice.repository;

import com.mszalek.cleaningservice.model.CleaningLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CleaningLogRepository extends JpaRepository<CleaningLog, Long> {
}
