package com.falcon.furniture.furniture.service;

import com.falcon.furniture.furniture.dto.ChangePasswordRequestDto;
import com.falcon.furniture.furniture.dto.ModelDto;
import com.falcon.furniture.furniture.dto.UserDto;
import com.falcon.furniture.furniture.model.Model;
import com.falcon.furniture.furniture.model.User;

public interface UserService {
    UserDto addUser(User user);

    UserDto viewUser(String userId);

    UserDto changePassword(ChangePasswordRequestDto changePasswordRequestDto);
}
