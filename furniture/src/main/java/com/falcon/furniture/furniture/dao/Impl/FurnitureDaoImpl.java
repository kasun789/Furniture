package com.falcon.furniture.furniture.dao.Impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.falcon.furniture.furniture.dao.FurnitureDao;
import com.falcon.furniture.furniture.dto.AddFurnitureDto;
import com.falcon.furniture.furniture.dto.FurnitureErrorDto;
import com.falcon.furniture.furniture.dto.FurnitureDto;
import com.falcon.furniture.furniture.model.Furniture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FurnitureDaoImpl implements FurnitureDao {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public AddFurnitureDto add(Furniture furniture) throws SecurityException{
        AddFurnitureDto addFurnitureDto = new AddFurnitureDto();
        List<Furniture> allFurniture = dynamoDBMapper.scan(Furniture.class, new DynamoDBScanExpression());
        for (Furniture existingFurniture : allFurniture) {
            if (existingFurniture.getName().equalsIgnoreCase(furniture.getName())) {
                addFurnitureDto.setFurniture(null);
                addFurnitureDto.setErrorMessage("Furniture item with name '" + furniture.getName() + "' already exists.");
                return addFurnitureDto;
            }
        }
        try {
            dynamoDBMapper.save(furniture);
            addFurnitureDto.setFurniture(furniture);
            addFurnitureDto.setErrorMessage(null);
        }
        catch (Exception e) {
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
        }
        catch (Exception e) {
            furnitureDao.setFurniture(null);
            furnitureDao.setErrorMessage(e.getMessage());
        }
        return furnitureDao;
    }

    public FurnitureErrorDto delete(String furnitureId) {
        FurnitureErrorDto furnitureErrorDto = new FurnitureErrorDto();
        try{
            Furniture furnitureItem = dynamoDBMapper.load(Furniture.class, furnitureId);
            dynamoDBMapper.delete(furnitureItem);
            furnitureErrorDto.setErrorMessage(null);
            furnitureErrorDto.setStatus("successful");
        }
        catch (Exception e) {
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
        }
        catch (Exception e) {
            furnitureErrorDto.setErrorMessage(e.getMessage());
            furnitureErrorDto.setStatus("unsuccessful");
        }
        return (furnitureErrorDto);
    }

    public List<Furniture> getAllItems() {
        return dynamoDBMapper.scan(Furniture.class, new DynamoDBScanExpression());
    }


}
