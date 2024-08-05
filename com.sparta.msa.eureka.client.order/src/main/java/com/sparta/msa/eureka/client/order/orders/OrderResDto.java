package com.sparta.msa.eureka.client.order.orders;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResDto {
    private Long orderId;
    private List<Long> orderItemIds;
    private String status;
}
