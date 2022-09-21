package com.example.bookstore_orders.service;

import com.example.bookstore_orders.dto.BookDataDTO;
import com.example.bookstore_orders.dto.CartDataDTO;
import com.example.bookstore_orders.dto.OrderDTO;
import com.example.bookstore_orders.dto.UserDataDTO;
import com.example.bookstore_orders.exception.OrderException;
import com.example.bookstore_orders.model.BookOrders;
import com.example.bookstore_orders.repository.BookOrdersRepo;
import com.example.bookstore_orders.utility.EmailSenderService;
import com.example.bookstore_orders.utility.TokenUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BookOrderService implements IBookOrderService{
    private String USER_URI = "http://localhost:9090/user/Id/";
    private String USER_Token_URI = "http://localhost:9090/user/GetUser/";
    private String BOOK_URI = "http://localhost:9091/book/Id/";
    private String ORDER_URI = "http://localhost:9093/orders/orderData/";
    private String CART_URI = "http://localhost:9092/cart/userCart/";
    @Autowired
    TokenUtility tokenUtility;
    @Autowired
    EmailSenderService emailSender;
    @Autowired
    BookOrdersRepo bookOrdersRepo;
    @Autowired
    private RestTemplate restTemplate;
    @Override
    public String addOrderDetails(OrderDTO orderDTO) {
        ResponseEntity<UserDataDTO> userDetails = restTemplate.getForEntity(USER_URI + orderDTO.getUserId(), UserDataDTO.class);
        ResponseEntity<BookDataDTO> bookDetails = restTemplate.getForEntity(BOOK_URI + orderDTO.getBookId(), BookDataDTO.class);
        if (userDetails.hasBody() && bookDetails.hasBody()) {
            if (orderDTO.getOrderQuantity() <= bookDetails.getBody().getQuantity()) {
                //Calculations
                double orderPrice = bookDetails.getBody().getPrice() * orderDTO.getOrderQuantity();
                String address = userDetails.getBody().getAddress();
                LocalDate orderDate = LocalDate.now();
                boolean cancel = false;
                BookOrders orderDetails = new BookOrders(orderDTO.getUserId(), orderDTO.getBookId(), orderDTO.getOrderQuantity(), orderPrice, address, orderDate, cancel);
                bookOrdersRepo.save(orderDetails);
                //Token generated
                String token = tokenUtility.createToken(userDetails.getBody().getUserId());
                //sending email
                emailSender.sendEmail(userDetails.getBody().getEmailAddress(), "Order Placed!!!", "Please Click on the below link for the order details." + "\n" + "http://localhost:9093/orders/orderDataByToken/" + token);
                return token;
            } else
                throw new OrderException("Quantity Exceeds, Available Book Quantity: " + bookDetails.getBody().getQuantity());
        } else
            throw new OrderException("Invalid User ID | Book ID");
    }

    @Override
    public List<BookOrders> getOrderDetailsByToken(String token) {
        ResponseEntity<UserDataDTO> userDetails = restTemplate.getForEntity(USER_Token_URI+token, UserDataDTO.class);
        if(userDetails.hasBody()){
            List<BookOrders> ordersList = bookOrdersRepo.getOrderListWithUserId(userDetails.getBody().getUserId());
            return ordersList;
        } else
            throw new OrderException("Invalid User Id");
    }
    //Get order details by order ID
    @Override
    public BookOrders getOrderDetailsByOrderId(Long orderId) {
        Optional<BookOrders> orderDetails = bookOrdersRepo.findById(orderId);
        if(orderDetails.isPresent()){
            return orderDetails.get();
        }else
            throw new OrderException("Invalid Order ID");
    }
    //Get order details by order ID (MicroServices)
    @Override
    public BookOrders findOrderDetailsByOrderId(Long orderId) {
        Optional<BookOrders> orderDetails = bookOrdersRepo.findById(orderId);
        if(orderDetails.isPresent()){
            return orderDetails.get();
        }else
            return null;
    }

    @Override
    public String editOrderByOrderId(Long orderId, OrderDTO orderDTO) {
        ResponseEntity<UserDataDTO> userDetails = restTemplate.getForEntity(USER_URI + orderDTO.getUserId(), UserDataDTO.class);
        ResponseEntity<BookDataDTO> bookDetails = restTemplate.getForEntity(BOOK_URI + orderDTO.getBookId(), BookDataDTO.class);
        ResponseEntity<BookOrders> orderDetails = restTemplate.getForEntity(ORDER_URI + orderId, BookOrders.class);
        if(userDetails.hasBody() && bookDetails.hasBody() && orderDetails.hasBody()){
            if(orderDetails.getBody().getUserId().equals(userDetails.getBody().getUserId())){
                if(orderDTO.getOrderQuantity()<=bookDetails.getBody().getQuantity()){
                    orderDetails.getBody().setBookId(orderDTO.getBookId());
                    orderDetails.getBody().setOrderQuantity(orderDTO.getOrderQuantity());
                    double orderPrice = bookDetails.getBody().getPrice()*orderDTO.getOrderQuantity();
                    orderDetails.getBody().setOrderPrice(orderPrice);
                    bookOrdersRepo.save(orderDetails.getBody());
                    String token = tokenUtility.createToken(userDetails.getBody().getUserId());
                    //sending email
                    emailSender.sendEmail(userDetails.getBody().getEmailAddress(), "Updated Order Details!!!", "Please Click on the below link for the Updated order details."+"\n"+"http://localhost:9093/orders/orderDetails/"+orderId);
                    return "Order Details Updated! with Book ID: "+orderDTO.getBookId()+", Quantity: "+orderDTO.getOrderQuantity();
                }else
                    throw new OrderException("Quantity Exceeds, Available Book Quantity: "+bookDetails.getBody().getQuantity());
            }else
                throw new OrderException("Order ID is not present in this User ID: "+userDetails.getBody().getUserId());
        }else
            throw new OrderException("User ID | order ID | Book ID is invalid");
    }
    //Delete Order Id by User Id and Order Id
    @Override
    public String deleteOrderByOrderId(Long userId, Long orderId) {
        ResponseEntity<UserDataDTO> userDetails = restTemplate.getForEntity(USER_URI + userId, UserDataDTO.class);
        ResponseEntity<BookOrders> orderDetails = restTemplate.getForEntity(ORDER_URI + orderId, BookOrders.class);
        if (userDetails.hasBody() && orderDetails.hasBody()) {
            if (orderDetails.getBody().getUserId().equals(userDetails.getBody().getUserId())) {
                bookOrdersRepo.deleteById(orderId);
                //sending email
                emailSender.sendEmail(userDetails.getBody().getEmailAddress(), "Order Deleted!!!", "Your Order ID: " + orderId + ", has been deleted successfully from the Book Store Application!!");
                return "Data Deleted successfully and e-mail sent to the user!";
            } else
                throw new OrderException("Cannot find order ID: " + orderId + ", for the User ID: " + userId);
        } else
            throw new OrderException("Invalid Order ID | User Id");
    }
}
