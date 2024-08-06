package com.sparta.msa_exam.order.orders;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @Value("${server.port}")
    private int serverPort;

    /*
    주문 등록
    path /api/orders
    Request
    header X-User-Id : userId
    body OrderReqDto

    Response
    OrderResDto
     */
    @PostMapping
    public ResponseEntity<OrderResDto> createProduct(@RequestBody OrderReqDto reqDto,
                                                       @RequestHeader(value = "X-User-Id") String userId) {
        OrderResDto res = orderService.createOrder(reqDto, userId);
        return ResponseEntity.status(HttpStatus.OK)
                .header("Server-Port", String.valueOf(serverPort))
                .body(res);
    }

    /*
    주문 단건 조회
    Request
    Path /api/orders/{orderId}

    Response
    OrderResDto
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResDto> getProductById(@PathVariable Long orderId) {
        OrderResDto res = orderService.getOrderById(orderId);
        return ResponseEntity.status(HttpStatus.OK)
                .header("Server-Port", String.valueOf(serverPort))
                .body(res);
    }

    /*
    주문 정보 업데이트
    path /api/orders/{orderId}
    Request
    header X-User-Id : userId
    body OrderReqDto

    Response
    OrderResDto
     */
    @PutMapping("/{orderId}")
    public ResponseEntity<OrderResDto> updateOrder(@PathVariable Long orderId,
                                                       @RequestBody OrderReqDto reqDto, @RequestHeader(value = "X-User-Id") String userId) {
        OrderResDto res = orderService.updateOrder(orderId, reqDto, userId);
        return ResponseEntity.status(HttpStatus.OK)
                .header("Server-Port", String.valueOf(serverPort))
                .body(res);
    }

    /*
    주문 삭제
    path /api/orders/{orderId}

    Request
    header X-User-Id : userId

    Response
    message
     */
    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long orderId,
                                                @RequestHeader(value = "X-User-Id") String userId) {
        orderService.deleteOrder(orderId, userId);
        return ResponseEntity.status(HttpStatus.OK)
                .header("Server-Port", String.valueOf(serverPort))
                .body("Deleted order successfully");
    }
}
