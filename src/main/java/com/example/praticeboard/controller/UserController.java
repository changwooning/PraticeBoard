package com.example.praticeboard.controller;

import com.example.praticeboard.dto.UserDto;
import com.example.praticeboard.model.User;
import com.example.praticeboard.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    // 생성자 주입 방식
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입 : 사용자 이름, 비밀번호, 이름, 이메일 정보로 회원가입
    // 회원가입 폼
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("registerRequest", new UserDto.RegisterRequest());
        return "user/register";
    }

    // 회원가입 처리
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registerRequest") UserDto.RegisterRequest registerRequest,
                           BindingResult bindingResult,
                           Model model){
        if(bindingResult.hasErrors()){
            return "user/register";
        }

        try{
            userService.register(registerRequest);
            return "redirect:/user/login?registered";
        }catch(IllegalArgumentException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "user/register";
        }
    }

    // 로그인 : 사용자 이름과 비밀번호로 로그인
    // 로그인 폼
    @GetMapping("/login")
    public String loginForm(Model model){
        model.addAttribute("loginRequest",new UserDto.LoginRequest());
        return "user/login";
    }

    // 로그인 처리
    @PostMapping("/login")
    public String loginForm(@Valid @ModelAttribute("loginRequest") UserDto.LoginRequest loginRequest,
                            BindingResult bindingResult,
                            Model model, HttpSession session){
        if(bindingResult.hasErrors()){
            return "user/login";
        }
        User user = userService.login(loginRequest.getUsername(),loginRequest.getPassword());
        if(user == null){
            model.addAttribute("errorMessage","아이디 또는 비밀번호가 올바르지 않습니다.");
            return "user/login";
        }
        session.setAttribute("username",user.getUsername());
        return "redirect:/board/list";
    }

    // 로그아웃 : 세션 무효화를 통한 로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/user/login?logout";
    }


}
