package com.falcon.furniture.furniture.dao;

import com.falcon.furniture.furniture.dto.*;
import com.falcon.furniture.furniture.model.Furniture;

import java.util.List;

public interface FurnitureDao {

    AddFurnitureDto add(Furniture furniture);

    FurnitureDto getItemById(String furnitureId);

    FurnitureErrorDto delete(String furnitureId);

    FurnitureErrorDto update(String furnitureId, Furniture furniture);

    List<Furniture> getAllItems();

    List<Furniture> filterFurnitures(FilterFurnitureDto filterFurnitureDto);

}
