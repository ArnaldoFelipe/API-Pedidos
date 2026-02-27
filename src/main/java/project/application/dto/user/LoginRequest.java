package project.application.dto.user;

import jakarta.validation.constraints.NotEmpty;

public record LoginRequest(
        @NotEmpty(message = "Nome não pode ser nulo")
        String name,
        @NotEmpty(message = "Senha não pode ser nula")
        String password
) {}
