package com.sparta.msa_exam.order.orders;

import com.sparta.msa_exam.order.core.domain.Order;
import com.sparta.msa_exam.order.core.domain.OrderProduct;
import com.sparta.msa_exam.order.core.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderReqDto {
    private String name;
    private List<Long> orderProductIds;
    private String status;

    public Order toEntity(){
        return Order.builder()
                .name(this.name)
                .orderProducts(new ArrayList<>())
                .status(OrderStatus.valueOf(this.status))
                .build();
    }
}
