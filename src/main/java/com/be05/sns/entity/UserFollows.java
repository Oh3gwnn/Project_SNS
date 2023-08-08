package com.be05.sns.entity;

import com.be05.sns.entity.embeddedId.FollowId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Data @Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_follows")
public class UserFollows {
    @EmbeddedId
    private FollowId id;

    @MapsId("follower")
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "follower_id")
    private Users follower;

    @MapsId("following")
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "following_id")
    private Users following;
}
