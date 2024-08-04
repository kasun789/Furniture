package com.falcon.furniture.furniture.controller;

import com.falcon.furniture.furniture.dto.AddFurnitureDto;
import com.falcon.furniture.furniture.dto.FurnitureDto;
import com.falcon.furniture.furniture.dto.FurnitureErrorDto;
import com.falcon.furniture.furniture.model.Furniture;
import com.falcon.furniture.furniture.service.FurnitureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/furniture")
@CrossOrigin
public class FurnitureController {

    @Autowired
    private FurnitureService furnitureService;

    @PostMapping("/addFurniture")
    public AddFurnitureDto addFurniture(@RequestBody Furniture furniture) {
        return furnitureService.addFurniture(furniture);
    }

    @GetMapping("/viewIFurniture/{id}")
    public FurnitureDto viewIFurniture(@PathVariable("id") String furnitureId) {
        return furnitureService.viewFurnitureItem(furnitureId);
    }

    @DeleteMapping("/deleteFurniture/{id}")
    public FurnitureErrorDto deleteFurniture(@PathVariable("id") String furnitureId) {
        return furnitureService.deleteFurniture(furnitureId);
    }

    @PutMapping("/updateFurniture/{id}")
    public FurnitureErrorDto updateFurniture(@PathVariable("id") String furnitureId, @RequestBody Furniture furniture) {
        return furnitureService.updateFurniture(furnitureId, furniture);
    }

    @GetMapping("/getAllFurniture")
    public List<Furniture> getAllFurniture() {
        return furnitureService.getAllFurnitures();
    }

}
