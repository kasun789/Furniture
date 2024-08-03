package com.falcon.furniture.furniture.dto;

import com.falcon.furniture.furniture.model.Model;
import com.falcon.furniture.furniture.model.User;
import lombok.Data;

@Data
public class UserDto {
    User user;
    String errorMessage;
}
