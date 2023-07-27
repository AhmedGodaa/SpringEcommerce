package net.godaa.SpringEcommerce.controller;

import net.godaa.SpringEcommerce.domain.Product;
import net.godaa.SpringEcommerce.repository.ProductRepo;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping
public class ProductController {

    ProductRepo productRepo;

    public ProductController(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok().body(productRepo.findAll());
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable("productId") Long productId) {
        if (productRepo.existsById(productId)) {
            return ResponseEntity.ok().body(productRepo.findById(productId).get());

        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<HttpStatus> deleteProductById(@PathVariable(value = "productId") Long productId) {
        if (productRepo.existsById(productId)) {
            productRepo.deleteById(productId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping("/product/save")
//    @PreAuthorize(SecurityConstants.ROLE_ADMIN)
    public ResponseEntity saveProduct(@RequestBody Product product) {
        return new ResponseEntity(productRepo.save(product), HttpStatus.CREATED);
    }

    @PutMapping("/product/edit/{productId}")
//    @PreAuthorize(SecurityConstants.ROLE_ADMIN)
    public void updateProduct(@PathVariable("productId") Long productId, @RequestBody Product productRequest) {
        Product product = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("productId : " + productId + "not found"));
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setDescription(productRequest.getDescription());
        product.setUnitStock(product.getUnitStock());
        productRepo.save(product);


    }


//    @PostMapping("/tutorials/{tutorialId}/comments")
//    public ResponseEntity<HttpStatus> createComment(
//            @RequestBody Comment commentBody,
//            @PathVariable(value = "tutorialId") Long tutorialId) {
//        tutorialRepo.findById(tutorialId).map(
//                tutorial -> {
//                    commentBody.setTutorial(tutorial);
//                    return commentRepo.save(commentBody);
//                }).orElseThrow(() ->
//                new ResourceNotFoundException("NOT FOUND TUTORIAL WITH ID" + tutorialId));
//        return new ResponseEntity<>(HttpStatus.OK);
//
//    }
}
