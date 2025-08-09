package com.example.praticeboard.repository;


import com.example.praticeboard.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> findAll();

    Optional<User> findByUsername(String username);

    User save(User user);
}
