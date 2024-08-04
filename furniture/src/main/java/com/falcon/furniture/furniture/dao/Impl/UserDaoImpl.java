package com.falcon.furniture.furniture.dao.Impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.falcon.furniture.furniture.dao.UserDao;
import com.falcon.furniture.furniture.dto.ChangePasswordRequestDto;
import com.falcon.furniture.furniture.dto.UserDto;
import com.falcon.furniture.furniture.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    @Override
    public UserDto add(User user) throws SecurityException {
        UserDto userDto = new UserDto();

        if (user.getPassword().length() >= 8 && user.getPassword().length() < 12) {
            user.setPassword(hashPassword(user.getPassword()));
        } else {
            userDto.setUser(null);
            userDto.setErrorMessage("Invalid Password, Please enter 8-12 characters");
            return userDto;
        }

        if (!isValidEmail(user.getEmail())) {
            userDto.setUser(null);
            userDto.setErrorMessage("Invalid email format.");
            return userDto;
        }

        if (!isValidPhoneNumber(user.getPhoneNo())) {
            userDto.setUser(null);
            userDto.setErrorMessage("Invalid phone number format.");
            return userDto;
        }

        List<User> allUser = dynamoDBMapper.scan(User.class, new DynamoDBScanExpression());
        for (User existingUser : allUser) {
            if (existingUser.getEmail().equalsIgnoreCase(user.getEmail())) {
                userDto.setUser(null);
                userDto.setErrorMessage("This user already exists.");
                return userDto;
            } else if (existingUser.getUserName().equalsIgnoreCase(user.getUserName())) {
                userDto.setUser(null);
                userDto.setErrorMessage("This userName is already taken.");
                return userDto;
            }
        }

        try {
            dynamoDBMapper.save(user);
            userDto.setUser(user);
            userDto.setErrorMessage(null);
        } catch (Exception e) {
            userDto.setUser(null);
            userDto.setErrorMessage(e.getMessage());
        }
        return userDto;

    }

    @Override
    public UserDto getUserById(String userId) {
        UserDto userDto = new UserDto();
        try {
            User user = dynamoDBMapper.load(User.class, userId);
            userDto.setUser(user);
            userDto.setErrorMessage(null);
        } catch (Exception e) {
            userDto.setUser(null);
            userDto.setErrorMessage(e.getMessage());
        }
        return userDto;
    }

    @Override
    public UserDto changePassword(ChangePasswordRequestDto changePasswordRequestDto) {
        User user = dynamoDBMapper.load(User.class, changePasswordRequestDto.getUserId());
        UserDto userDto = new UserDto();

        if (user == null) {
            userDto.setUser(null);
            userDto.setErrorMessage("User does not exist!");

        } else {
            if (user.getPassword().equalsIgnoreCase(hashPassword(changePasswordRequestDto.getCurrentPassword())) && changePasswordRequestDto.getNewPassword().equalsIgnoreCase(changePasswordRequestDto.getReEnterPassword())) {
                user.setPassword(hashPassword(changePasswordRequestDto.getNewPassword()));
                userDto.setUser(user);
                userDto.setErrorMessage(null);
            } else {
                userDto.setUser(null);
                userDto.setErrorMessage("Please enter correct current password!");
            }
        }
        return userDto;
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidPhoneNumber(String phoneNo) {
        String phoneRegex = "^0\\d{9}$";
        Pattern pattern = Pattern.compile(phoneRegex);
        Matcher matcher = pattern.matcher(phoneNo);
        return matcher.matches();
    }

    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}
