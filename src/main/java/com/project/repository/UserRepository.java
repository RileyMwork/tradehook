package com.project.repository;

import com.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // Custom method to find a user by email
    Optional<User> findByEmail(String email);

    Optional<User> findByTradehookApiKey(String tradehookApiKey);

    // Update Tradehook API key for a user
    @Modifying
    @Query("UPDATE User u SET u.tradehookApiKey = :tradehookApiKey WHERE u.id = :id")
    int updateTradehookApiKey(@Param("id") Integer id, @Param("tradehookApiKey") String tradehookApiKey);

    @Modifying
    @Query("UPDATE User u SET u.alpacaApiKey = :alpacaApiKey, u.alpacaSecretKey = :alpacaSecretKey WHERE u.id = :id")
    int updateAlpacaApiKeys(@Param("id") Integer id, @Param("alpacaApiKey") String alpacaApiKey, @Param("alpacaSecretKey") String alpacaSecretKey);

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

    // default Optional<User> selectUserByTradehookApiKey(String tradehookApiKey) {
    //     return findByTradehookApiKey(tradehookApiKey);
    // }
}
