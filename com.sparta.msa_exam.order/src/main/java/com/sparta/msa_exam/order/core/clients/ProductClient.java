package com.sparta.msa_exam.order.core.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "product-service")
public interface ProductClient {
    @GetMapping("/api/products/{productId}")
    ProductResDto getProductById(@PathVariable("productId") Long productId);

    @PutMapping("/api/products/{productId}/reduceQuantity")
    void reduceQuantity(@PathVariable Long productId,
                        @RequestParam Integer quantity,
                        @RequestHeader(value = "X-User-Id") String userId);
}
