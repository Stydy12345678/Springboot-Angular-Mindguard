package com.mindguard.backend.repository;

import com.mindguard.backend.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    // Check if username exists
    boolean existsByUsername(String username);

    // Find user by username only
    User findByUsername(String username);

    

	
}
