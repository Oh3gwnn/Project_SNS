package com.be05.sns.service;

import com.be05.sns.dto.follow.FollowDto;
import com.be05.sns.entity.UserFollows;
import com.be05.sns.entity.Users;
import com.be05.sns.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final GetObjService getObj;
    private final ModelMapper modelMapper;
    private final FollowRepository followRepository;

    public void requestFollow(String username, Authentication authentication) {
        Users authUser = getObj.getUser(authentication.getName());
        Users targetUser = getObj.getUser(username);

        //TODO: 자기자신은 팔로우 못함

        FollowDto dto = new FollowDto().toFollow(authUser, targetUser);
        UserFollows follow = modelMapper.map(dto, UserFollows.class);
        followRepository.save(follow);
    }
}
