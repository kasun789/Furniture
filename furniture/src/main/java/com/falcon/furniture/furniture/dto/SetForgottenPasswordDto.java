package com.falcon.furniture.furniture.dto;

import lombok.Data;
@Data
public class SetForgottenPasswordDto {
        String userId;
        String newPassword;
        String reEnterPassword;
}
