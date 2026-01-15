package project.application.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import project.application.dto.customer.CustomerRequest;
import project.application.dto.customer.CustomerResponse;
import project.application.entities.Customer;
import project.application.repository.CustomerRepository;

@Service
public class CustomerService {
    
    @Autowired
    CustomerRepository customerRepository;

    @Transactional
    public CustomerResponse createCustomer(CustomerRequest request){
        
        Customer customer = new Customer();
        customer.setName(request.name());
        customer.setEmail(request.email());
        
        Customer saved = customerRepository.save(customer);

        return new CustomerResponse(
            saved.getId(),
            saved.getName(),
            saved.getEmail());
    }

    public CustomerResponse findById(Long id){
        
        Customer customer =  customerRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer não encontrado"));

        return new CustomerResponse(
            customer.getId(),
            customer.getName(),
            customer.getEmail()
        );
    }

    public List<CustomerResponse> listCustomers(){
        return customerRepository.findAll().stream()
            .map(c ->  new CustomerResponse(c.getId(), c.getName(), c.getEmail()))
            .toList();
    }           

    @Transactional
    public CustomerResponse updateCustomer(Long id ,CustomerRequest request){
    
        Customer customer = customerRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

        customer.setName(request.name());
        customer.setEmail(request.email());
        
        Customer update = customerRepository.save(customer);

        return new CustomerResponse(
            update.getId(),
            update.getName(),
            update.getEmail());
    }

    @Transactional
    public void deleteCustomer(Long id){
        Customer customer = customerRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("cliente não encontrado"));

        customerRepository.delete(customer);
    }
}
