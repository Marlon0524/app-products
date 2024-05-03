package com.api.products.service;

import com.api.products.model.ApiResponse;
import com.api.products.model.Categories;
import com.api.products.model.Products;
import com.api.products.repository.CategoriesRepository;
import com.api.products.repository.ProductsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductsService {
    private final ProductsRepository productsRepository;
    private final CategoriesRepository categoriesRepository;

    public ResponseEntity<ApiResponse<Products>> createProducts(Products products) {
        try {
            //Obtener el Id de la categoria desde el producto
            Integer categoryId = products.getCategories().getCategory_id();

            //verificar si la categoria existe en la base de datos
            Optional<Categories> existingCategoriesOptional = categoriesRepository.findById(categoryId);
            if (existingCategoriesOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse<>(null, "error", "categoria no encontrada con el ID:" + categoryId));
            }
            //obtener la instancia existente de category en la bd
            Categories existingCategories = existingCategoriesOptional.get();
            //Asignar la categoria existente al producto
            products.setCategories(existingCategories);
            //guardar el producto en la bd
            Products newProducts = productsRepository.save(products);
            //construir el objeto apiresponse con el producto creado
            ApiResponse<Products> response = new ApiResponse<>(newProducts, "success", "producto creado con éxito");
            //devolver el responseentity con el apiresponse
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            //manejar cualquier excepción que pueda ocurrir
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(null, "error", "error al crear el producto " + e.getMessage()));
        }
    }

    public ResponseEntity<ApiResponse<Products>> getProductsId(@PathVariable Integer id) {
        try {
            Optional<Products> optionalProducts = productsRepository.findById(id);
            if (optionalProducts.isPresent()) {
                Products product = optionalProducts.get();
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ApiResponse<>(product, "success", "Producto encontrado"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(null, "error", "producto no encontrado con el id: " + id));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(null, "error", "error al obtener el producto" + e.getMessage()));
        }
    }

    public Page<Products> getAllProducts(Pageable pageable) {
        return productsRepository.findAll(pageable);
    }
    public List<Products> filterProducts(Integer categoryId, Double price, Integer quantity) {
        return productsRepository.findProductsByFilters(categoryId,price,quantity);
    }


}
