package com.falcon.furniture.furniture.controller;

import com.falcon.furniture.furniture.dto.ChangePasswordRequestDto;
import com.falcon.furniture.furniture.dto.ModelDto;
import com.falcon.furniture.furniture.dto.UserDto;
import com.falcon.furniture.furniture.model.Model;
import com.falcon.furniture.furniture.model.User;
import com.falcon.furniture.furniture.service.ModelService;
import com.falcon.furniture.furniture.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public UserDto add(@RequestBody User user) {
        return userService.addUser(user);
    }

    @GetMapping("/view/{id}")
    public UserDto getUser(@PathVariable("id") String userId) {
        return userService.viewUser(userId);
    }

    @PostMapping("changePassword")
    public UserDto changePassword(@RequestBody ChangePasswordRequestDto changePasswordRequestDto) {
        return userService.changePassword(changePasswordRequestDto);
    }
}
