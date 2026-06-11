package com.example.product.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.product.model.Product;

@Repository
public interface ProductRepo extends  JpaRepository<Product, Integer>{
@Query(value = "SELECT * FROM product WHERE product_id = ?1", nativeQuery = true)
    Product getProductById(Integer productId);
}
