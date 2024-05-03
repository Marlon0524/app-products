package com.api.products.controller;

import com.api.products.model.ApiResponse;
import com.api.products.model.Products;
import com.api.products.repository.ProductsRepository;
import com.api.products.service.ProductsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductsControllerTest {
    @Mock
    private ProductsService productsService;

    @Mock
    private ProductsRepository productsRepository;

    @InjectMocks
    private ProductsController productsController;
    @Test
    void createProducts() {
        Products products = new Products();
        ApiResponse<Products> expectedResponse = new ApiResponse<>(products, "success", "Producto creado satisfactoriamente");
        when(productsService.createProducts(products)).thenReturn(new ResponseEntity<>(expectedResponse, HttpStatus.OK));

        ResponseEntity<ApiResponse<Products>> actualResponse = productsController.createProducts(products);

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(expectedResponse, actualResponse.getBody());
    }

    @Test
    void getProductsById() {
        Integer id = 1;
        Products products = new Products();
        ApiResponse<Products> expectedResponse = new ApiResponse<>(products, "success", "Producto obtenido satisfactoriamente");
        when(productsService.getProductsId(id)).thenReturn(new ResponseEntity<>(expectedResponse, HttpStatus.OK));

        ResponseEntity<ApiResponse<Products>> actualResponse = productsController.getProductsById(id);

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(expectedResponse, actualResponse.getBody());
    }

    @Test
    void getAllProducts() {
        int page = 0;
        int size = 10;
        List<Products> productsList = Arrays.asList(new Products(), new Products());
        Page<Products> productsPage = new PageImpl<>(productsList);
        ApiResponse<Page<Products>> expectedResponse = new ApiResponse<>(productsPage, "success", "Productos obtenidos satisfactoriamente");
        when(productsService.getAllProducts(PageRequest.of(page, size))).thenReturn(productsPage);

        ResponseEntity<ApiResponse<Page<Products>>> actualResponse = productsController.getAllProducts(page, size);

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(expectedResponse, actualResponse.getBody());
    }

    @Test
    void getProductsByFilters() {
        Integer categoryId = 1;
        Double price = 5.000;
        Integer quantity = 120;
        List<Products> productsList = Arrays.asList(new Products(), new Products());
        ApiResponse<List<Products>> expectedResponse = new ApiResponse<>(productsList, "success", "Productos obtenidos satisfactoriamente");
        when(productsRepository.findProductsByFilters(categoryId, price, quantity)).thenReturn(productsList);

        ResponseEntity<ApiResponse<List<Products>>> actualResponse = productsController.getProductsByFilters(categoryId, price, quantity);

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(expectedResponse, actualResponse.getBody());
    }
}