// package project.application.controllers;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import project.application.entities.Order;
// import project.application.entities.Product;
// import project.application.services.OrderService;

// @RestController
// @RequestMapping("/order")
// public class OrderController {
    
//     @Autowired
//     private OrderService orderService;

//     @PostMapping("/created")
//     public ResponseEntity<Order> createOrder(@RequestBody Order order){
//         Order vOrder = OrderService.createOrder();
//         return ResponseEntity.status(201).body(vOrder);
//     }

//     @GetMapping("/{id}")
//     public ResponseEntity<Product> findProductById(@PathVariable Long id){
//         Product vProduct =  productService.findById(id);
//         return ResponseEntity.status(201).body(vProduct);
//     }

//     @GetMapping("/list")
//     public ResponseEntity<List<Product>> listAllProducts(){
//         List<Product> products = productService.listProducts();
//         return ResponseEntity.status(200).body(products);
//     }

//     @PutMapping("/update/{id}")
//     public ResponseEntity<Product> updateProduct(@PathVariable Long id,@RequestBody Product product){
//         Product vProduct =  productService.updateProduct(product.getName(), product.getPrice(), id);
//         return ResponseEntity.status(200).body(vProduct);
    
//     }

//     @DeleteMapping("/delete/{id}")
//     public String deleteProduct(@PathVariable Long id){
//         productService.deleteProduct(id);
//         return "Product id: " + id + " deletado com sucesso";
//     }
// }
