package com.pintoo.ems.Repository;

import com.pintoo.ems.Entity.Product;
import com.pintoo.ems.Response.TrendResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {


    Optional<List<Product>> findProductById(Long productId);
    Optional<List<Product>> findProductsByCategoryId(Long categoryId);




}
