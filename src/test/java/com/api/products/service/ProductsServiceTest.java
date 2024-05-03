package com.api.products.service;

import com.api.products.model.ApiResponse;
import com.api.products.model.Categories;
import com.api.products.model.Products;
import com.api.products.repository.CategoriesRepository;
import com.api.products.repository.ProductsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.resolver.MockParameterResolver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductsServiceTest {
    @Mock
    private ProductsRepository productsRepository;

    @Mock
    private CategoriesRepository categoriesRepository;

    @InjectMocks
    private ProductsService productsService;

    @Test
    void createProducts() {
        // Arrange
        Products inputProducts = new Products();
        Categories categories = new Categories();
        categories.setCategory_id(1);
        inputProducts.setCategories(categories);

        when(categoriesRepository.findById(1)).thenReturn(Optional.of(categories));
        when(productsRepository.save(inputProducts)).thenReturn(inputProducts);

        // Act
        ResponseEntity<ApiResponse<Products>> responseEntity = productsService.createProducts(inputProducts);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("success", responseEntity.getBody().getStatus());
        assertEquals("producto creado con éxito", responseEntity.getBody().getMessage());
        assertEquals(inputProducts, responseEntity.getBody().getData());
    }

    @Test
    void getProductsId() {
        // Arrange
        Products expectedMovie = new Products();
        when(productsRepository.findById(1)).thenReturn(Optional.of(expectedMovie));

        // Act
        ResponseEntity<ApiResponse<Products>> responseEntity = productsService.getProductsId(1);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("success", responseEntity.getBody().getStatus());
        assertEquals("Producto encontrado", responseEntity.getBody().getMessage());
        assertEquals(expectedMovie, responseEntity.getBody().getData());
    }

    @Test
    void getAllProducts() {
        // Arrange
        Pageable pageable = Pageable.unpaged();
        List<Products> productsList = new ArrayList<>();
        Page<Products> expectedPage = new PageImpl<>(productsList);
        when(productsRepository.findAll(pageable)).thenReturn(expectedPage);

        // Act
        Page<Products> resultPage = productsService.getAllProducts(pageable);

        // Assert
        assertEquals(expectedPage, resultPage);
    }

    @Test
    void filterProducts() {
        // Arrange
        List<Products> expectedProductsList = new ArrayList<>();
        when(productsRepository.findProductsByFilters(anyInt(), anyDouble(), anyInt())).thenReturn(expectedProductsList);

        // Act
        List<Products> resultProductsList = productsService.filterProducts(1, 2.000, 120);

        // Assert
        assertEquals(expectedProductsList, resultProductsList);
    }
}