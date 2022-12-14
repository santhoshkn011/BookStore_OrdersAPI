package com.example.bookstore_orders.controller;

import com.example.bookstore_orders.dto.OrderDTO;
import com.example.bookstore_orders.dto.ResponseDTO;
import com.example.bookstore_orders.model.BookOrders;
import com.example.bookstore_orders.service.IBookOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class BookOrdersController {
    @Autowired
    IBookOrderService bookOrderService;
    //Add Order Details and send email confirmation
    @PostMapping("/insert")
    public ResponseEntity<ResponseDTO> addOrderDetails(@RequestBody OrderDTO orderDTO){
        String response = bookOrderService.addOrderDetails(orderDTO);
        ResponseDTO responseDTO = new ResponseDTO("Order Details Added and e-mail sent", response);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    //Get Order Data by Token
    @GetMapping("/orderDataByToken/{token}")
    public ResponseEntity<ResponseDTO> getOrderDataByToken(@PathVariable String token){
        List<BookOrders> ordersList = bookOrderService.getOrderDetailsByToken(token);
        ResponseDTO responseDTO = new ResponseDTO("Order Details with Token: "+token, ordersList);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    //Get Order Details by Order ID
    @GetMapping("/orderDetails/{orderId}")
    public ResponseEntity<ResponseDTO> getOrderDataByOrderId(@PathVariable Long orderId){
        BookOrders orderDetails = bookOrderService.getOrderDetailsByOrderId(orderId);
        ResponseDTO responseDTO = new ResponseDTO("Order Details with Order ID: "+orderId, orderDetails);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    //Get Order Details by Order ID (MicroService)
    @GetMapping("/orderData/{orderId}")
    public BookOrders findOrderDataByOrderId(@PathVariable Long orderId){
        BookOrders orderDetails = bookOrderService.findOrderDetailsByOrderId(orderId);
        return orderDetails;
    }
    //Update Order Details(Book and Quantity) By OrderID
    @PutMapping("/update/{orderId}")
    public ResponseEntity<ResponseDTO> updateOrderById(@PathVariable Long orderId, @RequestBody OrderDTO orderDTO){
        String response = bookOrderService.editOrderByOrderId(orderId, orderDTO);
        ResponseDTO responseDTO = new ResponseDTO("Updated Order Details with Order ID: "+orderId, response);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    //Delete Order Id by User Id and Order Id
    @DeleteMapping("/delete/{userId}/{orderId}")
    public ResponseEntity<ResponseDTO> updateDeleteById(@PathVariable Long userId,@PathVariable Long orderId){
        String response = bookOrderService.deleteOrderByOrderId(userId, orderId);
        ResponseDTO responseDTO = new ResponseDTO("Status of order Id: "+orderId, response);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
