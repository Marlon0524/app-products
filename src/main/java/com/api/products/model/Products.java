package com.api.products.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class Products {
    @Id
    @GeneratedValue
    private Integer product_id;
    @Size(max = 30, message = "El nombre no puede tener más de 30 caracteres")
    private String name;
    @Size(max = 100, message = "La descripción no puede tener más de 100 caracteres")
    private String description;
    private Double price;
    private Integer quantity;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Categories categories;
}
