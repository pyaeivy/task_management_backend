package org.example.teskmanagementbackend.controller;

import java.util.List;

import org.example.teskmanagementbackend.dto.TaskDto.TaskRequest;
import org.example.teskmanagementbackend.dto.TaskDto.TaskResponse;
import org.example.teskmanagementbackend.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(request));
    }

    @PatchMapping("/{id}/submit")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<TaskResponse> submitTask(@PathVariable @NonNull Long id) {
        return ResponseEntity.ok(taskService.submitTask(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<TaskResponse>> searchTasks(
            @RequestParam(required = false) String taskName,
            @RequestParam(required = false) String period,
            @RequestParam(required = false) Long boardId,
            @RequestParam(required = false) Boolean isCompleted) {
        return ResponseEntity.ok(taskService.searchTasks(taskName, period, boardId, isCompleted));
    }
    
    @GetMapping("/list")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<TaskResponse>> listTask(){
    	return ResponseEntity.ok(taskService.listTask());
    }
}
