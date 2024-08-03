package com.falcon.furniture.furniture.controller;

import com.falcon.furniture.furniture.dto.ManufacturerDto;
import com.falcon.furniture.furniture.dto.OrderDto;
import com.falcon.furniture.furniture.model.Manufacturer;
import com.falcon.furniture.furniture.model.Order;
import com.falcon.furniture.furniture.service.ManufacturerService;
import com.falcon.furniture.furniture.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manufacturer")
@CrossOrigin
public class ManufacturerController {

    @Autowired
    private ManufacturerService manufacturerService;

    @PostMapping("/add")
    public ManufacturerDto add(@RequestBody Manufacturer manufacturer) {
        return manufacturerService.addManufacturer(manufacturer);
    }

    @GetMapping("/view/{id}")
    public ManufacturerDto getManufacturer(@PathVariable("id") String manufacturerId) {
        return manufacturerService.viewManufacturer(manufacturerId);
    }

}
