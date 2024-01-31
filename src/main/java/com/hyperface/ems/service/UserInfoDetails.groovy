package com.hyperface.ems.service;

import com.hyperface.ems.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

class UserInfoDetails implements UserDetails {

    private String name;
    private String password;
    private List<GrantedAuthority> authorities;

    UserInfoDetails(User userInfo) {
        name = userInfo.getUsername();
        password = userInfo.getPassword();
        authorities = Arrays.stream(userInfo.getRoles().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    String getPassword() {
        return password;
    }

    @Override
    String getUsername() {
        return name;
    }

    @Override
    boolean isAccountNonExpired() {
        return true;
    }

    @Override
    boolean isAccountNonLocked() {
        return true;
    }

    @Override
    boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    boolean isEnabled() {
        return true;
    }
}
