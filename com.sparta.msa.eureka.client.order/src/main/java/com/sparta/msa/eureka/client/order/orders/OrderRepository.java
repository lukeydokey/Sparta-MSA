package com.sparta.msa.eureka.client.order.orders;

import com.sparta.msa.eureka.client.order.core.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
