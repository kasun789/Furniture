package com.falcon.furniture.furniture.service.Impl;

import com.falcon.furniture.furniture.dao.ManufacturerDao;
import com.falcon.furniture.furniture.dao.OrderDao;
import com.falcon.furniture.furniture.dto.ManufacturerDto;
import com.falcon.furniture.furniture.model.Manufacturer;
import com.falcon.furniture.furniture.service.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {

    @Autowired
    private ManufacturerDao manufacturerRepository;

    @Override
    public ManufacturerDto addManufacturer(Manufacturer manufacturer) {
        return manufacturerRepository.add(manufacturer);
    }

    @Override
    public ManufacturerDto viewManufacturer(String manufacturerId) {
        return manufacturerRepository.getManufacturerById(manufacturerId);
    }
}
