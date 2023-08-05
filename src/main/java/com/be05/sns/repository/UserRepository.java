package com.be05.sns.repository;

import com.be05.sns.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(String userName);
    Boolean existsByUsername(String userName);
}