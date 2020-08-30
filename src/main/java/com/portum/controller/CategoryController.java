package com.portum.controller;

import com.portum.application.CategoryService;
import com.portum.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/category/{id}")
    public Category getCategory(@PathVariable(name = "id") long id) {
        return categoryService.getCategory(id);


    }

    @GetMapping("/category/all")
    public List<Category> getAllCategories() {
        return new ArrayList<Category>();
    }

    @PostMapping("/category")
    public long addCategory(String name, String description) {
        long id = categoryService.addCategory(name, description);

        return id;
    }

}
