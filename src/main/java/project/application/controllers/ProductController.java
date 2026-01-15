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
import project.application.dto.product.ProductRequest;
import project.application.dto.product.ProductResponse;
import project.application.services.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
    

    @Autowired
    private ProductService productService;
    
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest productRequest){
        ProductResponse vProduct = productService.createProduct(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(vProduct);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductFindById(@PathVariable  @Positive Long id){
        ProductResponse vProduct = productService.findById(id);
        return ResponseEntity.ok(vProduct);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> listProducts(){
        List<ProductResponse> products = productService.listProducts();
        return ResponseEntity.ok(products);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable  @Positive Long id, @RequestBody ProductRequest productRequest){
        ProductResponse product = productService.updateProduct(id, productRequest);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable @Positive Long id){
        productService.deleteProduct(id);
    }
}
