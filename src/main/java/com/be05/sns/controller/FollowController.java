package com.be05.sns.controller;

import com.be05.sns.dto.ResponseDto;
import com.be05.sns.dto.user.RequestStatus;
import com.be05.sns.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/friend/{username}")
    public ResponseDto requestFriend(@PathVariable("username") String username,
                                     Authentication authentication) {
        followService.applyFriend(username, authentication);
        return response.toMessage(username +"님에게 친구 신청을 보냈습니다.");
    }

    @GetMapping("/friend")
    public List<RequestStatus> viewFriendStatus(Authentication authentication) {
        return followService.viewStatus(authentication);
    }

    @PutMapping("/friend/status/{username}")
    public ResponseDto changeFriendStatus(@PathVariable("username") String username,
                                          @RequestParam("status") String status,
                                          Authentication authentication) {
        return response.toMessage(username +"님의 친구 신청을 "+
                followService.changeStatus(username, status, authentication) + "하셨습니다.");
    }
}
