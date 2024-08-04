package com.falcon.furniture.furniture.dao.Impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.falcon.furniture.furniture.dao.ManufacturerDao;
import com.falcon.furniture.furniture.dto.FurnitureDto;
import com.falcon.furniture.furniture.dto.ManufacturerDto;
import com.falcon.furniture.furniture.dto.OrderDto;
import com.falcon.furniture.furniture.dto.UserDto;
import com.falcon.furniture.furniture.model.Manufacturer;
import com.falcon.furniture.furniture.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Repository
public class ManufacturerDaoImpl implements ManufacturerDao {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    @Override
    public ManufacturerDto add(Manufacturer manufacturer) {

        ManufacturerDto manufacturerDto = new ManufacturerDto();

        try {
            dynamoDBMapper.save(manufacturer);
            manufacturerDto.setManufacturer(manufacturer);
            manufacturerDto.setErrorMessage(null);
        } catch (Exception e) {
            manufacturerDto.setManufacturer(null);
            manufacturerDto.setErrorMessage(e.getMessage());
        }
        return manufacturerDto;
    }

    @Override
    public ManufacturerDto getManufacturerById(String manufacturerId) {

        ManufacturerDto manufacturerDto = new ManufacturerDto();

        try {
            Manufacturer manufacturer = dynamoDBMapper.load(Manufacturer.class, manufacturerId);
            manufacturerDto.setManufacturer(manufacturer);
            manufacturerDto.setErrorMessage(null);
        } catch (Exception e) {
            manufacturerDto.setManufacturer(null);
            manufacturerDto.setErrorMessage(e.getMessage());
        }
        return manufacturerDto;

    }
}
