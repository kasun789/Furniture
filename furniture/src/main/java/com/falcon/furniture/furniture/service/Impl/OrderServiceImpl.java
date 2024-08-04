package com.falcon.furniture.furniture.service.Impl;

import com.falcon.furniture.furniture.dao.OrderDao;
import com.falcon.furniture.furniture.dao.UserDao;
import com.falcon.furniture.furniture.dto.OrderDto;
import com.falcon.furniture.furniture.dto.UserDto;
import com.falcon.furniture.furniture.model.Order;
import com.falcon.furniture.furniture.model.User;
import com.falcon.furniture.furniture.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderRepository;

    @Override
    public OrderDto addOrder(Order order) {
        return orderRepository.add(order);
    }

    @Override
    public OrderDto viewOrder(String orderId) {
        return orderRepository.getOrderById(orderId);
    }
}
