package com.be05.sns.dto.user;

import com.be05.sns.entity.UserFriends;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestStatus {
    private String username;
    private String status;

    public static RequestStatus requestStatusInfo(UserFriends friend) {
        return RequestStatus.builder()
                .username(friend.getFromUser().getUsername())
                .status(friend.getStatus())
                .build();
    }
}