package project.application.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.application.entities.Customer;
import project.application.repository.CustomerRepository;

@Service
public class CustomerService {
    
    @Autowired
    CustomerRepository customerRepository;

    @Transactional
    public Customer createCustomer(String name, String email){
        if(email == null || email.isBlank()){
            throw new IllegalArgumentException("Insira um email valido");
        }
        Customer customer = new Customer(name, email);
        return customerRepository.save(customer);
    }

    public Customer findById(Long id){
        if(id == null || id <=0){
            throw new IllegalArgumentException("Insira um id valido");
        }
        return customerRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));
    }

    public List<Customer> listCustomers(){
        return customerRepository.findAll();
    }

    @Transactional
    public Customer updateCustomer(Long id ,String name, String email){
        if(id == null || id <= 0){
            throw new IllegalArgumentException("insira um id valido");
        }
        if(email == null || email.isBlank()){
            throw new IllegalArgumentException("Insira um email valido");
        }
        Customer customer = customerRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

        customer.setName(name);
        customer.setEmail(email);
        return customerRepository.save(customer);
    }

    @Transactional
    public void deleteCustomer(Long id){
        if(id == 0 || id == null){
            throw new IllegalArgumentException("insira um id valido");
        }
        Customer customer = customerRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("cliente não encontrado"));

        customerRepository.delete(customer);
    }
}
