package com.pintoo.ems.Service;

import com.pintoo.ems.Entity.Category;
import com.pintoo.ems.Request.CategoryRequest;
import com.pintoo.ems.Response.CategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {

    Category addCategory(CategoryRequest categoryRequest);
    List<CategoryResponse> getAllCategories();
    List<Category> getAllCategory();
    Category getCategoryById( long categoryId);
    ResponseEntity deleteCategoryById( long categoryId);
    Category updateCategory( CategoryRequest category ,  long categoryId);
    Page<Category> findPaginated(int pageNo, int pageSize);

}
