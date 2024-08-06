package com.falcon.furniture.furniture.controller;

import com.falcon.furniture.furniture.dto.*;
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

    @PostMapping("/addUser")
    public UserDto addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @GetMapping("/viewUser/{id}")
    public UserDto viewUser(@PathVariable("id") String userId) {
        return userService.viewUser(userId);
    }

    @PostMapping("changePassword")
    public UserDto changePassword(@RequestBody ChangePasswordRequestDto changePasswordRequestDto) {
        return userService.changePassword(changePasswordRequestDto);
    }

    @GetMapping("forgetPassword/{email}")
    public ForgottenPasswordDto forgetPassword(@PathVariable("email") String email) {
        return userService.forgottenPassword(email);
    }

    @GetMapping("checkVerificationCode/{verficationCode}")
    public VerifyUserDto checkVerificationCode(@PathVariable("verficationCode") String verficationCode) {
        return userService.verfyUser(verficationCode);
    }

    @PostMapping("setForgottenPassword")
    public UserDto setForgottenPassword(@RequestBody SetForgottenPasswordDto setForgottenPasswordDto) {
        return userService.setPassword(setForgottenPasswordDto);
    }
}

