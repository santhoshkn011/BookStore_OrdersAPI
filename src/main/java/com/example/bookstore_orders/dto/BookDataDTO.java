package com.example.bookstore_orders.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookDataDTO {
    Long BookId;
    String bookName;
    String authorName;
    String bookDescription;
    String bookImage;
    int price;
    int quantity;
}
