package com.be05.sns.dto.follow;

import com.be05.sns.entity.Users;
import com.be05.sns.entity.embeddedId.FollowId;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FollowDto {
    private FollowId followId;
    private Users follower;
    private Users following;

    public FollowDto toFollow(Users user, Users targetUser, FollowId followId) {
        return FollowDto.builder()
                .followId(followId)
                .follower(user)
                .following(targetUser)
                .build();
    }
}