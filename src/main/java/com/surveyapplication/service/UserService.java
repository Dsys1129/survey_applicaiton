package com.surveyapplication.service;

import com.surveyapplication.domain.User;
import com.surveyapplication.dto.request.UserRequestDTO;
import com.surveyapplication.mapper.UserMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserMapper userMapper;

    @Transactional
    public Map<String, String> signup(UserRequestDTO requestDTO) {
        User foundUser = userMapper.findByUserId(requestDTO.getUserId());

        if (foundUser != null) {
            throw new IllegalArgumentException("중복된 유저 아이디");
        }
        User user = User.createRegularMember(requestDTO.getUserId(), requestDTO.getPassword());
        userMapper.save(user);

        return Collections.singletonMap("message", "회원 가입 성공");
    }

    public Map<String, String> login(UserRequestDTO requestDTO, HttpServletRequest httpServletRequest) {
        log.info("login");
        User foundUser = userMapper.findByUserIdAndPassword(requestDTO.getUserId(), requestDTO.getPassword());

        if (foundUser == null) {
            throw new IllegalArgumentException("잘못된 로그인 입력");
        }

        HttpSession session = httpServletRequest.getSession();
        session.setAttribute("user", foundUser);
        return Collections.singletonMap("message", "로그인 성공");
    }
}
