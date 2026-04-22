package org.example.teskmanagementbackend.dto;

import java.util.List;

import org.example.teskmanagementbackend.dto.TaskDto.TaskResponse;

import lombok.ToString;

@ToString
public class BoardDto {

        public record BoardRequest(
                        String boardTitle,
                        String type,
                        String owner) {
        }

        public record BoardResponse(
                        Long id,
                        String boardTitle,
                        String type,
                        String owner,
                        List<TaskResponse> taskList) {
        }
}
