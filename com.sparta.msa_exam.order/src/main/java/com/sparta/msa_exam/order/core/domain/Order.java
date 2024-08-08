package com.sparta.msa_exam.order.core.domain;

import com.sparta.msa_exam.order.core.enums.OrderStatus;
import com.sparta.msa_exam.order.orders.OrderResDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

//    @ElementCollection
//    @CollectionTable(name = "order_items", joinColumns = @JoinColumn(name = "order_id"))
//    @Column(name = "order_item_id")
//    private List<Long> orderItemIds;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProduct> orderProducts = new ArrayList<>();

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

    public void addOrderProducts(OrderProduct orderProduct){
        orderProducts.add(orderProduct);
        orderProduct.setOrder(this);
    }

    public void updateOrder(String name, List<Long> orderProductIds, OrderStatus status, String userId) {
        this.name = name;
        this.orderProducts.clear();
        for(Long productId : orderProductIds){
            this.addOrderProducts(OrderProduct.builder().productId(productId).build());
        }
        this.status = status;
        this.updatedBy = userId;
    }

    public OrderResDto toResDto(){
        return new OrderResDto(
                this.id,
                this.name,
                this.orderProducts.stream().map(OrderProduct::getProductId).toList(),
                this.status.toString()
        );
    }
}
