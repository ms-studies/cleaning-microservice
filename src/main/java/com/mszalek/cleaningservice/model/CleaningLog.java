package com.mszalek.cleaningservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CleaningLog {
    @Id
    @GeneratedValue
    private Long id;
    private int roomNumber;
    private LocalDateTime timeOfFinish;
    private CleaningComplexity cleaningComplexity;

    public CleaningLog(int roomNumber, LocalDateTime cleanTime) {
        this.roomNumber = roomNumber;
        this.timeOfFinish = cleanTime;
    }
}
