package com.be05.sns.repository;

import com.be05.sns.entity.UserFollows;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<UserFollows, Long> {
    UserFollows findByFollower_IdAndFollowing_Id(Long follower, Long following);
    List<UserFollows> findByFollower_Id(Long userId);
}