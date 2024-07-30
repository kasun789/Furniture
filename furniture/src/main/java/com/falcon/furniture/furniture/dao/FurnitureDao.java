package com.falcon.furniture.furniture.dao;

import com.falcon.furniture.furniture.dto.AddFurnitureDto;
import com.falcon.furniture.furniture.dto.FurnitureErrorDto;
import com.falcon.furniture.furniture.dto.FurnitureDto;
import com.falcon.furniture.furniture.model.Furniture;

import java.util.List;

public interface FurnitureDao {

    AddFurnitureDto add(Furniture furniture);

    FurnitureDto getItemById(String furnitureId);

    FurnitureErrorDto delete(String furnitureId);

    FurnitureErrorDto update(String furnitureId, Furniture furniture);

    List<Furniture> getAllItems();
}
