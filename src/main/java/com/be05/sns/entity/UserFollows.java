package com.be05.sns.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data @Entity
@Table(name = "user_follows")
public class UserFollows {
    @Id
    @ManyToOne
    private Users userId;

    @ManyToOne
    private Users follower;

    @ManyToOne
    private Users following;
}
