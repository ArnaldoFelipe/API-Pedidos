package project.application.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import project.application.dto.customer.CustomerRequest;
import project.application.dto.customer.CustomerResponse;
import project.application.services.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    
    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CustomerRequest customer){
        CustomerResponse vCustomer = customerService.createCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(vCustomer);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable @Positive Long id){
        CustomerResponse customer = customerService.findById(id);
        return ResponseEntity.ok(customer);
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> listCustomers(){
        List<CustomerResponse> customers = customerService.listCustomers();
        return ResponseEntity.ok(customers);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable @Positive Long id, @Valid @RequestBody CustomerRequest customerRequest){

        CustomerResponse customer = customerService.updateCustomer(id, customerRequest);
        return ResponseEntity.ok(customer);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable @Positive Long id){
        customerService.deleteCustomer(id);
    }
}
