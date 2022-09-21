package com.example.bookstore_orders.repository;

import com.example.bookstore_orders.model.BookOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookOrdersRepo extends JpaRepository<BookOrders, Long> {
    @Query(value = "SELECT * FROM book_orders WHERE user_id=:userId", nativeQuery = true)
    List<BookOrders> getOrderListWithUserId(Long userId);
}
