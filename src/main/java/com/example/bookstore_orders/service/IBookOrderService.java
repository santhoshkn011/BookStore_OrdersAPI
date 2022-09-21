package com.example.bookstore_orders.service;

import com.example.bookstore_orders.dto.OrderDTO;
import com.example.bookstore_orders.model.BookOrders;

import java.util.List;

public interface IBookOrderService {
    String addOrderDetails(OrderDTO orderDTO);

    List<BookOrders> getOrderDetailsByToken(String token);

    String editOrderByOrderId(Long orderId, OrderDTO orderDTO);

    String deleteOrderByOrderId(Long userId, Long orderId);

    BookOrders getOrderDetailsByOrderId(Long orderId);

    BookOrders findOrderDetailsByOrderId(Long orderId);
}
