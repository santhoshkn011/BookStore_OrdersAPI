package com.example.bookstore_orders.model;

import com.example.bookstore_orders.dto.OrderDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@ToString
public class BookOrders {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "orderId", nullable = false)
    private Long orderId;
    private Long userId;
    private Long bookId;
    int orderQuantity;
    private double orderPrice;
    private String address;
    private LocalDate orderDate;
    private boolean cancel;

    public BookOrders(Long userId, Long bookId, int orderQuantity, double orderPrice, String address, LocalDate orderDate, boolean cancel) {
        this.userId = userId;
        this.bookId = bookId;
        this.orderQuantity = orderQuantity;
        this.orderPrice = orderPrice;
        this.address = address;
        this.orderDate = orderDate;
        this.cancel = cancel;
    }
}
