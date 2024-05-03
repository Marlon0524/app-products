package com.api.products.controller;

import com.api.products.model.ApiResponse;
import com.api.products.model.Categories;
import com.api.products.service.CategoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoriesController {
    private final CategoriesService categoriesService;

    @PostMapping
    public ResponseEntity<ApiResponse<Categories>> createCategories (@RequestBody Categories categories){
        return categoriesService.createCategories(categories);
    }
}
