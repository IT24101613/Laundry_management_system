package com.pgno209.smartwash.service;

import com.pgno209.smartwash.model.Category;
import com.pgno209.smartwash.model.Clothes;
import com.pgno209.smartwash.repository.CategoryRepository;
import com.pgno209.smartwash.repository.ClothesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClothesService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ClothesRepository clothesRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public List<Clothes> getClothesByCategory(Integer categoryId) {
        return clothesRepository.findByCategoryId(categoryId);
    }

    public Clothes getClothById(Integer clothId) {
        return clothesRepository.findById(clothId);
    }
}