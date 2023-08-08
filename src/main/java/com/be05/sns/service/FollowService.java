package com.be05.sns.service;

import com.be05.sns.dto.follow.FollowDto;
import com.be05.sns.entity.UserFollows;
import com.be05.sns.entity.Users;
import com.be05.sns.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final GetObjService getObj;
    private final ModelMapper modelMapper;
    private final FollowRepository followRepository;

    // 팔로우/언팔로우
    public String requestFollow(String username, Authentication authentication) {
        Users authUser = getObj.getUser(authentication.getName());
        Users targetUser = getObj.getUser(username);

        if (authUser.getPassword().equals(targetUser.getPassword()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        UserFollows follows = getObj.getFollow(authUser.getUsername(), targetUser.getUsername());

        if (follows == null) {
            FollowDto dto = new FollowDto().toFollow(authUser, targetUser);
            UserFollows follow = modelMapper.map(dto, UserFollows.class);
            followRepository.save(follow);
            return "님을 팔로우 하셨습니다.";
        } else {
            followRepository.delete(follows);
            return "님을 언팔로우 하셨습니다.";
        }

    }

    // 상대방이 건 팔로우를 언팔로우
    public void requestUnfollow(String username, Authentication authentication) {
        Users authUser = getObj.getUser(authentication.getName());
        Users targetUser = getObj.getUser(username);

        if (authUser.getPassword().equals(targetUser.getPassword()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        UserFollows follows = getObj.getFollow(targetUser.getUsername(), authUser.getUsername());
        followRepository.delete(follows);
    }
}
