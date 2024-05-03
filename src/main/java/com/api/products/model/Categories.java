package com.api.products.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Entity
@Data
public class Categories {
    @Id
    @GeneratedValue
    private Integer category_id;
    @Size(max = 20, message = "El nombre no puede contener más de 20 caracteres")
    private String name;
}

