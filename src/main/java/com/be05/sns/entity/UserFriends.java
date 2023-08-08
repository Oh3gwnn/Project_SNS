package com.be05.sns.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data @Entity
@Table(name = "user_friends")
public class UserFriends {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "from_user")
    private Users fromUsers;

    @ManyToOne
    @JoinColumn(name = "to_user")
    private Users toUsers;
}
