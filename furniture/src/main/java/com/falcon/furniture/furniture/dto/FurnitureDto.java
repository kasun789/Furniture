package com.falcon.furniture.furniture.dto;

import com.falcon.furniture.furniture.model.Furniture;
import lombok.Data;

@Data
public class FurnitureDto {
    Furniture furniture;
    String errorMessage;
}
