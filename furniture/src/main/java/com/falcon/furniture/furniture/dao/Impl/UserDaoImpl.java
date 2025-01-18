package com.falcon.furniture.furniture.dao.Impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.falcon.furniture.furniture.dao.UserDao;
import com.falcon.furniture.furniture.dto.*;
import com.falcon.furniture.furniture.model.ForgetPassword;
import com.falcon.furniture.furniture.model.User;
import com.falcon.furniture.furniture.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
public class UserDaoImpl implements UserDao {

    private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    private EmailService emailServie;

    @Override
    public UserDto add(User user) throws SecurityException {
        UserDto userDto = new UserDto();

//        if (user.getPassword().length() >= 8 && user.getPassword().length() < 12) {
//            user.setPassword(user.getPassword());
//        } else {
//            logger.error("Invalid Password, Please enter 8-12 characters");
//            userDto.setUser(null);
//            userDto.setErrorMessage("Invalid Password, Please enter 8-12 characters");
//            return userDto;
//        }

        if (!isValidEmail(user.getEmail())) {
            logger.error("Invalid email format.");
            userDto.setUser(null);
            userDto.setErrorMessage("Invalid email format.");
            return userDto;
        }

        if (!isValidPhoneNumber(user.getPhoneNo())) {
            logger.error("Invalid phone number format.");
            userDto.setUser(null);
            userDto.setErrorMessage("Invalid phone number format.");
            return userDto;
        }

        List<User> allUser = dynamoDBMapper.scan(User.class, new DynamoDBScanExpression());
        for (User existingUser : allUser) {
            if (existingUser.getEmail().equalsIgnoreCase(user.getEmail())) {
                logger.error("This user already exists.");
                userDto.setUser(null);
                userDto.setErrorMessage("This user already exists.");
                return userDto;
            } else if (existingUser.getUserName().equalsIgnoreCase(user.getUserName())) {
                userDto.setUser(null);
                logger.error("This userName is already taken.");
                userDto.setErrorMessage("This userName is already taken.");
                return userDto;
            }
        }

        try {
            dynamoDBMapper.save(user);
            userDto.setUser(user);
            userDto.setErrorMessage(null);
        } catch (Exception e) {
            logger.error(e.getMessage());
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

    @Override
    public UserDto setPassword(SetForgottenPasswordDto setForgottenPasswordDto) {
        User user = dynamoDBMapper.load(User.class, setForgottenPasswordDto.getUserId());
        UserDto userDto = new UserDto();

        if (user == null) {
            userDto.setUser(null);
            userDto.setErrorMessage("User does not exist!");

        } else {
            if (setForgottenPasswordDto.getNewPassword().equalsIgnoreCase(setForgottenPasswordDto.getReEnterPassword())) {
                user.setPassword(hashPassword(setForgottenPasswordDto.getNewPassword()));
                userDto.setUser(user);
                userDto.setErrorMessage(null);
            } else {
                userDto.setUser(null);
                userDto.setErrorMessage("Please enter correct current password!");
            }
        }
        return userDto;
    }

    @Override
    public ForgottenPasswordDto forgetPassword(String email) {
        boolean foundUser = false;
        List<User> allUser = dynamoDBMapper.scan(User.class, new DynamoDBScanExpression());
        ForgetPassword forgetPassword = new ForgetPassword();
        ForgottenPasswordDto forgottenPasswordDto = new ForgottenPasswordDto();
        for (User existingUser : allUser) {
            if (existingUser.getEmail().equalsIgnoreCase(email)) {
                forgetPassword.setUserId(existingUser.getId());
                foundUser = true;
                break;
            }
        }
        if (!foundUser) {
            forgetPassword.setUserId(null);
            forgetPassword.setEmail(null);
            forgetPassword.setVerficationCode(null);
            forgetPassword.setExpiryDate(null);
            forgottenPasswordDto.setErrorMessage("Please enter correct email!");
        } else {
            String verificationCode = generateVerificationCode();
            forgetPassword.setEmail(email);
            forgetPassword.setVerficationCode(verificationCode);
            forgetPassword.setExpiryDate(String.valueOf(LocalDateTime.now().plusMinutes(15).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
            forgottenPasswordDto.setErrorMessage(null);
        }
        forgottenPasswordDto.setForgetPassword(forgetPassword);

        try {
            if (foundUser) {
                dynamoDBMapper.save(forgetPassword);
                emailServie.sendVerificationCode(forgetPassword.getEmail(), forgetPassword.getVerficationCode());
            }
        } catch (Exception e) {
            forgottenPasswordDto.setErrorMessage(e.getMessage());
        }

        return forgottenPasswordDto;
    }

    @Override
    public VerifyUserDto verfyUser(String verficationCode) {
        List<ForgetPassword> forgetPasswordItems = dynamoDBMapper.scan(ForgetPassword.class, new DynamoDBScanExpression());
        boolean verfiedUser = false;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        VerifyUserDto verifyUserDto = new VerifyUserDto();
        for (ForgetPassword item : forgetPasswordItems) {
            if (item.getVerficationCode().equalsIgnoreCase(verficationCode)) {
                if (LocalDateTime.now().isAfter(LocalDateTime.parse(item.getExpiryDate(), formatter))) {
                    verifyUserDto.setUserId(null);
                    verifyUserDto.setErrorMessage("Code is expired.");
                } else {
                    verfiedUser = true;
                    verifyUserDto.setUserId(item.getUserId());
                    verifyUserDto.setErrorMessage(null);
                }
                break;
            }
        }
        if (!verfiedUser) {
            verifyUserDto.setUserId(null);
            verifyUserDto.setErrorMessage("Invalid code. Try again.");
        }

        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":userId", new AttributeValue().withS(verifyUserDto.getUserId()));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("userId = :userId")
                .withExpressionAttributeValues(eav);

        List<ForgetPassword> itemsToDelete = dynamoDBMapper.scan(ForgetPassword.class, scanExpression);

        for (ForgetPassword item : itemsToDelete) {
            dynamoDBMapper.delete(item);
        }
        return verifyUserDto;
    }

	@Override
    public Optional<User> getUserByEmail(String email) {
        try {
            // Create the scan expression
            DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                    .withFilterExpression("email = :email")
                    .withExpressionAttributeValues(Map.of(":email", new AttributeValue().withS(email)));

            // Perform the scan
            List<User> users = dynamoDBMapper.scan(User.class, scanExpression);

            // Check if the result is empty before accessing the first element
            if (users.isEmpty()) {
                return Optional.empty();
            }
            return Optional.ofNullable(users.get(0));

        } catch (Exception e) {
            // Log the full stack trace for better debugging
            logger.error("Exception caught ---> {}", e.getMessage(), e);
            return Optional.empty();
        }
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

    private String generateVerificationCode() {
        return String.format("%06d", new Random().nextInt(999999));
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
