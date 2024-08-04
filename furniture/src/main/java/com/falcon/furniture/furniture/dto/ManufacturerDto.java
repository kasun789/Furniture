package com.falcon.furniture.furniture.dto;

import com.falcon.furniture.furniture.model.Manufacturer;
import lombok.Data;

@Data
public class ManufacturerDto {
    Manufacturer manufacturer;
    String errorMessage;
}
