package com.example.bookstore_orders.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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
