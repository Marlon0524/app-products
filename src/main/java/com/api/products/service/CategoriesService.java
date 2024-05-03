package com.api.products.service;

import com.api.products.model.ApiResponse;
import com.api.products.model.Categories;
import com.api.products.repository.CategoriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoriesService {
    private final CategoriesRepository categoriesRepository;

    public ResponseEntity<ApiResponse<Categories>> createCategories(Categories categories) {
        try {
            // Guardar la categoria en la base de datos
            Categories newCategories = categoriesRepository.save(categories);
            // crear objecto apiresponse con la categoria creada
            ApiResponse<Categories> response = new ApiResponse<>(newCategories, "success", "Categoria creada correctamente");
            //devolver responseentity con el apiresponse
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            //manejar cualquier excepción que pueda ocurrir
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(null, "error", "error al crear la categoria " + e.getMessage()));
        }
    }
}
