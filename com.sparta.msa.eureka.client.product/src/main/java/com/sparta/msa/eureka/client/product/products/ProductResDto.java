package com.sparta.msa.eureka.client.product.products;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResDto {
    private Long id;
    private String name;
    private String description;
    private Integer price;
    private Integer quantity;
}
