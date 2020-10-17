package com.portum.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portum.application.CategoryService;
import com.portum.domain.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    private final int ID = 1;
    private final String NAME = "aName";
    private final String DESCRIPTION = "aDescription";
    private final Category CATEGORY = new Category(NAME, DESCRIPTION);

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CategoryService repository;

    @Test
    public void getCategory() throws Exception {

        Category aCategory = new Category(NAME, DESCRIPTION);
        aCategory.setId(ID);

        given(repository.getCategory(ID)).willReturn(
                aCategory);

        mvc.perform(get("/category/" + ID).accept(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("$.id", is(ID)))
                .andExpect(jsonPath("$.name", is(NAME)))
                .andExpect(jsonPath("$.description", is(DESCRIPTION)))
                .andExpect(jsonPath("$._links.self.href", is("http://localhost/category/" + ID)))
                .andExpect(jsonPath("$._links.categories.href", is("http://localhost/category/all")))
                .andReturn();
    }

    @Test
    public void getAllCategories() throws Exception {

        Category aCategory = new Category(NAME, DESCRIPTION);
        aCategory.setId(ID);


        given(repository.getAll()).willReturn(
                Collections.singletonList(aCategory)
        );

        mvc.perform(get("/category/all").accept(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("$._embedded.categoryList[0].id", is(ID)))
                .andExpect(jsonPath("$._embedded.categoryList[0].name", is(NAME)))
                .andExpect(jsonPath("$._embedded.categoryList[0].description", is(DESCRIPTION)))
                .andExpect(jsonPath("$._embedded.categoryList[0]._links.self.href", is("http://localhost/category/" + ID)))
                .andExpect(jsonPath("$._embedded.categoryList[0]._links.categories.href", is("http://localhost/category/all")))
                .andExpect(jsonPath("$._links.self.href", is("http://localhost/category/all")))
                .andReturn();
    }

    @Test
    public void AddCategory() throws Exception {
        Category aCategory = new Category(NAME, DESCRIPTION);

        given(repository.addCategory(aCategory)).willReturn(
                aCategory
        );

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(aCategory);

        mvc.perform(post("/category/").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andReturn();
    }
}
