package com.falcon.furniture.furniture.dao.Impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.falcon.furniture.furniture.dao.OrderDao;
import com.falcon.furniture.furniture.dto.AddFurnitureDto;
import com.falcon.furniture.furniture.dto.FurnitureDto;
import com.falcon.furniture.furniture.dto.OrderDto;
import com.falcon.furniture.furniture.dto.UserDto;
import com.falcon.furniture.furniture.model.Furniture;
import com.falcon.furniture.furniture.model.Model;
import com.falcon.furniture.furniture.model.Order;
import com.falcon.furniture.furniture.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    private UserDaoImpl userRepository;

    @Autowired
    private FurnitureDaoImpl furnitureRepository;


    @Override
    public OrderDto add(Order order) {
        OrderDto orderDto = new OrderDto();

        UserDto user = userRepository.getUserById(order.getUserId());

        FurnitureDto furniture = furnitureRepository.getItemById(order.getFurnitureId());

        if (user == null) {
            orderDto.setOrder(null);
            orderDto.setErrorMessage("user does not exist.");
            return orderDto;
        }

        if (furniture == null) {
            orderDto.setOrder(null);
            orderDto.setErrorMessage("furniture does not exist.");
            return orderDto;
        }

        try {
            LocalDateTime now = LocalDateTime.now();
            order.setDeliveryState(0);
            order.setDateTime(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            dynamoDBMapper.save(order);
            orderDto.setOrder(order);
            orderDto.setErrorMessage(null);
        } catch (Exception e) {
            orderDto.setOrder(null);
            orderDto.setErrorMessage(e.getMessage());
        }
        return orderDto;
    }

    @Override
    public OrderDto getOrderById(String orderId) {
        OrderDto orderDto = new OrderDto();
        try {
            Order order = dynamoDBMapper.load(Order.class, orderId);
            orderDto.setOrder(order);
            orderDto.setErrorMessage(null);
        } catch (Exception e) {
            orderDto.setOrder(null);
            orderDto.setErrorMessage(e.getMessage());
        }
        return orderDto;
    }
}
