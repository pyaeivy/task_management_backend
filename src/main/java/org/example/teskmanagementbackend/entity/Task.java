package org.example.teskmanagementbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String taskName;
    @Enumerated(EnumType.STRING)
    private Period period;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isCompleted;

    @ManyToOne
    private User user;
    @ManyToOne
    private Board board;
}
