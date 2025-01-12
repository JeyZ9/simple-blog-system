package com.app.simpleblogsystem.repository;

import com.app.simpleblogsystem.models.Save;
import com.app.simpleblogsystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SaveRepository extends JpaRepository<Save, Long> {
    List<Save> findSaveByUsersId(Long userId);
//    Optional<Save> findBySaveIdAndUserIdAndBlogId(Long id, Long userId, Long blogId);
    Optional<Save> findByUsers(User user);
}
