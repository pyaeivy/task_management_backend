package org.example.teskmanagementbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean isCompleted;
    private String description;
    private String comments;
    

    @ManyToOne
    private User user;
    @ManyToOne
    private Board board;
    @OneToMany
    private List<CheckList> checkList = new ArrayList<>();
}
