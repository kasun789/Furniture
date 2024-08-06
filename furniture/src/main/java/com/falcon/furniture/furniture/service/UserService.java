package com.falcon.furniture.furniture.service;

import com.falcon.furniture.furniture.dto.*;
import com.falcon.furniture.furniture.model.Model;
import com.falcon.furniture.furniture.model.User;

public interface UserService {
    UserDto addUser(User user);

    UserDto viewUser(String userId);

    UserDto changePassword(ChangePasswordRequestDto changePasswordRequestDto);

    ForgottenPasswordDto forgottenPassword(String email);

    VerifyUserDto verfyUser(String verficationCode);

    UserDto setPassword(SetForgottenPasswordDto setForgottenPasswordDto);
}
