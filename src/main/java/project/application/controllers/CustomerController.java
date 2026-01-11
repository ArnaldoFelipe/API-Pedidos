package project.application.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping("/{id}")
    public ResponseEntity<Customer> findCustomerById(@PathVariable Long id){
        Customer vCustomer =  customerService.findById(id);
        return ResponseEntity.status(201).body(vCustomer);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Customer>> listAllCustomers(){
        List<Customer> customers = customerService.listCustomers();
        return ResponseEntity.status(200).body(customers);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id,@RequestBody Customer customer){
        Customer vCustomer =  customerService.updateCustomer(id, customer.getName(), customer.getEmail());
        return ResponseEntity.status(200).body(vCustomer);
    
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Long id){
        customerService.deleteCustomer(id);
        return "Customer id: " + id + " deletado com sucesso";
    }

}
