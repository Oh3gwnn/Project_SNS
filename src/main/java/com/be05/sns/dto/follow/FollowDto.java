package com.be05.sns.dto.follow;

import com.be05.sns.entity.Users;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FollowDto {
    private Users fromUser;
    private Users toUser;

    public FollowDto toFollow(Users user, Users targetUser) {
        return FollowDto.builder()
                .fromUser(user)
                .toUser(targetUser)
                .build();
    }
}