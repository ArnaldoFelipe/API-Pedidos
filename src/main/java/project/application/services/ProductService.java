package project.application.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import project.application.dto.product.ProductRequest;
import project.application.dto.product.ProductResponse;
import project.application.entities.Product;
import project.application.repository.ProductRepository;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public ProductResponse createProduct(ProductRequest request){
        
        Product product = new Product();
        product.setName(request.name());
        product.setPrice(request.price());

        Product saved = productRepository.save(product);
        return new ProductResponse(saved.getId(), saved.getName(), saved.getPrice());
    }

    public ProductResponse findById(Long id){
        
        Product product = productRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Produto não encontrado"));

        return new ProductResponse(
            product.getId(),
            product.getName(),
            product.getPrice());
    }

    public List<ProductResponse> listProducts(){
        return productRepository.findAll().stream()
            .map(p -> new ProductResponse(p.getId(), p.getName(), p.getPrice()))
            .toList();
    }

    @Transactional
    public ProductResponse updateProduct(Long id, ProductRequest productRequest){
        
        Product product = productRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Produto não encontrado"));

        product.setName(productRequest.name());
        product.setPrice(productRequest.price());

        Product productUpdate = productRepository.save(product);

        return new ProductResponse(
            productUpdate.getId(),
            productUpdate.getName(),
            productUpdate.getPrice());
    }

    @Transactional
    public void deleteProduct(Long id){
        Product product = productRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Produto não encontrado"));

        productRepository.delete(product);
    }
}
