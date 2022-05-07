package net.godaa.SpringEcommerce.service.Impl;

import net.godaa.SpringEcommerce.domain.Category;
import net.godaa.SpringEcommerce.repository.CategoryRepo;
import net.godaa.SpringEcommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepo categoryRepo;

    @Override
    public List<Category> getAllCategory() {
        return categoryRepo.findAll();

    }

    @Override
    public void addCategory(Category category) {
        categoryRepo.save(category);

    }

    @Override
    public void removeCategory(Long categoryId) {
        categoryRepo.deleteById(categoryId);

    }

    @Override
    public Optional<Category> getCategoryById(Long categoryId) {
        Category category = categoryRepo.getById(categoryId);
        return Optional.of(category);
    }


}
