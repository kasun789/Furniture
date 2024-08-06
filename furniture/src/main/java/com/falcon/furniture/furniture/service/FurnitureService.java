package com.falcon.furniture.furniture.service;

import com.falcon.furniture.furniture.dto.AddFurnitureDto;
import com.falcon.furniture.furniture.dto.FilterFurnitureDto;
import com.falcon.furniture.furniture.dto.FurnitureDto;
import com.falcon.furniture.furniture.dto.FurnitureErrorDto;
import com.falcon.furniture.furniture.model.Furniture;

import java.util.List;

public interface FurnitureService {

    AddFurnitureDto addFurniture(Furniture furniture);

    FurnitureDto viewFurnitureItem(String furnitureId);

    FurnitureErrorDto deleteFurniture(String furnitureId);

    FurnitureErrorDto updateFurniture(String furnitureId, Furniture furniture);

    List<Furniture> getAllFurnitures();

    List<Furniture> getFurnituresByFilter(FilterFurnitureDto filterFurnitureDto);
}
