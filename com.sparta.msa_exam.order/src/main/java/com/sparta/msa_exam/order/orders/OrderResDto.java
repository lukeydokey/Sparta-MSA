package com.sparta.msa_exam.order.orders;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResDto implements Serializable {
    private Long orderId;
    private String name;
    private List<Long> orderProductsIds;
    private String status;
}
