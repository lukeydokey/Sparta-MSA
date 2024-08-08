package com.sparta.msa_exam.product.products;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResDto implements Serializable {
    private Long id;
    private String name;
    private String description;
    private Integer price;
    private Integer quantity;
}
