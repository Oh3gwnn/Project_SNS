package com.be05.sns.service;

import com.be05.sns.entity.Users;
import com.be05.sns.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindUser {
    private final UserRepository userRepository;

    public Users getUsers(String userName) {
        return userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException(userName));
    }
}
