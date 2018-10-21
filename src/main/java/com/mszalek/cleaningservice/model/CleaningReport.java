package com.mszalek.cleaningservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CleaningReport {
    LocalDate date;
    Collection<CleaningLog> cleaningsDone;
}
