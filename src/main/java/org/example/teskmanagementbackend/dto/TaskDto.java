package org.example.teskmanagementbackend.dto;

import lombok.ToString;

import org.example.teskmanagementbackend.entity.Period;
import java.time.LocalDateTime;

@ToString
public class TaskDto {

    public record TaskRequest(
            Long boardId,
            String taskName,
            Period period
    ) {}

    public record TaskResponse(
            Long id,
            String taskName,
            Period period,
            LocalDateTime startDate,
            LocalDateTime endDate,
            boolean isCompleted,
            String ownerUsername,
            Long boardId,
            String description,
            String comments
    ) {}
}

