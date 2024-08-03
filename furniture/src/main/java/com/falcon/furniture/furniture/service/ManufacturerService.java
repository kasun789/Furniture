package com.falcon.furniture.furniture.service;

import com.falcon.furniture.furniture.dto.ManufacturerDto;
import com.falcon.furniture.furniture.dto.OrderDto;
import com.falcon.furniture.furniture.model.Manufacturer;
import com.falcon.furniture.furniture.model.Order;

public interface ManufacturerService {
    ManufacturerDto addManufacturer(Manufacturer manufacturer);

    ManufacturerDto viewManufacturer(String manufacturerId);
}
