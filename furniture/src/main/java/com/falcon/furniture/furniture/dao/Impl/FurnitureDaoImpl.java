package com.falcon.furniture.furniture.dao.Impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.falcon.furniture.furniture.dao.FurnitureDao;
import com.falcon.furniture.furniture.dto.*;
import com.falcon.furniture.furniture.model.Furniture;
import com.falcon.furniture.furniture.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class FurnitureDaoImpl implements FurnitureDao {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    private ModelDaoImpl modelRepository;

    public AddFurnitureDto add(Furniture furniture) throws SecurityException {

        AddFurnitureDto addFurnitureDto = new AddFurnitureDto();
        List<Furniture> allFurniture = dynamoDBMapper.scan(Furniture.class, new DynamoDBScanExpression());
        for (Furniture existingFurniture : allFurniture) {
            if (existingFurniture.getName().equalsIgnoreCase(furniture.getName())) {
                addFurnitureDto.setFurniture(null);
                addFurnitureDto.setErrorMessage("Furniture item with name '" + furniture.getName() + "' already exists.");
                return addFurnitureDto;
            }
        }

        Model model = modelRepository.getModelById(furniture.getModelId()).getModel();
        System.out.println(furniture.getModelId());

        if (model == null) {
            addFurnitureDto.setFurniture(null);
            addFurnitureDto.setErrorMessage("Model does not exist.");
            return addFurnitureDto;
        }

        try {
            dynamoDBMapper.save(furniture);
            addFurnitureDto.setFurniture(furniture);
            addFurnitureDto.setErrorMessage(null);
        } catch (Exception e) {
            addFurnitureDto.setFurniture(null);
            addFurnitureDto.setErrorMessage(e.getMessage());
        }
        return addFurnitureDto;
    }

    public FurnitureDto getItemById(String furnitureId) {
        FurnitureDto furnitureDao = new FurnitureDto();
        try {
            Furniture furniture = dynamoDBMapper.load(Furniture.class, furnitureId);
            furnitureDao.setFurniture(furniture);
            furnitureDao.setErrorMessage(null);
        } catch (Exception e) {
            furnitureDao.setFurniture(null);
            furnitureDao.setErrorMessage(e.getMessage());
        }
        return furnitureDao;
    }

    public FurnitureErrorDto delete(String furnitureId) {
        FurnitureErrorDto furnitureErrorDto = new FurnitureErrorDto();
        try {
            Furniture furnitureItem = dynamoDBMapper.load(Furniture.class, furnitureId);
            dynamoDBMapper.delete(furnitureItem);
            furnitureErrorDto.setErrorMessage(null);
            furnitureErrorDto.setStatus("successful");
        } catch (Exception e) {
            furnitureErrorDto.setErrorMessage(e.getMessage());
            furnitureErrorDto.setStatus("unsuccessful");
        }

        return furnitureErrorDto;
    }

    public FurnitureErrorDto update(String furnitureId, Furniture furniture) {
        FurnitureErrorDto furnitureErrorDto = new FurnitureErrorDto();
        try {
            dynamoDBMapper.save(furniture,
                    new DynamoDBSaveExpression()
                            .withExpectedEntry("id",
                                    new ExpectedAttributeValue(
                                            new AttributeValue().withS(furnitureId)
                                    )));
            furnitureErrorDto.setErrorMessage(null);
            furnitureErrorDto.setStatus("successful");
        } catch (Exception e) {
            furnitureErrorDto.setErrorMessage(e.getMessage());
            furnitureErrorDto.setStatus("unsuccessful");
        }
        return (furnitureErrorDto);
    }

    public List<Furniture> getAllItems() {
        return dynamoDBMapper.scan(Furniture.class, new DynamoDBScanExpression());
    }

    @Override
    public List<Furniture> filterFurnitures(FilterFurnitureDto filterFurnitureDto) {

        Map<String, AttributeValue> eav = new HashMap<>();
        String filterValue = filterFurnitureDto.getFilterValue();

        if (filterFurnitureDto.getType() == 0) {
            eav.put(":filterValue", new AttributeValue().withS((String) filterValue));

        } else if (filterFurnitureDto.getType() == 1 || filterFurnitureDto.getType() == 2) {
            eav.put(":filterValue", new AttributeValue().withN(filterValue));

        } else {
            throw new IllegalArgumentException("Unsupported filter value type");
        }

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression(filterFurnitureDto.getFilterAttribute() + " = :filterValue")
                .withExpressionAttributeValues(eav);

        return dynamoDBMapper.scan(Furniture.class, scanExpression);
    }

}
