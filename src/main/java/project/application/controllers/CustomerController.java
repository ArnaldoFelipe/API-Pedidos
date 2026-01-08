package project.application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.application.entities.Customer;
import project.application.services.CustomerService;

@RestController
@RequestMapping("/Customers")
public class CustomerController {
    
    @Autowired
    private CustomerService customerService;

    @PostMapping("/created")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer){
        Customer vCustomer = customerService.createCustomer(customer.getName(), customer.getEmail());
        return ResponseEntity.status(201).body(vCustomer);
    }

    // @GetMapping("/{id}")
    // public ResponseEntity<Customer> findCustomerById(@PathVariable Long id){
    //     Customer vCustomer =  customerService.findById(id);
    //     return ResponseEntity.status(0)
    // }

}
