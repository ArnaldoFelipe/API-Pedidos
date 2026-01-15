package project.application.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import project.application.dto.customer.CustomerResponse;
import project.application.dto.order.OrderRequest;
import project.application.dto.order.OrderResponse;
import project.application.dto.orderItem.OrderItemResponse;
import project.application.entities.Customer;
import project.application.entities.Order;
import project.application.entities.OrderItem;
import project.application.entities.OrderStatus;
import project.application.entities.Product;
import project.application.repository.CustomerRepository;
import project.application.repository.OrderRepository;
import project.application.repository.ProductRepository;

@Service
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;


    @Transactional
    public OrderResponse createOrder(OrderRequest orderRequest){

        Customer customer = customerRepository.findById(orderRequest.customerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Cliente não encontrado"));

        //Criar o pedido com status inicial PENDING
        Order order = new Order();
        order.setCustomer(customer);
        order.setStatus(OrderStatus.PENDING);

        //Criar os itens do pedido a partir do DTO
        List<OrderItem> items = orderRequest.items().stream().map(ItemReq -> {
            Product product = productRepository.findById(ItemReq.productId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado: " + ItemReq.productId()));

            if(ItemReq.quantity() <= 0){
                throw new IllegalArgumentException("Quantidade deve ser maior que 0");
            }
        
            return new OrderItem(order, product, ItemReq.quantity(), product.getPrice());
        }).toList();

        order.setItems(items);

        //Calcular o total do pedido
        BigDecimal total = items.stream()
            .map(i -> i.getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotal(total);

        orderRepository.save(order);

         //Mapear os itens para OrderItemResponse
        List<OrderItemResponse> itemResponses = items.stream().map(i -> 
            new OrderItemResponse(
                i.getProduct().getId(),
                i.getProduct().getName(),
                i.getQuantity(),
                i.getPrice(),
                i.getPrice().multiply(BigDecimal.valueOf(i.getQuantity()))
            )
        ).toList();

        //Criar e retornar o OrderResponse
        return new OrderResponse(
            order.getId(),
            new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getEmail()
            ),
            order.getStatus(),
            order.getTotal(),
            order.getCreatedAt(),
            itemResponses
        );
    }

    public OrderResponse findById(Long id){
        if(id == null || id <=0){
            throw new IllegalArgumentException("Insira um id valido");
        }
        Order order =  orderRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Pedido não encontrado"));

        List<OrderItemResponse> items = order.getItems().stream()
            .map(item -> new OrderItemResponse(
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getQuantity(),
                item.getPrice(),
                item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))
            ))
            .toList();            

        return new OrderResponse(
            order.getId(),
            new CustomerResponse(
                order.getCustomer().getId(),
                order.getCustomer().getName(),
                order.getCustomer().getEmail()
            ),
            order.getStatus(),
            order.getTotal(),
            order.getCreatedAt(),
            items
        );
    }

    public List<OrderResponse> listOrders(){
        List<Order> orders = orderRepository.findAll();

        return orders.stream()
            .map(order -> {
                List<OrderItemResponse> items = order.getItems().stream()
                    .map(item -> new OrderItemResponse(
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getPrice(),
                        item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))
                    ))
                    .toList();
                
                return new OrderResponse(
                    order.getId(),
                    new CustomerResponse(
                        order.getCustomer().getId(),
                        order.getCustomer().getName(),
                        order.getCustomer().getEmail()
                    ),
                    order.getStatus(),
                    order.getTotal(),
                    order.getCreatedAt(),
                    items
                );
            })
            .toList();
    }

    @Transactional
    public OrderResponse updateOrder(Long orderId, OrderStatus newStatus){

        Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new IllegalArgumentException("pedido não encontrado"));

        if(order.getStatus() == OrderStatus.CANCELLED){
            throw new IllegalArgumentException("Pedido cancelado não pode ser alterado");
        }

        if(order.getStatus() == OrderStatus.PAID && newStatus == OrderStatus.CANCELLED){
            throw new IllegalArgumentException("Pedido pago não pode ser alterado");
        }
        order.setStatus(newStatus);
        orderRepository.save(order);

        List<OrderItemResponse> items = order.getItems().stream()
            .map(item -> new OrderItemResponse(
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getQuantity(),
                item.getPrice(),
                item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))
            ))
            .toList();
            
        return new OrderResponse(
            order.getId(),
            new CustomerResponse(
                order.getCustomer().getId(),
                order.getCustomer().getName(),
                order.getCustomer().getEmail()
            ),
            order.getStatus(),
            order.getTotal(),
            order.getCreatedAt(),
            items
        );
    }

    @Transactional
    public void deleteOrder(Long id){
    
        Order order = orderRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"pedido não encontrado"));

        orderRepository.delete(order);
    }
}
