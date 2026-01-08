package project.application.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.application.entities.Customer;
import project.application.entities.Order;
import project.application.entities.OrderItem;
import project.application.repository.CustomerRepository;
import project.application.repository.OrderRepository;

@Service
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    public Order createOrder(Long customerId, String status, List<OrderItem> items){
        if (customerId == null || customerId <= 0) {
        throw new IllegalArgumentException("Insira um Id de cliente válido");
        }
        
        for(OrderItem item : items){
            if(item.getPrice() == null || item.getQuantity() == null || item.getQuantity() <= 0){
                throw new IllegalArgumentException("Item invalido: preço ou quantidade não podem ser nulos");
            }
        }

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

        Order order = new Order (customer, status, BigDecimal.ZERO);

        for(OrderItem item : items){
            item.setOrder(order);
            order.getItems().add(item);
        }

        BigDecimal total = BigDecimal.ZERO;

        for (OrderItem item : order.getItems()){
            BigDecimal itemTotal = item.getPrice().multiply(new BigDecimal(item.getQuantity()));
            total = total.add(itemTotal);
        }

        order.setTotal(total);

        return orderRepository.save(order);
    }

    public Order findById(Long id){
        if(id == null || id <=0){
            throw new IllegalArgumentException("Insira um id valido");
        }
        return orderRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));
    }

    public List<Order> listOrders(){
        return orderRepository.findAll();
    }

    @Transactional
    public Order updateOrder(Long orderId, String status){

        if(orderId == null || orderId <= 0){
            throw new IllegalArgumentException("insira um id de pedido valido");
        }

        Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new IllegalArgumentException("pedido não encontrado"));

        order.setStatus(status);
        return order;
    }

    @Transactional
    public void deleteOrder(Long orderId){
        if(orderId == null || orderId <= 0){
            throw new IllegalArgumentException("insira um id do pedido valido");
        }
        Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new IllegalArgumentException("pedido não encontrado"));

        orderRepository.delete(order);
    }
}
