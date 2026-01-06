package project.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import project.application.entities.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{
    
}
