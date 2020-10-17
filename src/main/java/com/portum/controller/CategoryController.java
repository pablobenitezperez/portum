package com.portum.controller;

import com.portum.application.CategoryService;
import com.portum.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<EntityModel<Category>> getCategory(@PathVariable(name = "id") long id) {
        Category category = categoryService.getCategory(id);

        category.add(linkTo(methodOn(CategoryController.class)
                .getCategory(id)).withSelfRel());
        category.add(linkTo(methodOn(CategoryController.class)
                .getAllCategories()).withRel("categories"));

        return ResponseEntity.ok(EntityModel.of(category));
    }

    @GetMapping("/category/all")
    public ResponseEntity<CollectionModel<EntityModel<Category>>> getAllCategories() {

        List<EntityModel<Category>> categories = categoryService.getAll().stream()
                .map(category -> EntityModel.of(category, //
                        linkTo(methodOn(CategoryController.class).getCategory(category.getId())).withSelfRel(),
                        linkTo(methodOn(CategoryController.class).getAllCategories()).withRel("categories")))
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(categories, linkTo(methodOn(CategoryController.class).getAllCategories()).withSelfRel()));
    }

    @PostMapping("/category")
    public ResponseEntity<?> addCategory(@RequestBody Category category) {

        try {
            Category newCategory = categoryService.addCategory(category);
            newCategory
                    .add(linkTo(methodOn(CategoryController.class).getCategory(newCategory.getId())).withSelfRel());

            return ResponseEntity.created(new URI(newCategory.getRequiredLink(IanaLinkRelations.SELF).getHref()))
                    .body(newCategory);
        } catch (URISyntaxException ex) {
            return ResponseEntity.badRequest().body("Unable to create " + category);
        }
    }

    @PutMapping("/category/{id}")
    public ResponseEntity<?> updateCategory(@RequestBody Category category
            , @PathVariable long id) {
        Category updatedCategory = categoryService.updateCategory(category, id);

        Link newlyCreatedLink = linkTo(methodOn(CategoryController.class).getCategory(id)).withSelfRel();

        try {
            return ResponseEntity.noContent().location(new URI(newlyCreatedLink.getHref())).build();
        } catch (URISyntaxException ex) {
            return ResponseEntity.badRequest().body("Unable to update " + updatedCategory);
        }
    }

}
