package com.portum.application;

import com.portum.domain.Category;
import com.portum.domain.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category getCategory(long id) {
        Category category = categoryRepository.findOneById(id);

        return category;
    }

    public long addCategory(String name, String description) {
        Category category = new Category(name, description);
        categoryRepository.save(category);

        return category.getId();
    }
}
