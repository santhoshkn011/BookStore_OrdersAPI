package com.example.bookstore_orders.service;

import com.example.bookstore_orders.dto.OrderDTO;
import com.example.bookstore_orders.exception.OrderException;
import com.example.bookstore_orders.model.BookOrders;
import com.example.bookstore_orders.repository.BookOrdersRepo;
import com.example.bookstore_orders.utility.EmailSenderService;
import com.example.bookstore_orders.utility.TokenUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
public class BookOrderService implements IBookOrderService{
    @Autowired
    TokenUtility tokenUtility;
    @Autowired
    EmailSenderService emailSender;
    @Autowired
    BookOrdersRepo bookOrdersRepo;
    @Override
    public String addOrderDetails(OrderDTO orderDTO) {
//        Optional<UserDetails> userDetails = userRepo.findById(orderDTO.getUserId());
//        List<Cart> cartList = cartRepo.getCartListWithUserId(userDetails.get().getUserId());
//        Optional<Book> bookDetails = bookRepo.findById(orderDTO.getBookId());
//        if(userDetails.isPresent() && bookDetails.isPresent()){
//            if(orderDTO.getOrderQuantity()<=bookDetails.get().getQuantity()){
                //Calculations
//                double orderPrice = bookDetails.get().getPrice()*orderDTO.getOrderQuantity();
//                String address = userDetails.get().getAddress();
                LocalDate orderDate = LocalDate.now();
                BookOrders orderDetails = new BookOrders(orderDTO.getUserId(), orderDTO.getBookId(), orderDTO.getOrderQuantity(), orderDTO.getOrderPrice(), orderDTO.getAddress(),orderDate, orderDTO.isCancel());
                //Token Generated
//                String token = tokenUtility.createToken(userDetails.get().getUserId());
                bookOrdersRepo.save(orderDetails);
                //sending email
//                emailSender.sendEmail(userDetails.get().getEmailAddress(), "Order Placed!!!", "Please Click on the below link for the order details."+"\n"+"http://localhost:9090/orders/orderDataByToken/"+token);
//                return token;
        return "Order Placed Successfully";
//            }else
//                throw new OrderException("Quantity Exceeds, Available Book Quantity: "+bookDetails.get().getQuantity());
//        }else
//            throw new OrderException("Invalid User ID | Book ID");
    }

    @Override
    public List<BookOrders> getOrderDetailsByToken(String token) {
        Long userId = tokenUtility.decodeToken(token);
        List<BookOrders> ordersList = bookOrdersRepo.getOrderListWithUserId(userId);
        if(ordersList.isEmpty()){
            throw new OrderException("Order List is Empty!");
        }else
            return ordersList;
    }

    @Override
    public String editOrderByOrderId(Long orderId, OrderDTO orderDTO) {
//        Optional<UserDetails> userData = userRepo.findById(orderDTO.getUserId());
//        Optional<Orders> orderDetails = orderRepo.findById(orderId);
//        Optional<Book> bookDetails = bookRepo.findById(orderDTO.getBookId());
//        if(orderDetails.isPresent() && bookDetails.isPresent() && userData.get().equals(orderDetails.get().getUser())){
//            if(orderDTO.getOrderQuantity()<=bookDetails.get().getQuantity()){
//                orderDetails.get().setBook(bookDetails.get());
//                orderDetails.get().setOrderQuantity(orderDTO.getOrderQuantity());
//                double orderPrice = bookDetails.get().getPrice()*orderDTO.getOrderQuantity();
//                orderDetails.get().setOrderPrice(orderPrice);
//                orderRepo.save(orderDetails.get());
//                String token = tokenUtility.createToken(orderDetails.get().getUser().getUserId());
                //sending email
//                emailSender.sendEmail(orderDetails.get().getUser().getEmailAddress(), "Updated Order Details!!!", "Please Click on the below link for the Updated order details."+"\n"+"http://localhost:9090/orders/orderDataByToken/"+token);
                return "Order Details Updated! with Book ID: "+orderDTO.getBookId()+", Quantity: "+orderDTO.getOrderQuantity();
//            }else
//                throw new OrderException("Quantity Exceeds, Available Book Quantity: "+bookDetails.get().getQuantity());
//        }else
//            throw new OrderException("User ID | order ID | Book ID is invalid");
    }

    @Override
    public String deleteOrderByOrderId(Long userId, Long orderId) {
//        Optional<UserDetails> userData = userRepo.findById(userId);
//        Optional<Orders> orderDetails = orderRepo.findById(orderId);
//        if(orderDetails.isPresent() && userData.get().equals(orderDetails.get().getUser())){
//            orderRepo.deleteByOrderId(orderId);
            //sending email
//            emailSender.sendEmail(orderDetails.get().getUser().getEmailAddress(), "Order Deleted!!!", "Your Order has been deleted successfully from the Book Store Application!!");
            return "Data Deleted successfully and e-mail sent to the user!";
//        }else
//            throw new OrderException("Invalid Order ID | User ID");
    }
}
