package com.cub.project.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class LoginDto {
    @NotEmpty
    private String login;
    @NotEmpty
    private String password;
}
