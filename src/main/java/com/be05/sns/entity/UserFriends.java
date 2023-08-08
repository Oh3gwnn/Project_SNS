package com.be05.sns.entity;

import com.be05.sns.entity.embeddedId.FollowId;
import com.be05.sns.entity.embeddedId.FriendId;
import com.be05.sns.entity.embeddedId.LikeId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Data @Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_friends")
public class UserFriends {
    @EmbeddedId
    private FriendId id;

    @MapsId("fromUser")
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "from_user")
    private Users fromUser;

    @MapsId("toUser")
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "to_user")
    private Users toUser;

    private String status;

    public UserFriends newRequestFriend(Users fromUser, Users toUser, FriendId friendId) {
        UserFriends friend = new UserFriends();
        friend.setId(friendId);
        friend.setFromUser(fromUser);
        friend.setToUser(toUser);
        friend.setStatus("신청");
        return friend;
    }
}
