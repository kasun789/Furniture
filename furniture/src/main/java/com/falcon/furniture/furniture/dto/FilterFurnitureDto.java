package com.falcon.furniture.furniture.dto;

import lombok.Data;

@Data
public class FilterFurnitureDto {
    String filterAttribute;
    String filterValue;
    Integer type;
}
