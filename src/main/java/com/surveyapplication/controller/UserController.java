package com.surveyapplication.controller;

import com.surveyapplication.dto.request.UserRequestDTO;
import com.surveyapplication.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @ResponseBody
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequestDTO requestDTO, HttpServletRequest httpServletRequest) {
        Map<String, String> result = userService.login(requestDTO, httpServletRequest);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ResponseBody
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserRequestDTO requestDTO) {
        Map<String, String> result = userService.signup(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
