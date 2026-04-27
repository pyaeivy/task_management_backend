package org.example.teskmanagementbackend.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.example.teskmanagementbackend.dao.BoardDao;
import org.example.teskmanagementbackend.dao.TaskDao;
import org.example.teskmanagementbackend.dao.UserDao;
import org.example.teskmanagementbackend.dto.TaskDto.TaskRequest;
import org.example.teskmanagementbackend.dto.TaskDto.TaskResponse;
import org.example.teskmanagementbackend.entity.Board;
import org.example.teskmanagementbackend.entity.Period;
import org.example.teskmanagementbackend.entity.Task;
import org.example.teskmanagementbackend.entity.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TaskService {

	private final TaskDao taskDao;
	private final BoardDao boardDao;
	private final UserDao userDao;
	
	public List<TaskResponse> listTask(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null && !auth.isAuthenticated()) {
			throw new RuntimeException("User is not authenticated!!!");
		}
		String username = auth.getName();
		return taskDao.listTaskByUsername(username).stream().map(this::mapToResponse).toList();
	}

	public TaskResponse createTask(TaskRequest request) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		User owner = userDao.findByUsername(username).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authenticated user not found"));

		@SuppressWarnings("null")
		Board board = boardDao.findById(request.boardId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not found"));

		Task task = new Task();
		task.setTaskName(request.taskName());
		task.setPeriod(request.period());
		task.setStartDate(LocalDate.now());
		task.setEndDate(endDate());
		task.setCompleted(false);
		task.setUser(owner);
		task.setBoard(board);

		return mapToResponse(taskDao.save(task));
	}

	public TaskResponse submitTask(@NonNull Long taskId) {
		Task task = taskDao.findById(taskId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));

		task.setCompleted(true);
		task.setEndDate(LocalDate.now()); // mark the completion date

		return mapToResponse(taskDao.save(task));
	}

	public List<TaskResponse> searchTasks(String taskName, String period, Long boardId, Boolean isCompleted) {
		Specification<Task> spec = (root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();

			if (taskName != null && !taskName.isBlank()) {
				predicates.add(cb.like(cb.lower(root.get("taskName")), "%" + taskName.toLowerCase() + "%"));
			}

			if (period != null && !period.isBlank()) {
				try {
					Period p = Period.valueOf(period);
					predicates.add(cb.equal(root.get("period"), p));
				} catch (IllegalArgumentException e) {
					// invalid period value — ignore filter
				}
			}

			if (boardId != null) {
				predicates.add(cb.equal(root.get("board").get("id"), boardId));
			}

			if (isCompleted != null) {
				predicates.add(cb.equal(root.get("isCompleted"), isCompleted));
			}

			return cb.and(predicates.toArray(new Predicate[0]));
		};

		return taskDao.findAll(spec).stream().map(this::mapToResponse).toList();
	}

	private TaskResponse mapToResponse(Task task) {
		String ownerUsername = task.getUser() != null ? task.getUser().getUsername() : null;
		Long boardId = task.getBoard() != null ? task.getBoard().getId() : null;

		return new TaskResponse(task.getId(), task.getTaskName(), task.getPeriod(), task.getStartDate(),
				task.getEndDate(), task.isCompleted(), ownerUsername, boardId);
	}
	
	public LocalDate endDate () {
		return LocalDate.of(1900, 1, 1);
	}
}
