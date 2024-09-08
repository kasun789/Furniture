package com.falcon.furniture.furniture.dao;

import com.falcon.furniture.furniture.dto.ChangePasswordRequestDto;
import com.falcon.furniture.furniture.dto.ModelDto;
import com.falcon.furniture.furniture.dto.UserDto;
import com.falcon.furniture.furniture.model.Model;
import com.falcon.furniture.furniture.model.User;

import java.util.Optional;

public interface UserDao {
    UserDto add(User user);

    UserDto getUserById(String userId);

    UserDto changePassword(ChangePasswordRequestDto changePasswordRequestDto);

    Optional<User> getUserByEmail(String email);

}
