package project.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import project.application.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
    
}
