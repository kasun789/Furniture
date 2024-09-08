package com.falcon.furniture.furniture.controller;

import com.falcon.furniture.furniture.dto.OrderDto;
import com.falcon.furniture.furniture.dto.UserDto;
import com.falcon.furniture.furniture.model.Order;
import com.falcon.furniture.furniture.model.User;
import com.falcon.furniture.furniture.service.OrderService;
import com.falcon.furniture.furniture.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/addOrder")
    public OrderDto addOrder(@RequestBody Order order) {
        return orderService.addOrder(order);
    }

    @GetMapping("/viewOrder/{id}")
    public OrderDto viewOrder(@PathVariable("id") String orderId) {
        return orderService.viewOrder(orderId);
    }
}
