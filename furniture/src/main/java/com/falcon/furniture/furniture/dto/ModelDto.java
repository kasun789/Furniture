package com.falcon.furniture.furniture.dto;

import com.falcon.furniture.furniture.model.Furniture;
import com.falcon.furniture.furniture.model.Model;
import lombok.Data;

@Data
public class ModelDto {
    Model model;
    String errorMessage;
}
