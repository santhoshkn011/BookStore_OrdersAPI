package com.example.bookstore_orders.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderDTO {
    Long userId;
    Long bookId;
    int orderQuantity;
}
