package com.api.products.repository;

import com.api.products.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Integer> {
    @Query(value = "SELECT * FROM productsdb.products p \n" +
            "WHERE \n" +
            "    (p.category_id = ?1 OR ?1 IS NULL) AND \n" +
            "    (p.price = ?2 OR ?2 IS NULL) AND \n" +
            "    (p.quantity <= ?3 OR ?3 IS NULL)\n" +
            "LIMIT 0, 1000;", nativeQuery = true)
    List<Products> findProductsByFilters(
            Integer categoryId, Double price, Integer quantity);
}
