# 스파르타 Java 단기 심화 과정 MSA 실습 프로젝트

# API 목록

---

## 회원

- /api/auth/signUp (POST)
  ```json
    {
        "userId": "tester",
        "userName" : "테스터",
        "password" : "1q2w3e4r"
    }
    ```
- /api/auth/signIn (POST)
    ```json
    {
        "userId": "tester",
        "password" : "1q2w3e4r"
    }
    ```

---

## 상품

### Request Header ( Authorization: Bearer {Sigin In으로 얻은 accessToken})

- /api/products (POST)
  ```json
    {
        "name" : "상품",
        "description" : "상품 설명",
        "price" : 10000,
        "quantity" : 5
    }
    ```
- /api/products (GET)
- /api/products/{productId} (GET)
- /api/products/{productId} (PUT)
  ```json
    {
        "name" : "상품",
        "description" : "상품 설명",
        "price" : 10000,
        "quantity" : 5
    }
    ```
- /api/products/{productId} (DELETE)
- /api/products/{productId}/updateQuantity?quantity={quantity} (PUT)
  - Request Parameter : quantity
  - Request Body
    ```json
      {
          "name" : "상품",
          "description" : "상품 설명",
          "price" : 10000,
          "quantity" : 5
      }
      ```

---

# 주문

### Request Header ( Authorization: Bearer {Sigin In으로 얻은 accessToken})

- /api/orders (POST)
  ```json
    {
        "name" : "주문2",
        "orderProductIds" : [1,2],
        "status" : "CREATED"
    }
    ```
- /api/orders/{orderId} (GET)
- /api/orders/{orderId} (PUT)
  ```json
    // CREATED, PAID, SHIPPED, COMPLETED, CANCELLED
    {
        "name" : "주문1",
        "orderProductIds" : [1,2],
        "status" : "CREATED"
    }
    ```
- /api/orders/{orderId} (DELETE)
