package com.example1.login.repository;

import com.example1.login.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

// This interface extends MongoRepository to get database methods for the User class
public interface UserRepository extends MongoRepository<User, String> {

    // We add this custom method to find a user by their username.
    // Spring Data MongoDB is smart enough to create the query for us!
    Optional<User> findByUsername(String username);
}