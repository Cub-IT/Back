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
<<<<<<< HEAD
    @NotEmpty
    private String name;
    @NotEmpty
=======
    private String firstName;
    private String lastName;
>>>>>>> 8eb83d7c838b626df20cab90fcd5d88d9b085c4c
    private String email;
    @NotEmpty
    private String password;
}
