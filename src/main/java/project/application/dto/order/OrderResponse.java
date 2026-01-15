package project.application.dto.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import project.application.dto.customer.CustomerResponse;
import project.application.dto.orderItem.OrderItemResponse;
import project.application.entities.OrderStatus;

public record OrderResponse(

    Long id,
    CustomerResponse customer,
    OrderStatus status, 
    BigDecimal total,
    LocalDateTime createdAt,
    List<OrderItemResponse> items
){}
