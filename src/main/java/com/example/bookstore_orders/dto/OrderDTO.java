package com.example.bookstore_orders.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class OrderDTO {
    private Long userId;
    private Long bookId;
    private int orderQuantity;
    private int orderPrice;
    private String address;
    private boolean cancel;
}
