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
public class CleaningRequest {
    @Id
    @GeneratedValue
    private Long id;
    private int roomNumber;
    private LocalDateTime createTime;
    private LocalDateTime deadline;
    private CleaningComplexity cleaningComplexity;
    private String note;
    private boolean handled;
}
