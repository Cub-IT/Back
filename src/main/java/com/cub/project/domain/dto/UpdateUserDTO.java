package com.cub.project.domain.dto;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class UpdateUserDTO {
    private long id;

    @NotBlank(message = "{validation.user.name.not_blank}")
    @Size(min = 2, max = 50, message = "{validation.user.name.size}")
    private String name;
    private String password;
}
