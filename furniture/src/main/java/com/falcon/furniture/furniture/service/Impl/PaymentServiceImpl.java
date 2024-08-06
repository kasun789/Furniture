package com.falcon.furniture.furniture.service.Impl;

import com.falcon.furniture.furniture.dao.OrderDao;
import com.falcon.furniture.furniture.dao.PaymentDao;
import com.falcon.furniture.furniture.dto.PaymentDto;
import com.falcon.furniture.furniture.dto.PaymentRequestDto;
import com.falcon.furniture.furniture.service.PaymentService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentDao paymentRepository;

    @Override
    public PaymentDto addPayment(PaymentRequestDto paymentInfoDto) {
        return paymentRepository.add(paymentInfoDto);
    }
}
