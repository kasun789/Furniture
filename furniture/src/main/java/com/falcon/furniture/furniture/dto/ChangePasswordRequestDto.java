package com.falcon.furniture.furniture.dto;

import lombok.Data;

@Data
public class ChangePasswordRequestDto {
    String userId;
    String currentPassword;
    String newPassword;
    String reEnterPassword;
}
