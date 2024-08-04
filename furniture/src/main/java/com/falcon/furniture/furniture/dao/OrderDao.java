package com.falcon.furniture.furniture.dao;

import com.falcon.furniture.furniture.dto.ModelDto;
import com.falcon.furniture.furniture.dto.OrderDto;
import com.falcon.furniture.furniture.model.Model;
import com.falcon.furniture.furniture.model.Order;

public interface OrderDao {

    OrderDto add(Order order);

    OrderDto getOrderById(String orderId);
}
