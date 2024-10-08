package com.sparta.msa_exam.product.core.domain;

import com.sparta.msa_exam.product.products.ProductResDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Integer price;
    private Integer quantity;

    @CreatedDate
    private LocalDateTime createdAt;
    private String createdBy;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    private String updatedBy;

    private LocalDateTime deletedAt;
    private String deletedBy;

    public void setCreated(String userId){
        this.createdBy = userId;
        this.updatedBy = userId; // LastModifiedDate는 생성시에도 값이 입력 됨
    }

    public void setDeleted(String userId){
        this.deletedBy = userId;
        this.deletedAt = LocalDateTime.now();
    }

    public void updateProduct(String name, String description, Integer price, Integer quantity, String userId){
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.updatedBy = userId;
    }

    public ProductResDto toResDto(){
        return new ProductResDto(
                this.id,
                this.name,
                this.description,
                this.price,
                this.quantity
        );
    }

}
