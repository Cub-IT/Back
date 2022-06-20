package com.cub.project.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private long id;
    private LocalDate creationDate;
    private LocalDate editDate;
    private String description;
}
