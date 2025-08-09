package com.example.praticeboard.service;


import com.example.praticeboard.dto.UserDto;
import com.example.praticeboard.model.User;
import jakarta.validation.Valid;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    void register(UserDto.@Valid RegisterRequest registerRequest);
    User login(String username, String password);
}
