package com.falcon.furniture.furniture.dao.Impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.falcon.furniture.furniture.dao.PaymentDao;
import com.falcon.furniture.furniture.dto.PaymentDto;
import com.falcon.furniture.furniture.dto.PaymentRequestDto;
import com.falcon.furniture.furniture.model.Payment;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.param.ChargeCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Repository
public class PaymentDaoImpl implements PaymentDao {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    @Value("${stripe.key.secret}")
    private String secretKey;

    @Value("${stripe.currency}")
    private String currency;

    @Override
    public PaymentDto add(PaymentRequestDto paymentInfoDto) {

        PaymentDto paymentDto = new PaymentDto();
        Payment payment = new Payment();
        Stripe.apiKey = secretKey;

        try {
            ChargeCreateParams createParams = new ChargeCreateParams.Builder()
                    .setAmount(paymentInfoDto.getAmount())
                    .setCurrency(currency)
                    .setSource(paymentInfoDto.getToken())
                    .build();

            Charge charge = Charge.create(createParams);

            try {
                LocalDateTime now = LocalDateTime.now();
                payment.setDateTime(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                payment.setOrderId(paymentInfoDto.getOrderId());
                payment.setPrice(paymentInfoDto.getAmount());
                dynamoDBMapper.save(payment);
                paymentDto.setPayment(payment);
                paymentDto.setErrorMessage(null);
            } catch (Exception e) {
                paymentDto.setPayment(null);
                paymentDto.setErrorMessage(e.getMessage());
            }
        } catch (StripeException e) {
            paymentDto.setPayment(null);
            paymentDto.setErrorMessage(e.getMessage());
        }
        return paymentDto;
    }
}


