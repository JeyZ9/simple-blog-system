package com.app.simpleblogsystem.repository;

import com.app.simpleblogsystem.dto.save.SaveDTO;
import com.app.simpleblogsystem.models.Save;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SaveRepository extends JpaRepository<Save, Long> {
    List<Save> findSaveByUserId(Long userId);
    Optional<Save> findBySaveIdAndUserIdAndBlogId(Long saveId, Long userId, Long blogId);
}
