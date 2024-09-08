package com.falcon.furniture.furniture.controller;

import com.falcon.furniture.furniture.dto.AddFurnitureDto;
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
    @PostMapping("/add")
    public AddFurnitureDto addFurniture(@RequestBody Furniture furniture) {
        return furnitureService.addFurniture(furniture);
    }

    @GetMapping("/view/{id}")
    public FurnitureDto viewItem(@PathVariable("id") String furnitureId) {
        return furnitureService.viewFurnitureItem(furnitureId);
    }

    @DeleteMapping("/delete/{id}")
    public FurnitureErrorDto delete(@PathVariable("id") String furnitureId) {
        return furnitureService.deleteFurniture(furnitureId);
    }

    @PutMapping("/update/{id}")
    public FurnitureErrorDto update(@PathVariable("id") String furnitureId, @RequestBody Furniture furniture) {
        return furnitureService.updateFurniture(furnitureId, furniture);
    }

    @GetMapping("/getAll")
    public List<Furniture> getAll() {
        return furnitureService.getAllFurnitures();
    }

}
