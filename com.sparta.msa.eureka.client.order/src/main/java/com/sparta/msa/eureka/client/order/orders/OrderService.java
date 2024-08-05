package com.sparta.msa.eureka.client.order.orders;

import com.sparta.msa.eureka.client.order.core.clients.ProductClient;
import com.sparta.msa.eureka.client.order.core.clients.ProductResDto;
import com.sparta.msa.eureka.client.order.core.domain.Order;
import com.sparta.msa.eureka.client.order.core.enums.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;

    @Transactional
    public OrderResDto createOrder(OrderReqDto requestDto, String userId){
        for( Long productId : requestDto.getOrderItemIds()){
            ProductResDto product = productClient.getProductById(productId);
            if(product == null || product.getQuantity() < 1){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product is sold out");
            }
        }

        for(Long productId : requestDto.getOrderItemIds()){
            productClient.reduceQuantity(productId,1, userId);
        }

        Order order = requestDto.toEntity();
        order.setCreated(userId);
        return orderRepository.save(order).toResDto();
    }

    public OrderResDto getOrderById(Long orderId){
        Order order = orderRepository.findById(orderId)
                .filter(o -> o.getDeletedAt() == null)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found or has been deleted"));
        return order.toResDto();
    }

    @Transactional
    public OrderResDto updateOrder(Long orderId, OrderReqDto requestDto, String userId) {
        Order order = orderRepository.findById(orderId)
                .filter(o -> o.getDeletedAt() == null)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found or has been deleted"));

        order.updateOrder(requestDto.getOrderItemIds(), OrderStatus.valueOf(requestDto.getStatus()) ,userId);
        return order.toResDto();
    }

    @Transactional
    public void deleteOrder(Long orderId, String userId) {
        Order order = orderRepository.findById(orderId)
                .filter(o -> o.getDeletedAt() == null)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found or has been deleted"));
        order.setDeleted(userId);
    }

}