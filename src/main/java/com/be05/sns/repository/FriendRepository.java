package com.be05.sns.repository;

import com.be05.sns.entity.UserFriends;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepository extends JpaRepository<UserFriends, Long> {
    UserFriends findByFromUser_IdAndToUser_Id(Long fromUser, Long toUser);
    List<UserFriends> findAllByToUser_Username(String username);
}