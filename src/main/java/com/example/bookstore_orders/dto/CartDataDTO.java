package com.example.bookstore_orders.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartDataDTO {
    Long cartId;
    Long userId;
    Long bookId;
    int quantity;
}
