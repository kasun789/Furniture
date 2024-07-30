package com.falcon.furniture.furniture.dao.Impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.falcon.furniture.furniture.dao.ModelDao;
import com.falcon.furniture.furniture.dto.AddFurnitureDto;
import com.falcon.furniture.furniture.dto.FurnitureDto;
import com.falcon.furniture.furniture.dto.ModelDto;
import com.falcon.furniture.furniture.model.Furniture;
import com.falcon.furniture.furniture.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ModelDaoImpl implements ModelDao {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    @Override
    public ModelDto add(Model model) throws SecurityException{

        ModelDto modelDto = new ModelDto();
        List<Model> allModels = dynamoDBMapper.scan(Model.class, new DynamoDBScanExpression());
        for (Model existingModel : allModels) {
            if (existingModel.getName().equalsIgnoreCase(model.getName())) {
                modelDto.setModel(null);
                modelDto.setErrorMessage("model with name '" + model.getName() + "' already exists.");
                return modelDto;
            }
        }
        try {
            dynamoDBMapper.save(model);
            modelDto.setModel(model);
            modelDto.setErrorMessage(null);
        }
        catch (Exception e) {
            modelDto.setModel(null);
            modelDto.setErrorMessage(e.getMessage());
        }
        return modelDto;
    }

    @Override
    public ModelDto getModelById(String modelId) {
            ModelDto modelDto = new ModelDto();
            try {
                Model model = dynamoDBMapper.load(Model.class, modelId);
                modelDto.setModel(model);
                modelDto.setErrorMessage(null);
            }
            catch (Exception e) {
                modelDto.setModel(null);
                modelDto.setErrorMessage(e.getMessage());
            }
            return modelDto;
    }
}
