package com.pintoo.ems.Service.Impl;

import com.pintoo.ems.Entity.*;
import com.pintoo.ems.Exception.ResourceNotFoundException;
import com.pintoo.ems.Repository.CategoryRepository;
import com.pintoo.ems.Repository.ProductRepository;
import com.pintoo.ems.Request.CategoryRequest;
import com.pintoo.ems.Response.CategoryResponse;
import com.pintoo.ems.Service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private ProductRepository productRepository;

    //post
    public Category addCategory(CategoryRequest categoryRequest)  {
        Category category =new Category();
        category.setCategoryName(categoryRequest.getCategoryName());
        category.setDescription(categoryRequest.getDescription());
        return categoryRepository.save(category);

    }

    //Get all category using CategoryResponse
    public List<CategoryResponse> getAllCategories() {
        List<Category> category = categoryRepository.findAll();
        List<CategoryResponse> categoryResponse = new ArrayList<>();
        for (Category c : category) {
            CategoryResponse c2 = new CategoryResponse();
            c2.setCategoryName(c.getCategoryName());
            c2.setDescription(c.getDescription());

            categoryResponse.add(c2);
        }
        return categoryResponse;
    }

    //Get all category Using Category Entity
    public List<Category> getAllCategory(){
        return categoryRepository.findAll() ;

    }

    //getById
    public Category getCategoryById( long categoryId){
        Optional<Category> category = categoryRepository.findById(categoryId);
        if(category.isPresent()){
            return category.get();
        }else{
            throw new ResourceNotFoundException("Category","Id",categoryId);
        }
        //
        //return userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Users","Id",id));
    }


    //delete
    public ResponseEntity deleteCategoryById( long categoryId){
        Optional<Category> category = categoryRepository.findById(categoryId);
        if(category.isPresent()){
            Optional<List<Product>> productOptional = productRepository.findProductsByCategoryId(categoryId);
            if (productOptional.isPresent()) {

                for (Product pd : productOptional.get()) {
                    productRepository.deleteById(pd.getId());
                }
                categoryRepository.deleteById(categoryId);

            }else{
                throw new RuntimeException("no such products with such categoryId found");
            }
        }else{
            throw new ResourceNotFoundException("Category","Id",categoryId);
        }
        return ResponseEntity.ok("Success");
    }

    public Category updateCategory( CategoryRequest category ,  long categoryId) {
        Category existingCategory = categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("CategoryRequest", "Id", categoryId));

        existingCategory.setCategoryName(category.getCategoryName());
        existingCategory.setDescription(category.getDescription());

        categoryRepository.save(existingCategory);
        return existingCategory;

    }

    @Override
    public Page<Category> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return  categoryRepository.findAll(pageable);

    }
}
