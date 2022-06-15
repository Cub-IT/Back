package com.cub.project.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
}
