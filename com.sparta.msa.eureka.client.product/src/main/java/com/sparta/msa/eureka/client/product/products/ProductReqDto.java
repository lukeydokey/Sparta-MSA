package com.sparta.msa.eureka.client.product.products;

import com.sparta.msa.eureka.client.product.core.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductReqDto {
    private String name;
    private String description;
    private Integer price;
    private Integer quantity;

    public Product toEntity(){
        return Product.builder()
                .name(this.name)
                .description(this.description)
                .price(this.price)
                .quantity(this.quantity).build();
    }
}
