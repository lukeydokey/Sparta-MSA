package com.sparta.msa_exam.order.orders;

import com.sparta.msa_exam.order.core.domain.Order;
import com.sparta.msa_exam.order.core.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderReqDto {
    private List<Long> orderItemIds;
    private String status;

    public Order toEntity(){
        return Order.builder()
                .orderItemIds(this.orderItemIds)
                .status(OrderStatus.valueOf(this.status))
                .build();
    }
}
