package com.project.repository;

import com.project.entity.APost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface APostRepository extends JpaRepository<APost, Integer> {

    
    // Custom query to select all TvPosts by userId
    List<APost> findByUserId(int userId);

    // Custom query to find a TvPost by id (this method is already available in JpaRepository)
    Optional<APost> findById(int id);

    // Custom query to delete all TvPosts by userId
    void deleteByUserId(int userId);
}
