package com.falcon.furniture.furniture.dao;

import com.falcon.furniture.furniture.dto.AddFurnitureDto;
import com.falcon.furniture.furniture.dto.FurnitureDto;
import com.falcon.furniture.furniture.dto.ModelDto;
import com.falcon.furniture.furniture.model.Furniture;
import com.falcon.furniture.furniture.model.Model;

public interface ModelDao {

    ModelDto add(Model model);

    ModelDto getModelById(String modelId);
}
