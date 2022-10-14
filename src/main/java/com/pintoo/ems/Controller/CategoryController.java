package com.pintoo.ems.Controller;


import com.pintoo.ems.Entity.Address;
import com.pintoo.ems.Entity.Category;
import com.pintoo.ems.Entity.User;
import com.pintoo.ems.Request.CategoryRequest;
import com.pintoo.ems.Response.CategoryResponse;
import com.pintoo.ems.Service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/api/category")
@AllArgsConstructor
public class CategoryController {

    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Category> saveCategory(@Valid @RequestBody CategoryRequest category) {
        return new ResponseEntity(categoryService.addCategory(category), HttpStatus.CREATED);

    }


    @GetMapping("/all")
    public List<CategoryResponse> getAllCategories(){
        return categoryService.getAllCategories();

    }


    @GetMapping
    public List<Category> getAllCategory()
    {
        return categoryService.getAllCategory();
    }


    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> getCategoryById(@PathVariable long categoryId){
        return new ResponseEntity(categoryService.getCategoryById(categoryId),HttpStatus.OK);

    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Category> deleteCategoryById(@PathVariable long categoryId) {
        return new ResponseEntity(categoryService.deleteCategoryById(categoryId),HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@RequestBody CategoryRequest category, @PathVariable long id){
        return new ResponseEntity(categoryService.updateCategory(category,id),HttpStatus.OK);

    }

    @GetMapping("/page/{pageNo}/{pageSize}")
    public Page<Category> getPaginated(@PathVariable int pageNo, @PathVariable int pageSize ) {
        return  categoryService.findPaginated(pageNo,pageSize);
    }

}
