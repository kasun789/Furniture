package com.falcon.furniture.furniture.service;

import com.falcon.furniture.furniture.dto.FurnitureDto;
import com.falcon.furniture.furniture.dto.ModelDto;
import com.falcon.furniture.furniture.model.Model;

public interface ModelService {

    ModelDto addModel(Model model);

    ModelDto viewModel(String modelId);

}
