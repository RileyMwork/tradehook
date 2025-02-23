package com.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.project.entity.TvPost;
import java.util.List;
import java.util.Optional;

@Repository
public interface TvPostRepository extends JpaRepository<TvPost, Integer> {

    // Custom query to select all TvPosts by userId
    List<TvPost> findByUserId(int userId);

    // Custom query to find a TvPost by id (this method is already available in JpaRepository)
    Optional<TvPost> findById(int id);

    // Custom query to delete all TvPosts by userId
    void deleteByUserId(int userId);
}
