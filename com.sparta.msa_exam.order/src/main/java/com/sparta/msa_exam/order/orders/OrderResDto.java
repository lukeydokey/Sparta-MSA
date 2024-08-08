package com.sparta.msa_exam.order.orders;

import com.sparta.msa_exam.order.core.domain.OrderProduct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResDto {
    private Long orderId;
    private String name;
    private List<Long> orderProductsIds;
    private String status;
}
