package com.falcon.furniture.furniture.dto;

import lombok.Data;

@Data
public class SignupRequest {
    private String name;
    private String address;
    private String username;
    private String email;
    private String password;
    private String phoneNo;
    private String userRole;
}
