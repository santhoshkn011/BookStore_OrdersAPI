package com.example.bookstore_orders.dto;

import java.util.List;
import java.util.Optional;

public class ResponseDTO {
    String message;
    Object response;
    public ResponseDTO(String message, String response) {
        this.message = message;
        this.response = response;
    }
    public ResponseDTO(String message, Object response) {
        this.message = message;
        this.response = response;
    }
}
