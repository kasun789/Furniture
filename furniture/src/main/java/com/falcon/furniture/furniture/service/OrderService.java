package com.falcon.furniture.furniture.service;

import com.falcon.furniture.furniture.dto.OrderDto;
import com.falcon.furniture.furniture.dto.UserDto;
import com.falcon.furniture.furniture.model.Order;
import com.falcon.furniture.furniture.model.User;

public interface OrderService {
    OrderDto addOrder(Order order);

    OrderDto viewOrder(String orderId);
}
