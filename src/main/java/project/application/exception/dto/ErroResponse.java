package project.application.exception.dto;

import java.time.LocalDateTime;

public record ErroResponse(
        String msg,
        String codigo,
        LocalDateTime timeStamp
) {}
