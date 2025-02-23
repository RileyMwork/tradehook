package com.project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.repository.APostRepository;
import com.project.entity.APost;

@Service
public class APostService {

    private APostRepository aPostRepository;

    @Autowired
    public APostService(APostRepository aPostRepository) {
        this.aPostRepository = aPostRepository;
    }

    // Create a new APost
    public APost createNewAPost(APost aPost) {
        return aPostRepository.save(aPost);  // save method automatically handles insert and update
    }

    // Select APost by Id
    public Optional<APost> selectAPostById(int id) {
        return aPostRepository.findById(id);  // findById already returns Optional
    }

    // Select all APosts by userId
    public List<APost> selectAPostsByUserId(int userId) {
        return aPostRepository.findByUserId(userId);  // findByUserId is custom query
    }

    // Delete APost by id
    public void deleteAPostById(int id) {
        Optional<APost> aPost = aPostRepository.findById(id);  // findById returns Optional
        if (aPost.isPresent()) {
            aPostRepository.deleteById(id);  // deleteById is built into JpaRepository
            System.out.println("APost with id " + id + " deleted!");
        } else {
            System.out.println("APost with id " + id + " not found.");
        }
    }

    // Delete all APosts by userId
    public void deleteAPostByUserId(int userId) {
        List<APost> aPosts = aPostRepository.findByUserId(userId);  // findByUserId is custom query
        if (!aPosts.isEmpty()) {
            aPostRepository.deleteByUserId(userId);  // deleteByUserId is custom query
            System.out.println("All APosts for user with id " + userId + " deleted!");
        } else {
            System.out.println("No APosts found for user with id " + userId);
        }
    }
}
