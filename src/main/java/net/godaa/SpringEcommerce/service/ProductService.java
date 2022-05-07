package net.godaa.SpringEcommerce.service;

import net.godaa.SpringEcommerce.domain.Product;
import net.godaa.SpringEcommerce.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepo productRepo;


    public Optional<Product> getProductById(Long productId) {
        return productRepo.findById(productId);

    }
}
