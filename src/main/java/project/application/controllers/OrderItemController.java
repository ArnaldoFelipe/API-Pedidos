package project.application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import project.application.dto.orderItem.OrderItemRequest;
import project.application.dto.orderItem.OrderItemResponse;
import project.application.services.OrderItemService;

@RestController
@RequestMapping("/orderItems")
public class OrderItemController {
    
    @Autowired
    private OrderItemService orderItemService;

    @PostMapping("/{orderId}")
    public ResponseEntity<OrderItemResponse> addItem(@PathVariable @Positive Long orderId, @RequestBody @Valid OrderItemRequest orderItemRequest){
        OrderItemResponse orderItem  = orderItemService.addItem(orderId, orderItemRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderItem);
    }

    @DeleteMapping("/{orderId}/{itemId}")
    public ResponseEntity<OrderItemResponse> removeItem(@PathVariable @Positive Long orderId, @PathVariable @Positive Long itemId){
        OrderItemResponse orderItem = orderItemService.removeItem(orderId, itemId);
        return ResponseEntity.ok(orderItem);
    }
}
