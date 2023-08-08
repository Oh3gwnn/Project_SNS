package com.be05.sns.controller;

import com.be05.sns.dto.ResponseDto;
import com.be05.sns.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/{username}")
public class FollowController {
    private final ResponseDto response;
    private final FollowService followService;

    @PostMapping("/follow")
    public ResponseDto createComment(@PathVariable("username") String username,
                                     Authentication authentication) {
        followService.requestFollow(username, authentication);
        return response.toMessage(username +"님을 팔로우 하셨습니다.");
    }
}
