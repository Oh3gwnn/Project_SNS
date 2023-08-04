package com.be05.sns.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data @Entity
@Table(name = "user_friends")
public class UserFriends {
    @Id
    @ManyToOne
    private Users userId;

    @ManyToOne
    @JoinColumn(name = "from_user")
    private Users fromUsers;

    @ManyToOne
    @JoinColumn(name = "to_user")
    private Users toUsers;
}
