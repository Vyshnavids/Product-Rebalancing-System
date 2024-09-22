package com.ProductreBalancing.ProductRebalancing.Repository;

import com.ProductreBalancing.ProductRebalancing.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

