package net.godaa.SpringEcommerce.controller;

import net.godaa.SpringEcommerce.domain.Category;
import net.godaa.SpringEcommerce.repository.CategoryRepo;
import net.godaa.SpringEcommerce.service.Impl.CategoryServiceImpl;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
public class CategoryController {

    CategoryServiceImpl categoryService;

    CategoryRepo categoryRepo;

    public CategoryController(CategoryServiceImpl categoryService, CategoryRepo categoryRepo) {
        this.categoryService = categoryService;
        this.categoryRepo = categoryRepo;
    }

    @PostMapping("/category/add")
    public ResponseEntity<Category> addCategory(@RequestBody Category category) {
        categoryService.addCategory(category);
        return new ResponseEntity<>(HttpStatus.OK);


    }


    @PutMapping("/category/{categoryId}")
    public ResponseEntity<Category> updateCategory(@PathVariable("categoryId") Long categoryId, @RequestBody Category categoryRequest) {
        Category category = categoryService.getCategoryById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category with Id (" + categoryId + ") Not found"));
        category.setName(categoryRequest.getName());
        category.setDescription(categoryRequest.getDescription());
        category.setProducts(categoryRequest.getProducts());
        category.setImageURL(categoryRequest.getImageURL());
        return new ResponseEntity<>(categoryRepo.save(category), HttpStatus.OK);


    }
}
