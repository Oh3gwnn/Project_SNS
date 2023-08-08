package com.be05.sns.repository;

import com.be05.sns.entity.UserFollows;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<UserFollows, Long> {
    UserFollows findByFromUser_UsernameAndToUser_Username(String fromUser, String toUser);
}