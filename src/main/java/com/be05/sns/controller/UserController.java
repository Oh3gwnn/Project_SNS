package com.be05.sns.controller;

import com.be05.sns.dto.ResponseDto;
import com.be05.sns.dto.UserDto;
import com.be05.sns.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final ResponseDto response;
    private final UserService userService;

    // 1. 로그인(토큰 생성)
    @PostMapping("/login")
    public ResponseDto toLogin(@RequestBody UserDto dto) {
        userService.toLoginSaveJwt(dto);
        return response.toMessage("로그인되었습니다.");
    }

    // 2. 회원가입
    @PostMapping("/register")
    public ResponseDto signUp(@RequestBody UserDto dto) {
        userService.createUser(dto);
        return response.toMessage("회원가입 성공");
    }
}
