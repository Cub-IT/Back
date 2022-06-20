package com.cub.project.domain.dto;

import com.cub.project.domain.models.Group;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupDto {
    private long id;
    private String title;
    private String description;
    private String code;
    private LocalDate creationDate;

    public static GroupDto convert(Group group) {
        return new GroupDto(group.getId(), group.getTitle(), group.getDescription(), group.getCode(), group.getCreationDate());
    }
}
