package com.app.simpleblogsystem.repository;

import com.app.simpleblogsystem.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
//    List<Categories> findByCategoryName(String categoryName);
}
