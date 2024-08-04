package com.falcon.furniture.furniture.dto;

import com.falcon.furniture.furniture.model.Order;
import lombok.Data;

@Data
public class OrderDto {
    Order order;
    String errorMessage;
}
