package com.falcon.furniture.furniture.dao;

import com.falcon.furniture.furniture.dto.ManufacturerDto;
import com.falcon.furniture.furniture.dto.ModelDto;
import com.falcon.furniture.furniture.model.Manufacturer;
import com.falcon.furniture.furniture.model.Model;

public interface ManufacturerDao {
    ManufacturerDto add(Manufacturer manufacturer);

    ManufacturerDto getManufacturerById(String manufacturerId);
}
