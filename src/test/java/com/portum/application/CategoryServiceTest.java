package com.portum.application;

import com.portum.domain.Category;
import com.portum.domain.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CategoryServiceTest {

    private final long ID = 1;
    private final String NAME = "aName";
    private final String DESCRIPTION = "AdESCRIPTION";

    @InjectMocks
    private CategoryService categoryService;
    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    /*
    private CategoryRepository categoryRepository;
    private CategoryService service;

    @BeforeEach
    void setUp() {
        categoryRepository = mock(CategoryRepository.class);
        service = new CategoryService(categoryRepository);
    }
    */

    @Test
    void getCategory() {
        Category aCategory = new Category(NAME, DESCRIPTION);
        aCategory.setId(ID);
        when(categoryRepository.findOneById(ID)).thenReturn(aCategory);

        Category result = categoryService.getCategory(ID);

        assertEquals(ID, result.getId());
        assertEquals(NAME, result.getName());
        assertEquals(DESCRIPTION, result.getDescription());
    }

    @Test
    void addCategory() {
        Category aCategory = new Category(NAME, DESCRIPTION);
        aCategory.setId(ID);
        when(categoryRepository.save(aCategory)).thenReturn(aCategory);

        long idResult = categoryService.addCategory(NAME, DESCRIPTION);

        assertEquals(ID, idResult);
    }
}