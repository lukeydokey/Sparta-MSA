package com.sparta.msa.eureka.client.product.products;

import com.sparta.msa.eureka.client.product.core.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
