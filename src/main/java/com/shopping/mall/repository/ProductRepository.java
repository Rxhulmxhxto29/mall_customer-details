package com.shopping.mall.repository;

import com.shopping.mall.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryId(Long categoryId);
    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findByStatus(Product.ProductStatus status);
    
    @Query("SELECT p FROM Product p WHERE p.stockQuantity > 0 AND p.status = 'AVAILABLE'")
    List<Product> findAvailableProducts();
}
