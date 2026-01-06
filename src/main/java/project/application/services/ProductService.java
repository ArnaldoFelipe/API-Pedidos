package project.application.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.application.entities.Product;
import project.application.repository.ProductRepository;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;

    public Product createProduct(String name, BigDecimal price){
        if(price.compareTo(BigDecimal.ZERO)<= 0){
            throw new IllegalArgumentException("Insira um valor valido");
        }
        Product product = new Product(name, price);
        return productRepository.save(product);
    }

    public Product findById(Long id){
        if(id == null || id <=0){
            throw new IllegalArgumentException("Insira um id valido");
        }
        return productRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));
    }

    public List<Product> listProducts(){
        return productRepository.findAll();
    }

    // public Product updateProduct(String name, BigDecimal price, Long id){
    //     if(id == 0 || id == null){
    //         throw new IllegalArgumentException("insira um id valido");
    //     }

    //     Product product = productRepository.findById(id)
    //                 .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));
    // }

}
