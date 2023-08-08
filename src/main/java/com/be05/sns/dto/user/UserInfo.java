package com.be05.sns.dto.user;

import com.be05.sns.entity.Users;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfo {
    private String username;
    private String profileImg;

    public static UserInfo userInfo(Users user) {
        return UserInfo.builder()
                .username(user.getUsername())
                .profileImg(user.getProfileImg())
                .build();
    }
}