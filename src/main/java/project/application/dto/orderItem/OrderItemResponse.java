package project.application.dto.orderItem;

import java.math.BigDecimal;

public record OrderItemResponse(

    Long productId,
    String productName,
    int quantity,
    BigDecimal unitPrice,
    BigDecimal subTotal
) {}
