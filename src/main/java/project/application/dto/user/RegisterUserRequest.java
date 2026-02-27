package project.application.dto.user;

import jakarta.validation.constraints.NotEmpty;

public record RegisterUserRequest(
        @NotEmpty(message = "Nome é obrigatorio")
        String name,

        @NotEmpty(message = "senha é obrigatorio é obrigatorio")
        String password
) {}
