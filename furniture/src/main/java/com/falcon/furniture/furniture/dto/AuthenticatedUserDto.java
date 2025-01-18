package com.falcon.furniture.furniture.dto;

import lombok.Data;

@Data
public class AuthenticatedUserDto {
    private String id;
    private String username;
    private String email;
    private String password;
    private String userRole;
}
