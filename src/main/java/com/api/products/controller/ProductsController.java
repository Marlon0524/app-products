package com.api.products.controller;

import com.api.products.model.ApiResponse;
import com.api.products.model.Products;
import com.api.products.repository.ProductsRepository;
import com.api.products.service.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductsController {
    private final ProductsService productsService;
    private final ProductsRepository productsRepository;
    private static final Logger logger = Logger.getLogger(ProductsController.class.getName());


    @PostMapping
    public ResponseEntity<ApiResponse<Products>> createProducts(@RequestBody Products products) {
        return productsService.createProducts(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Products>> getProductsById(@PathVariable Integer id) {
        return productsService.getProductsId(id);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<Products>>> getAllProducts(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        logger.info("Llamada al endpoint GET /api recibida.");
        Page<Products> productsPage = productsService.getAllProducts(PageRequest.of(page, size));

        if (productsPage != null && productsPage.hasContent()) {
            ApiResponse<Page<Products>> response = new ApiResponse<>(productsPage, "success", "Productos obtenidos satisfactoriamente");
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<Page<Products>> response = new ApiResponse<>(null, "error", "No se encontraron productos");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/filters")
    public ResponseEntity<ApiResponse<List<Products>>> getProductsByFilters(
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Double price,
            @RequestParam(required = false) Integer quantity) {

        List<Products> products = productsRepository.findProductsByFilters(categoryId, price, quantity);

        if (products.isEmpty()) {
            // Si la lista de productos está vacía, devuelve un ResponseEntity con un mensaje apropiado
            ApiResponse<List<Products>> response = new ApiResponse<>(null, "error", "No se encontraron productos con los filtros proporcionados");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } else {
            // Si se encontraron productos, devuelve un ResponseEntity con los productos y un mensaje de éxito
            ApiResponse<List<Products>> response = new ApiResponse<>(products, "success", "Productos obtenidos satisfactoriamente");
            return ResponseEntity.ok().body(response);
        }
    }
}
