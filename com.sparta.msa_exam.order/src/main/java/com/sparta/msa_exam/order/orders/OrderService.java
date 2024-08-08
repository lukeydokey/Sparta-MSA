package com.sparta.msa_exam.order.orders;

import com.sparta.msa_exam.order.core.clients.ProductClient;
import com.sparta.msa_exam.order.core.clients.ProductResDto;
import com.sparta.msa_exam.order.core.domain.Order;
import com.sparta.msa_exam.order.core.domain.OrderProduct;
import com.sparta.msa_exam.order.core.enums.OrderStatus;
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
        for( Long productId : requestDto.getOrderProductIds()){
            ProductResDto product = productClient.getProductById(productId);
            if(product == null || product.getQuantity() < 1){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product is sold out");
            }
        }

        for(Long productId : requestDto.getOrderProductIds()){
            productClient.updateQuantity(productId,-1, userId);
        }

        Order order = requestDto.toEntity();
        for(Long productId : requestDto.getOrderProductIds()){
            order.addOrderProducts(OrderProduct.builder().productId(productId).build());
        }
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

        // 주문 상품 리스트를 새로 업뎃 예정이기 때문에 수량 다시 +1
        for(OrderProduct product : order.getOrderProducts()){
            productClient.updateQuantity(product.getProductId(),1, userId);
        }

        // 업데이트 된 주문 상품 리스트 수량 -1
        for(Long productId : requestDto.getOrderProductIds()){
            productClient.updateQuantity(productId,-1, userId);
        }

        order.updateOrder(requestDto.getName(), requestDto.getOrderProductIds(), OrderStatus.valueOf(requestDto.getStatus()) ,userId);
        return order.toResDto();
    }

    @Transactional
    public void deleteOrder(Long orderId, String userId) {
        Order order = orderRepository.findById(orderId)
                .filter(o -> o.getDeletedAt() == null)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found or has been deleted"));

        // 주문 취소 때문에 수량 다시 +1
        for(OrderProduct product : order.getOrderProducts()){
            productClient.updateQuantity(product.getProductId(),1, userId);
        }

        order.setDeleted(userId);
    }

}
