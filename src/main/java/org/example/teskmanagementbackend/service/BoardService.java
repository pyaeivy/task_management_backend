package org.example.teskmanagementbackend.service;

import java.util.List;

import org.example.teskmanagementbackend.dao.BoardDao;
import org.example.teskmanagementbackend.dao.UserDao;
import org.example.teskmanagementbackend.dto.BoardDto.BoardRequest;
import org.example.teskmanagementbackend.dto.BoardDto.BoardResponse;
import org.example.teskmanagementbackend.dto.TaskDto.TaskResponse;
import org.example.teskmanagementbackend.entity.Board;
import org.example.teskmanagementbackend.entity.Task;
import org.example.teskmanagementbackend.entity.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Collections;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardService {

	private final BoardDao boardDao;
	private final UserDao userDao;

	public List<BoardResponse> listBoard() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null || !auth.isAuthenticated()) {
			throw new RuntimeException("User is not authenticated!");
		}
		String username = auth.getName();
		return boardDao.listBoardByUsername(username).stream().map(this::mapToBoardResponse).toList();
	}

	public List<BoardResponse> searchBoards(String ownerName, String title, String taskName, String type) {
		Specification<Board> spec = (root, query, cb) -> {
			query.distinct(true);
			List<Predicate> predicates = new ArrayList<>();

			if (ownerName != null && !ownerName.isBlank()) {
				Join<Board, User> ownerJoin = root.join("owner", JoinType.LEFT);
				predicates.add(cb.equal(ownerJoin.get("username"), ownerName));
			}

			if (title != null && !title.isBlank()) {
				predicates.add(cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
			}

			if (type != null && !type.isBlank()) {
				try {
					Board.Type boardType = Board.Type.valueOf(type.toUpperCase());
					predicates.add(cb.equal(root.get("type"), boardType));
				} catch (IllegalArgumentException e) {
					// Invalid type, ignore this filter
				}
			}

			if (taskName != null && !taskName.isBlank()) {
				Join<Board, Task> taskJoin = root.join("lists", JoinType.LEFT);
				predicates.add(cb.like(cb.lower(taskJoin.get("taskName")), "%" + taskName.toLowerCase() + "%"));
			}

			return cb.and(predicates.toArray(new Predicate[0]));
		};

		return boardDao.findAll(spec).stream().map(this::mapToBoardResponse).toList();
	}

	private BoardResponse mapToBoardResponse(Board board) {
		String ownerUsername = board.getUser() != null ? board.getUser().getUsername() : null;

		List<TaskResponse> taskList = board.getLists() != null
				? board.getLists().stream().map(t -> new TaskResponse(t.getId(), t.getTaskName(), t.getPeriod(),
						t.getStartDate(), t.getEndDate(), t.isCompleted())).toList()
				: Collections.emptyList();

		return new BoardResponse(board.getId(), board.getBoardTitle(),
				board.getType() != null ? board.getType().name() : null, ownerUsername, taskList);
	}

	public BoardResponse saveBoard(BoardRequest request, Long id) {
		Board board;
		if (id != null && boardDao.existsById(id)) {
			board = boardDao.findById(id).orElseThrow();
		} else {
			board = new Board();
		}

		if (request.boardTitle() != null) {
			board.setBoardTitle(request.boardTitle());
		}
		if (request.type() != null) {
			board.setType(Board.Type.valueOf(request.type().toUpperCase()));
		}

		// Resolve the currently logged-in user from the JWT-backed SecurityContext
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User owner = userDao.findByUsername(username).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authenticated user not found"));
		board.setUser(owner);

		@SuppressWarnings("null")
		Board savedBoard = boardDao.save(board);

		return mapToBoardResponse(savedBoard);
	}

}
