package project.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import project.application.entities.Customer;

public interface CustomerRepository extends JpaRepository <Customer,Long>{
    
}
