package com.be05.sns.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data @Entity
@Table(name = "user")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 필수 입력사항
    @Column(nullable = false) // (unique = true) -> sqlite 자체 error 존재
    private String userName;
    @Column(nullable = false)
    private String password;

    // 선택 입력사항
    @Column(name = "profile_img")
    private String profileImg;
    private String email;
    private String phone;

    // token 저장
    private String token;
}