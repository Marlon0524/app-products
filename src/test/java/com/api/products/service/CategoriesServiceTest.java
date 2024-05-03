package com.api.products.service;

import com.api.products.model.ApiResponse;
import com.api.products.model.Categories;
import com.api.products.repository.CategoriesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoriesServiceTest {
    @Mock
    private CategoriesRepository categoriesRepository;
    @InjectMocks
    private CategoriesService categoriesService;

    @Test
    void createCategories() {
        // Arrange
        CategoriesRepository categoriesRepository = mock(CategoriesRepository.class);
        CategoriesService categoriesService = new CategoriesService(categoriesRepository);
        Categories mockCategories = new Categories(); // Mocking the categories object
        Categories savedCategory = new Categories(); // Mocking the saved categories object

        // Mocking the repository method to return the saved category
        when(categoriesRepository.save(any())).thenReturn(savedCategory);

        // Act
        ResponseEntity<ApiResponse<Categories>> responseEntity = categoriesService.createCategories(mockCategories);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("success", responseEntity.getBody().getStatus());
        assertEquals("Categoria creada correctamente", responseEntity.getBody().getMessage());
        assertEquals(savedCategory, responseEntity.getBody().getData());
    }
}