package com.falcon.furniture.furniture.service.Impl;

import com.falcon.furniture.furniture.dto.AddFurnitureDto;
import com.falcon.furniture.furniture.dto.FurnitureDto;
import com.falcon.furniture.furniture.dto.FurnitureErrorDto;
import com.falcon.furniture.furniture.model.Furniture;
import com.falcon.furniture.furniture.dao.Impl.FurnitureDaoImpl;
import com.falcon.furniture.furniture.service.FurnitureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class FurnitureServiceImpl implements FurnitureService {

    @Autowired
    private FurnitureDaoImpl furnitureRepository;

    @Override
    public AddFurnitureDto addFurniture(Furniture furniture) {
        return furnitureRepository.add(furniture);
    }

    @Override
    public FurnitureDto viewFurnitureItem(String furnitureId){
        return furnitureRepository.getItemById(furnitureId);
    }

    @Override
    public FurnitureErrorDto deleteFurniture(String furnitureId){
        return furnitureRepository.delete(furnitureId);
    }

    @Override
    public FurnitureErrorDto updateFurniture(String furnitureId, Furniture furniture){
        return furnitureRepository.update(furnitureId, furniture);
    }

    @Override
    public List<Furniture> getAllFurnitures() {
        return furnitureRepository.getAllItems();
    }
}
