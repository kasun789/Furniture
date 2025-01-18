package com.falcon.furniture.furniture.controller;

import com.falcon.furniture.furniture.dto.AddFurnitureDto;
import com.falcon.furniture.furniture.dto.FilterFurnitureDto;
import com.falcon.furniture.furniture.dto.FurnitureDto;
import com.falcon.furniture.furniture.dto.FurnitureErrorDto;
import com.falcon.furniture.furniture.model.Furniture;
import com.falcon.furniture.furniture.service.FurnitureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/furniture")
@CrossOrigin
public class FurnitureController {

    private static final Logger logger = LoggerFactory.getLogger(FurnitureController.class);

    @Autowired
    private FurnitureService furnitureService;

	@PreAuthorize("hasRole('ROLE_ADMIN')")
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

    @PostMapping("/filterFurnitures")
    public List<Furniture> filterFurnitures(@RequestBody FilterFurnitureDto filterFurnitureDto) {
        return furnitureService.getFurnituresByFilter(filterFurnitureDto);
    }

}
