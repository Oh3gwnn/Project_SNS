package com.be05.sns.dto;

import com.be05.sns.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@Builder
@AllArgsConstructor
public class UserDto implements UserDetails {

    private Long id;
    private String username;
    private String password;
    private String profileImg;
    private String email;
    private String phone;
    private String token;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    @Override // 계정 만료
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override // 계정 락
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override // 자격 증명 만료
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override // 계정 활성화
    public boolean isEnabled() {
        return true;
    }

    public static UserDto fromEntity(Users user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .profileImg(user.getProfileImg())
                .email(user.getEmail())
                .phone(user.getPhone())
                .token(user.getToken())
                .build();
    }

    // 새로운 User 생성
    public Users newUser(String encodedPassword) {
        Users user = new Users();
        user.setUsername(username);
        user.setPassword(encodedPassword);
        user.setProfileImg(profileImg);
        user.setEmail(email);
        user.setPhone(phone);
        user.setToken(token);
        return user;
    }
}