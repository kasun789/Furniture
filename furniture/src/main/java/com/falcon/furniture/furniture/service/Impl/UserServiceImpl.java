package com.falcon.furniture.furniture.service.Impl;

import com.falcon.furniture.furniture.dao.Impl.FurnitureDaoImpl;
import com.falcon.furniture.furniture.dao.UserDao;
import com.falcon.furniture.furniture.dto.ChangePasswordRequestDto;
import com.falcon.furniture.furniture.dto.UserDto;
import com.falcon.furniture.furniture.model.User;
import com.falcon.furniture.furniture.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userRepository;

    @Override
    public UserDto addUser(User user) {
        return userRepository.add(user);
    }

    @Override
    public UserDto viewUser(String userId) {
        return userRepository.getUserById(userId);
    }

    @Override
    public UserDto changePassword(ChangePasswordRequestDto changePasswordRequestDto) {
        return userRepository.changePassword(changePasswordRequestDto);
    }
}
