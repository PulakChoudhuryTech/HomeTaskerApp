package com.smarthome.hometasker.mapper.expenseTracker;

import com.smarthome.hometasker.dao.models.expenseTracker.Category;
import com.smarthome.hometasker.dto.expenseTracker.CategoryDTO;

public class CategoryMapper {
    
    public static Category mapDtoToEntity(CategoryDTO dto) {
        Category entity = new Category();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        return entity;
    }
}
