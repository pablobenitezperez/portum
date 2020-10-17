package com.portum.application;

import com.portum.domain.Category;
import com.portum.domain.CategoryNotFoundException;
import com.portum.domain.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category getCategory(long id) {

        return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Category addCategory(Category category) {
        categoryRepository.save(category);

        return category;
    }

    public Category updateCategory(Category newCategory, long id) {

        return categoryRepository.findById(id)
                .map(category -> {
                    category.setDescription(newCategory.getDescription());
                    category.setName(newCategory.getName());
                    return categoryRepository.save(category);
                })
                .orElseGet(() -> {
                    newCategory.setId(id);
                    return categoryRepository.save(newCategory);
                });
    }
}
