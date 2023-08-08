package com.be05.sns.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data @Entity
@Table(name = "user_follows")
public class UserFollows {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Users fromUser;

    @ManyToOne
    private Users toUser;
}
