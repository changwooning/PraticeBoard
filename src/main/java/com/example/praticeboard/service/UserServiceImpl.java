package com.example.praticeboard.service;

import com.example.praticeboard.dto.UserDto;
import com.example.praticeboard.model.User;
import com.example.praticeboard.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void register(@Valid UserDto.RegisterRequest registerRequest) {
        // 중복체크
        userRepository.findByUsername(registerRequest.getUsername())
                .ifPresent(user -> {
                    throw new IllegalArgumentException("이미 존재하는 사용자 이름입니다.");
                });
        // 이메일 중복 체크
        userRepository.findAll().stream()
                .filter(u -> u.getEmail().equals(registerRequest.getEmail())).findAny()
                .ifPresent(user -> { throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
                });

        User user = new User(registerRequest.getUsername(), registerRequest.getPassword(), registerRequest.getName(),registerRequest.getEmail());
        userRepository.save(user);
    }

    @Override
    public User login(String username, String password) {
        return userRepository.findByUsername(username)
                .filter(u -> u.getPassword().equals(password))
                .orElse(null);
    }


}
