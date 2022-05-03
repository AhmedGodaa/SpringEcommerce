package net.godaa.SpringEcommerce.controller;

import net.godaa.SpringEcommerce.domain.Product;
import net.godaa.SpringEcommerce.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api")
public class ProductController {
    //    create read delete update
    @Autowired
    ProductRepo productRepo;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok().body(productRepo.findAll());
    }

    @PostMapping("/product/save")
    public ResponseEntity<List<Product>> saveProduct(@RequestBody Product product) {
        return new ResponseEntity(productRepo.save(product), HttpStatus.CREATED);
    }


    @DeleteMapping("/product/{productId}")
    public ResponseEntity<HttpStatus> deleteProductById(@PathVariable Long productId) {
        if (productRepo.existsById(productId)) {
            productRepo.deleteById(productId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } else {
            return ResponseEntity.notFound().build();
        }

    }


    @GetMapping("/product/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) {
        if (productRepo.existsById(productId)) {
            return ResponseEntity.ok().body(productRepo.findById(productId).get());

        } else {
            return ResponseEntity.notFound().build();
        }

    }
}
