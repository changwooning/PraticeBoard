package com.example.praticeboard.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

// 사용자 DTO 클래스
//  - 클라이언트와 서버 간의 데이터 전송에 포함
//  - 입력 유효성 검사를 위한 어노테이션이 포함되어있다.
public class UserDto {

    public static class RegisterRequest{

        @NotBlank
        private String username;

        @NotBlank
        private String password;

        @NotBlank
        private String name;

        @NotBlank
        private String email;

        public RegisterRequest(){}

        public RegisterRequest(String username, String password, String name, String email) {
            this.username = username;
            this.password = password;
            this.name = name;
            this.email = email;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }


    public static class LoginRequest{

        @NotBlank
        private String username;
        @NotBlank
        private String password;

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

}
