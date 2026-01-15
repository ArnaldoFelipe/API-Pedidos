package project.application.dto.order;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import project.application.dto.orderItem.OrderItemRequest;

public record OrderRequest(

    @NotNull(message = "Customer ID is required")
    @Positive(message = "Customer ID must be positive")
    Long customerId,

    @NotEmpty(message = "Order must have at least one item")
    List<@Valid OrderItemRequest> items
) {}
    

