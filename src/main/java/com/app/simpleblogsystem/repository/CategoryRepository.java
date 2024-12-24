package com.app.simpleblogsystem.repository;

import com.app.simpleblogsystem.models.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Categories, Long> {
//    List<Categories> findByCategoryName(String categoryName);
}
