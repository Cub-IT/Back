package com.cub.project.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class LoginDto {
    @NotNull
    private String login;
    @NotNull
    private String password;
}
