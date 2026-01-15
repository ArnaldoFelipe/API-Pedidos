package project.application.dto.product;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductRequest(
    
    @NotBlank(message = "Name is required")
    String name,

    @NotNull(message = "Price ir required")
    @Positive(message = "Price must be positive")
    BigDecimal price
){} 
    

