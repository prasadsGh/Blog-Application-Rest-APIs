package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Category;
import com.springboot.blog.exceptions.ResourceNotFoundException;
import com.springboot.blog.payload.CategoryDto;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {


    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto,Category.class);
        Category savedCategory=categoryRepository.save(category);

        return modelMapper.map(savedCategory, CategoryDto.class);

    }

    @Override
    public CategoryDto getCategory(long categoryId){
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                ()->new ResourceNotFoundException(
                        "Category",
                        "Id",
                        categoryId
                )
        );

        return modelMapper.map(category, CategoryDto.class);

    }

    @Override
    public List<CategoryDto> getAllCategories(){
        List<Category> listOfCategories= categoryRepository.findAll();
        List<CategoryDto> returnList = new ArrayList<>();
        for(Category category: listOfCategories){
            CategoryDto categoryDto =modelMapper.map(category, CategoryDto.class);
            returnList.add(categoryDto);
        }

        return returnList;
    }
    @Override
    public CategoryDto updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable long id){
        Category category = categoryRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException(
                        "Category",
                        "id",
                        id
                )
        );
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        Category updatedCategory= categoryRepository.save(category);
        return modelMapper.map(updatedCategory, CategoryDto.class);
    }
}