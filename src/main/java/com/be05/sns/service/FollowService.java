package com.be05.sns.service;

import com.be05.sns.dto.follow.FollowDto;
import com.be05.sns.dto.user.RequestStatus;
import com.be05.sns.entity.UserFollows;
import com.be05.sns.entity.UserFriends;
import com.be05.sns.entity.Users;
import com.be05.sns.entity.embeddedId.FollowId;
import com.be05.sns.entity.embeddedId.FriendId;
import com.be05.sns.repository.FollowRepository;
import com.be05.sns.repository.FriendRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final GetObjService getObj;
    private final ModelMapper modelMapper;
    private final FollowRepository followRepository;
    private final FriendRepository friendRepository;

    // 팔로우/언팔로우
    public String requestFollow(String username, Authentication authentication) {
        Users authUser = getObj.getUser(authentication.getName());
        Users targetUser = getObj.getUser(username);

        if (authUser.getPassword().equals(targetUser.getPassword()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        FollowId followId = new FollowId(authUser.getId(), targetUser.getId());
        UserFollows follows = getObj.getFollow(authUser.getId(), targetUser.getId());

        if (follows == null) {
            FollowDto dto = new FollowDto().toFollow(authUser, targetUser, followId);
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

        UserFollows follows = getObj.getFollow(targetUser.getId(), authUser.getId());
        followRepository.delete(follows);
    }

    // 친구 신청
    public void applyFriend(String username, Authentication authentication) {
        Users authUser = getObj.getUser(authentication.getName());
        Users targetUser = getObj.getUser(username);

        if (authUser.getPassword().equals(targetUser.getPassword()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        FriendId friendId = new FriendId(authUser.getId(), targetUser.getId());
        UserFriends friends = getObj.getFriend(authUser.getId(), targetUser.getId());

        if (friends == null) {
            UserFriends request = new UserFriends().newRequestFriend(authUser, targetUser, friendId);
            friendRepository.save(request);
        }
    }

    public List<RequestStatus> viewStatus(Authentication authentication) {
        List<UserFriends> requestList = friendRepository.findAllByToUser_Username(authentication.getName());
        return requestList.stream()
                .map(RequestStatus::requestStatusInfo).toList();
    }

    // 수락 및 거절
    public String changeStatus(String username, String status, Authentication authentication) {
        Users authUser = getObj.getUser(authentication.getName());
        Users targetUser = getObj.getUser(username);

        if (authUser.getPassword().equals(targetUser.getPassword()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        FriendId friendId = new FriendId(authUser.getId(), targetUser.getId()); // 반대로 되어있음
        UserFriends friends = getObj.getFriend(targetUser.getId(), authUser.getId());

        if (Objects.equals(status, "수락") && friends.getStatus().equals("신청")) {
            friends.setStatus("친구");
            friendRepository.save(friends);
            UserFriends request = new UserFriends().newRequestFriend(authUser, targetUser, friendId);
            request.setStatus("친구");
            friendRepository.save(request);
            return "수락";
        }
        else if (Objects.equals(status, "거절") && friends.getStatus().equals("신청")) {
            friendRepository.delete(friends);
            return "거절";
        }
        return username;
    }
}