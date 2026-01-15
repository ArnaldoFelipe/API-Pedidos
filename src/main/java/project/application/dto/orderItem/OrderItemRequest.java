package project.application.dto.orderItem;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderItemRequest(

    @NotNull(message = "Product ID is required")
    @Positive(message = "Product ID must be positive")
    Long productId,

    @NotNull(message = "Quantity Product is required")
    @Positive(message = "Quantity must be positive")
    Integer quantity
) {}
