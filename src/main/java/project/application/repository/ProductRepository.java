package project.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import project.application.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
    
}
