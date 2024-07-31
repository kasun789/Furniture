package com.falcon.furniture.furniture.service.Impl;

import com.falcon.furniture.furniture.dao.Impl.FurnitureDaoImpl;
import com.falcon.furniture.furniture.dao.Impl.ModelDaoImpl;
import com.falcon.furniture.furniture.dto.ModelDto;
import com.falcon.furniture.furniture.model.Model;
import com.falcon.furniture.furniture.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModelServiceImpl implements ModelService {

    @Autowired
    private ModelDaoImpl modelRepository;


    @Override
    public ModelDto addModel(Model model) {
        return modelRepository.add(model);
    }

    @Override
    public ModelDto viewModel(String modelId) {
        return modelRepository.getModelById(modelId);
    }
}
