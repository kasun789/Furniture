package com.falcon.furniture.furniture.dto;

import lombok.Data;

@Data
public class PaymentRequestDto {
    private String token;
    private Long amount;
    private String orderId;
}
