package com.falcon.furniture.furniture.controller;

import com.falcon.furniture.furniture.dto.AddFurnitureDto;
import com.falcon.furniture.furniture.dto.FurnitureDto;
import com.falcon.furniture.furniture.dto.ModelDto;
import com.falcon.furniture.furniture.model.Furniture;
import com.falcon.furniture.furniture.model.Model;
import com.falcon.furniture.furniture.service.FurnitureService;
import com.falcon.furniture.furniture.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/model")
@CrossOrigin
public class ModelController {

    @Autowired
    private ModelService modelService;

    @PostMapping("/add")
    public ModelDto addModelItem(@RequestBody Model model) {
        return modelService.addModel(model);
    }

    @GetMapping("/view/{id}")
    public ModelDto viewItem(@PathVariable("id") String modelId) {
        return modelService.viewModel(modelId);
    }
}
