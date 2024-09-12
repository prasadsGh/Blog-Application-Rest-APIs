package com.springboot.blog.controller;

import com.springboot.blog.payload.CategoryDto;
import com.springboot.blog.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Tag(
        name="CRUD Rest APIs for Category Resource"
)
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // build add category rest api
    //http://localhost:8080/api/categories/add
    @PostMapping(value="/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto){
        CategoryDto savedDto = categoryService.addCategory(categoryDto);
        return new ResponseEntity<>(savedDto, HttpStatus.CREATED);
    }

    //http://localhost:8080/api/categories/id
    @GetMapping(value="/{id}")
   // @PreAuthorize("hasRole({'ADMIN','USER'})")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable(name="id") long id){
        return new ResponseEntity<>(categoryService.getCategory(id),HttpStatus.OK);
    }

    //http://localhost:8080/api/categories/
    @GetMapping
    // @PreAuthorize("hasRole({'ADMIN','USER'})")
    public ResponseEntity<List<CategoryDto>> getCategory(){
        return new ResponseEntity<>(categoryService.getAllCategories(),HttpStatus.OK);
    }

    //http://localhost:8080/api/categories/id
    @PutMapping(value="/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable long id){

            CategoryDto categoryDto1=categoryService.updateCategory(categoryDto,id);
            return new ResponseEntity<>(categoryDto1,HttpStatus.OK);
    }

    //http://localhost:8080/api/categories/id
    @DeleteMapping(value="/{id}")
     @PreAuthorize("hasRole({'ADMIN'})")
    public ResponseEntity<String> deleteCategory(@PathVariable(name="id") long id){
        categoryService.deleteCategory(id);
        return new  ResponseEntity<>("Category deleted successfully",HttpStatus.OK);
    }




}
