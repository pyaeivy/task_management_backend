package org.example.teskmanagementbackend.dto;

import lombok.ToString;
import org.example.teskmanagementbackend.entity.Period;

import java.time.LocalDate;

@ToString
public class TaskDto {

    public record TaskRequest(
            String taskName,
            Period period,
            LocalDate startDate,
            LocalDate endDate,
            boolean isCompleted
    ){}

    public record TaskResponse(
            Long id,
            String taskName,
            Period period,
            LocalDate startDate,
            LocalDate endDate,
            boolean isCompleted
    ){}
}
