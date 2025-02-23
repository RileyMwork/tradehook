package com.project.repository;

import com.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    
    // Custom method to find a user by email
    Optional<User> findByEmail(String email);

    // Custom method to update user information
    default Optional<User> updateUserInfo(int id, String email, String password) {
        Optional<User> userOpt = findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setEmail(email);
            user.setPassword(password);
            return Optional.of(save(user));
        }
        return Optional.empty();
    }

    // Custom method to update the tradehook API key
    default Optional<User> updateUserTradehookApiKey(int id, String tradehookApiKey) {
        Optional<User> userOpt = findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setTradehookApiKey(tradehookApiKey);
            return Optional.of(save(user));
        }
        return Optional.empty();
    }

    // Custom method to update Alpaca API keys
    default Optional<User> updateUserAlpacaApiKeys(int id, String alpacaApiKey, String alpacaSecretKey) {
        Optional<User> userOpt = findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setAlpacaApiKey(alpacaApiKey);
            user.setAlpacaSecretKey(alpacaSecretKey);
            return Optional.of(save(user));
        }
        return Optional.empty();
    }

    // Custom method to delete user by ID
    default void deleteUserById(int id) {
        deleteById(id);
    }

    // Custom method to get all users
    default List<User> selectAllUsers() {
        return findAll();
    }
    
    // Custom method to select a user by ID
    default Optional<User> selectUserById(int id) {
        return findById(id);
    }
}
