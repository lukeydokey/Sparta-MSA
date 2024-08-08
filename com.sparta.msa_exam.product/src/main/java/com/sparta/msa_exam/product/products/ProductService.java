package com.sparta.msa_exam.product.products;

import com.sparta.msa_exam.product.core.domain.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "Product Service")
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

    public List<ProductResDto> getAllProducts(){
        try{
            return productRepository.findAll()
                    .stream().filter(p -> p.getDeletedAt() == null).map(Product::toResDto).toList();
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
        }
    }

    public ProductResDto getProductById(Long productId){
        Product product = productRepository.findById(productId)
                .filter(p -> p.getDeletedAt() == null)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found or has been deleted"));
        return product.toResDto();
    }

    @Transactional
    public void updateQuantity(Long productId, Integer quantity, String userId) {
        Product product = productRepository.findById(productId)
                .filter(p -> p.getDeletedAt() == null)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found or has been deleted"));

        if(product.getQuantity() + quantity < 0){
            throw new IllegalArgumentException("Not Enough Product");
        }

        product.updateProduct(product.getName(), product.getDescription(),
                product.getPrice(), product.getQuantity() + quantity, userId);
    }
}
