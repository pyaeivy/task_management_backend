package org.example.teskmanagementbackend.controller;

import org.example.teskmanagementbackend.dto.BoardDto.BoardRequest;
import org.example.teskmanagementbackend.dto.BoardDto.BoardResponse;
import org.example.teskmanagementbackend.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/save")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BoardResponse> saveBoard(
            @RequestBody BoardRequest req,
            @RequestParam(required = false) Long id) {
        BoardResponse savedBoard = boardService.saveBoard(req, id);
        return ResponseEntity.status(HttpStatus.OK).body(savedBoard);
    }

    @GetMapping("/all")
    public ResponseEntity<List<BoardResponse>> listBoards() {
        return ResponseEntity.ok(boardService.listBoard());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardResponse> getBoardById(@PathVariable @NonNull Long id) {
        return ResponseEntity.ok(boardService.getBoardById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<BoardResponse>> searchBoards(
            @RequestParam(required = false) String ownerName,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String taskName,
            @RequestParam(required = false) String type) {
        return ResponseEntity.ok(boardService.searchBoards(ownerName, title, taskName, type));
    }
}
