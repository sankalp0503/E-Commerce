package com.pintoo.ems.Repository;

import com.pintoo.ems.Entity.Address;
import com.pintoo.ems.Entity.Category;
import com.pintoo.ems.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    Optional<Category> findCategoryById(Long categoryId);

}
