package com.falcon.furniture.furniture.dao;

import com.falcon.furniture.furniture.dto.*;
import com.falcon.furniture.furniture.model.Model;
import com.falcon.furniture.furniture.model.User;

import java.util.Optional;

public interface UserDao {
    UserDto add(User user);

    UserDto getUserById(String userId);

    UserDto changePassword(ChangePasswordRequestDto changePasswordRequestDto);

    ForgottenPasswordDto forgetPassword(String email);

    VerifyUserDto verfyUser(String verficationCode);

    UserDto setPassword(SetForgottenPasswordDto setForgottenPasswordDto);

    Optional<User> getUserByEmail(String email);

}
