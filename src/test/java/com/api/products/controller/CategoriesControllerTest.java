package com.api.products.controller;

import com.api.products.model.ApiResponse;
import com.api.products.model.Categories;
import com.api.products.repository.CategoriesRepository;
import com.api.products.service.CategoriesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class CategoriesControllerTest {
    @Mock
    private CategoriesService categoriesService;
    @Mock
    private CategoriesRepository categoriesRepository;
    @InjectMocks
    private CategoriesController categoriesController;
    @Test
    void createCategories() {

        CategoriesService categoriesService = mock(CategoriesService.class);
        CategoriesController categoriesController = new CategoriesController(categoriesService);
        Categories mockCategories = new Categories();

        when(categoriesService.createCategories(any())).thenReturn(ResponseEntity.ok(new ApiResponse<>(mockCategories,"success","Categoria creada satisfactoriamente")));

        ResponseEntity<ApiResponse<Categories>> responseEntity = categoriesController.createCategories(mockCategories);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("success", responseEntity.getBody().getStatus());
        assertEquals("Categoria creada satisfactoriamente", responseEntity.getBody().getMessage());
        assertEquals(mockCategories, responseEntity.getBody().getData());
        // Verifica que el método de servicio fue llamado una vez con el argumento correcto
        verify(categoriesService, times(1)).createCategories(mockCategories);
    }
}