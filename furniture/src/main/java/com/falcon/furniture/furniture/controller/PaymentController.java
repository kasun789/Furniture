package com.falcon.furniture.furniture.controller;

import com.falcon.furniture.furniture.dto.PaymentDto;
import com.falcon.furniture.furniture.dto.PaymentRequestDto;
import com.falcon.furniture.furniture.service.OrderService;
import com.falcon.furniture.furniture.service.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.param.ChargeCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@CrossOrigin
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/addPayment")
    public PaymentDto addPayment(@RequestBody PaymentRequestDto paymentRequestDto) {
       return paymentService.addPayment(paymentRequestDto);
}

}
