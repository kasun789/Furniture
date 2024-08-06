package com.falcon.furniture.furniture.dao;

import com.falcon.furniture.furniture.dto.PaymentDto;
import com.falcon.furniture.furniture.dto.PaymentRequestDto;
import org.springframework.http.ResponseEntity;

public interface PaymentDao {
    PaymentDto add(PaymentRequestDto paymentInfoDto);

}
