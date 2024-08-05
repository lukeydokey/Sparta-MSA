package com.sparta.msa.eureka.client.product.products;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /*
    상품 등록
    path /api/products
    Request
    header X-User-Id : userId
    body ProductReqDto

    Response
    ProductResDto
     */
    @PostMapping
    public ResponseEntity<ProductResDto> createProduct(@RequestBody ProductReqDto reqDto,
                                                       @RequestHeader(value = "X-User-Id") String userId) {
        ProductResDto res = productService.createProduct(reqDto, userId);
        return ResponseEntity.ok(res);
    }

    /*
    상품 단건 조회
    Request
    Path /api/products/{productId}

    Response
    ProductResDto
     */
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResDto> getProductById(@PathVariable Long productId) {
        ProductResDto res = productService.getProductById(productId);
        return ResponseEntity.ok(res);
    }

    /*
    상품 정보 업데이트
    path /api/products/{productId}
    Request
    header X-User-Id : userId
    body ProductReqDto

    Response
    ProductResDto
     */
    @PutMapping("/{productId}")
    public ResponseEntity<ProductResDto> updateProduct(@PathVariable Long productId,
                                                       @RequestBody ProductReqDto reqDto, @RequestHeader(value = "X-User-Id") String userId) {
        ProductResDto res = productService.updateProduct(productId, reqDto, userId);
        return ResponseEntity.ok(res);
    }

    /*
    상품 삭제
    path /api/products/{productId}

    Request
    header X-User-Id : userId

    Response
    message
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId,
                                                @RequestHeader(value = "X-User-Id") String userId) {
        productService.deleteProduct(productId, userId);
        return ResponseEntity.ok("Deleted product successfully");
    }

    /*
    상품 삭제
    path /api/products/{productId}/reduceQuantity

    Request
    header X-User-Id : userId
    param quantity

    Response
    message
     */
    @PutMapping("/{productId}/reduceQuantity")
    public ResponseEntity<String> reduceQuantity(@PathVariable Long productId,
                                                 @RequestParam Integer quantity,
                                                 @RequestHeader(value = "X-User-Id") String userId) {
        productService.reduceQuantity(productId, quantity, userId);
        return ResponseEntity.ok("Reduced quantity of product successfully");
    }
}
