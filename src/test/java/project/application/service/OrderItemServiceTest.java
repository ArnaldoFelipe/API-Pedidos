package project.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import project.application.dto.orderItem.OrderItemRequest;
import project.application.dto.orderItem.OrderItemResponse;
import project.application.entities.Order;
import project.application.entities.OrderItem;
import project.application.entities.Product;
import project.application.repository.OrderItemRepository;
import project.application.repository.OrderRepository;
import project.application.repository.ProductRepository;
import project.application.services.OrderItemService;

@ExtendWith(MockitoExtension.class)
public class OrderItemServiceTest {

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;
    
    @InjectMocks
    private OrderItemService orderItemService; 

    @Test
    void adicionarItemQuandoPedidoEProdutoExistir(){

        Long orderId = 1L;
        Long productId = 1L;
        int quantity = 20;

        Product product = new Product();
        product.setId(productId);

        Order order = new Order();
        order.setId(orderId);

        OrderItemRequest orderItemRequest = new OrderItemRequest(product.getId(), quantity);

        Mockito.when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        Mockito.when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        OrderItemResponse orderItemResponse = orderItemService.addItem(order.getId(), orderItemRequest);

        assertNotNull(orderItemResponse);
        assertEquals(orderItemRequest.quantity(), orderItemResponse.quantity());

        assertEquals(1, order.getItems().size());
        assertEquals(quantity, order.getItems().get(0).getQuantity());
    }

    @Test
    void erroAoAdicionarItemComQuantidadeNegativa(){

        Long orderId = 1L;
        Long productId = 1L;
        int quantity = -5;

        Product product = new Product();
        product.setId(productId);

        Order order = new Order();
        order.setId(orderId);

        OrderItemRequest orderItemRequest = new OrderItemRequest(productId, quantity);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> orderItemService.addItem(orderId, orderItemRequest));

        assertEquals("Quantidade deve ser maior que zero", exception.getMessage());
    }

    @Test
    void deletarItemQuePertenceAoPedido(){

        Long orderId = 1L;
        Long itemId = 1L;

        Order order = new Order();
        order.setId(orderId);

        OrderItem orderItem = new OrderItem();
        orderItem.setId(itemId);

        orderItem.setOrder(order);
        order.getItems().add(orderItem);

        Product product = new Product();
        product.setId(10L);
        product.setName("Produto teste");

        orderItem.setProduct(product);
        orderItem.setQuantity(10);
        orderItem.setPrice(BigDecimal.valueOf(20));

        Mockito.when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        Mockito.when(orderItemRepository.findById(orderItem.getId())).thenReturn(Optional.of(orderItem));

        assertEquals(1, order.getItems().size());

        OrderItemResponse orderItemResponse = orderItemService.removeItem(orderId, itemId);

        assertNotNull(orderItemResponse);
        assertEquals(0, order.getItems().size());
    }

    @Test
    void erroAoDeletarItemQueNaoPertenceAoPedido(){

        Long orderId = 1L;
        Long itemId = 1L;

        Order order = new Order();
        order.setId(orderId);

        OrderItem orderItem = new OrderItem();
        orderItem.setId(itemId);

        Product product = new Product();
        product.setId(10L);
        product.setName("Produto teste");

        orderItem.setProduct(product);
        orderItem.setQuantity(10);
        orderItem.setPrice(BigDecimal.valueOf(20));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> orderItemService.removeItem(orderId, itemId));

        assertEquals("Pedido não encontrado", exception.getMessage());
    }
}
