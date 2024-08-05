package com.sparta.msa.eureka.client.order.core.domain;

import com.sparta.msa.eureka.client.order.core.enums.OrderStatus;
import com.sparta.msa.eureka.client.order.orders.OrderResDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
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

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ElementCollection
    @CollectionTable(name = "order_items", joinColumns = @JoinColumn(name = "order_id"))
    @Column(name = "order_item_id")
    private List<Long> orderItemIds;

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

    public void updateOrder(List<Long> orderItemIds, OrderStatus status, String userId) {
        this.orderItemIds = orderItemIds;
        this.status = status;
        this.updatedBy = userId;
    }

    public OrderResDto toResDto(){
        return new OrderResDto(
                this.id,
                this.orderItemIds,
                this.status.toString()
        );
    }
}
