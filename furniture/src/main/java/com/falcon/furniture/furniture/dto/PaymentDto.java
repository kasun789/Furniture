package com.falcon.furniture.furniture.dto;

import com.falcon.furniture.furniture.model.Order;
import com.falcon.furniture.furniture.model.Payment;
import lombok.Data;

@Data
public class PaymentDto {
    Payment payment;
    String errorMessage;
}
