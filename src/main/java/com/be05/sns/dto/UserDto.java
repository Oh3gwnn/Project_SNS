package com.be05.sns.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data @Builder @AllArgsConstructor
public class UserDto implements UserDetails {
    private Long id;
    private String userName;
    private String password;
    private String profile_img;
    private String email;
    private String phone;
    private String token;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getUsername() {
        return this.userName;
    }

    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
