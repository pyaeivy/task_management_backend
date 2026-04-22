package org.example.teskmanagementbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class RoleCategoryDto {
    private Long id;
    private String code;
    private String description;

    public RoleCategoryDto(String code, String description) {
        this.code = code;
        this.description = description;

    }
}
