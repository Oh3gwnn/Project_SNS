package com.be05.sns.controller;

import com.be05.sns.dto.ResponseDto;
import com.be05.sns.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class FollowController {
    private final ResponseDto response;
    private final FollowService followService;

    @PostMapping("/follow/{username}")
    public ResponseDto followUser(@PathVariable("username") String username,
                                     Authentication authentication) {
        return response.toMessage(username +
                followService.requestFollow(username, authentication));
    }

    @PostMapping("/unfollow/{username}")
    public ResponseDto unfollowUser(@PathVariable("username") String username,
                                    Authentication authentication) {
        followService.requestUnfollow(username, authentication);
        return response.toMessage(username +"님의 팔로우를 해제하셨습니다.");
    }
}
