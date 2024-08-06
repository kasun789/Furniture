package com.falcon.furniture.furniture.service;

public interface EmailService {

    void sendVerificationCode(String to, String verificationCode);
}
