package net.godaa.SpringEcommerce.service;

import net.godaa.SpringEcommerce.domain.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> getAllCategory();

    void addCategory(Category category);

    public void removeCategory(Long categoryId);

    public Optional<Category> getCategoryById(Long categoryId);


}
