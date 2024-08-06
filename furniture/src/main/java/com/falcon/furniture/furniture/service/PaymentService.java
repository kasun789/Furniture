package com.falcon.furniture.furniture.service;

import com.falcon.furniture.furniture.dto.PaymentDto;
import com.falcon.furniture.furniture.dto.PaymentRequestDto;

public interface PaymentService {
    PaymentDto addPayment(PaymentRequestDto paymentInfoDto);
}
