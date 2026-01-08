package project.application.services;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.application.entities.Order;
import project.application.entities.OrderItem;
import project.application.entities.Product;
import project.application.repository.OrderItemRepository;
import project.application.repository.OrderRepository;
import project.application.repository.ProductRepository;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public Order addItem(Long orderId, long productId, Integer quantity) {

        if (orderId == null || orderId <= 0) {
            throw new IllegalArgumentException("Pedido inválido");
        }

        if (productId <= 0) {
            throw new IllegalArgumentException("Produto inválido");
        }

        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantidade inválida");
        }

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));

        OrderItem orderItem = new OrderItem(
                order,
                product,
                quantity,
                product.getPrice());

        order.getItems().add(orderItem);
        recalcularTotal(order);

        return order;
    }

    @Transactional
    public Order removeItem(Long orderId, Long itemId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));

        OrderItem item = orderItemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Item não encontrado"));

        if(!item.getOrder().getId().equals(orderId)){
            throw new IllegalArgumentException("Item não pertence a esse pedido");
        }

        order.getItems().remove(item);
        recalcularTotal(order);

        return order;
    }

    private void recalcularTotal(Order order) {
        BigDecimal total = BigDecimal.ZERO;

        for (OrderItem item : order.getItems()) {
            total = total.add(item.getSubTotal());
        }

        order.setTotal(total);
    }
}
