package com.sparta.msa_exam.product.products;

import com.sparta.msa_exam.product.core.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
