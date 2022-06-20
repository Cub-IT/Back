package com.cub.project.domain.dto;

import com.cub.project.domain.models.Group;
import com.cub.project.domain.models.Participant;
import com.cub.project.domain.models.User;
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
    private String ownerFirstName;
    private String ownerLastName;
    private String color;
    private String ownerColor;

    public static GroupDto convert(Group group) {
        User owner = group.getOwner().getUser();
        return new GroupDto(group.getId(), group.getTitle(), group.getDescription(), group.getCode(), group.getCreationDate(), owner.getFirstName(), owner.getLastName(), group.getColor(), owner.getColor());
    }
}