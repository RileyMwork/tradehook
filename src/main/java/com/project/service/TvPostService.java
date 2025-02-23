package com.project.service;

import com.project.entity.TvPost;
import com.project.repository.TvPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TvPostService {

    private final TvPostRepository tvPostRepository;

    @Autowired
    public TvPostService(TvPostRepository tvPostRepository) {
        this.tvPostRepository = tvPostRepository;
    }

    // Create a new TvPost
    public TvPost createNewTvPost(TvPost tvPost) {
        return tvPostRepository.save(tvPost); // Uses JpaRepository to save the entity
    }

    // Retrieve TvPost by ID
    public TvPost selectTvPostById(int id) {
        return tvPostRepository.findById(id).orElse(null); // Uses JpaRepository's findById
    }

    // Retrieve all TvPosts by userId
    public List<TvPost> selectTvPostsByUserId(int userId) {
        return tvPostRepository.findByUserId(userId); // Custom query in repository
    }

    // Delete TvPost by ID
    public void deleteTvPostById(int id) {
        if (tvPostRepository.existsById(id)) { // Check if the TvPost exists
            tvPostRepository.deleteById(id);
            System.out.println("TvPost with id " + id + " deleted!");
        } else {
            System.out.println("No TvPost found with id " + id);
        }
    }

    // Delete all TvPosts by userId
    public void deleteTvPostByUserId(int userId) {
        if (tvPostRepository.findByUserId(userId).isEmpty()) {
            System.out.println("No TvPosts found for user with id " + userId);
        } else {
            tvPostRepository.deleteByUserId(userId);
            System.out.println("All TvPosts for user with id " + userId + " deleted!");
        }
    }
}
