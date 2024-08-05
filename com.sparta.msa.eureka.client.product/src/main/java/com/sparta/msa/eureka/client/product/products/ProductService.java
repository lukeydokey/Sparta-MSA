package com.sparta.msa.eureka.client.product.products;

import com.sparta.msa.eureka.client.product.core.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ProductResDto createProduct(ProductReqDto requestDto, String userId) {
        Product product = requestDto.toEntity();
        product.setCreated(userId);
        return productRepository.save(product).toResDto();
    }

    @Transactional
    public ProductResDto updateProduct(Long productId,ProductReqDto requestDto, String userId) {
        Product product = productRepository.findById(productId)
                .filter(p -> p.getDeletedAt() == null)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found or has been deleted"));

        product.updateProduct(requestDto.getName(), requestDto.getDescription(),
                requestDto.getPrice(), requestDto.getQuantity(), userId);
        return product.toResDto();
    }

    @Transactional
    public void deleteProduct(Long productId, String userId) {
        Product product = productRepository.findById(productId)
                .filter(p -> p.getDeletedAt() == null)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found or has been deleted"));
        product.setDeleted(userId);
    }

    public ProductResDto getProductById(Long productId){
        Product product = productRepository.findById(productId)
                .filter(p -> p.getDeletedAt() == null)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found or has been deleted"));
        return product.toResDto();
    }

    @Transactional
    public void reduceQuantity(Long productId, Integer quantity, String userId) {
        Product product = productRepository.findById(productId)
                .filter(p -> p.getDeletedAt() == null)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found or has been deleted"));

        if(quantity > product.getQuantity()){
            throw new IllegalArgumentException("Not Enough Product");
        }

        product.updateProduct(product.getName(), product.getDescription(),
                product.getPrice(), product.getQuantity() - quantity, userId);
    }
}
