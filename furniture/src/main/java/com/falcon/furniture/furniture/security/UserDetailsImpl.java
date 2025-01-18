package com.falcon.furniture.furniture.security;

import com.falcon.furniture.furniture.dto.AuthenticatedUserDto;
import com.falcon.furniture.furniture.dto.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Data
public class UserDetailsImpl implements UserDetails {
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsImpl.class);

    private static final Long serialVersionUID = 1L;

    private String id;
    private String username;
    private String email;
    @JsonIgnore
    private String password;
    private GrantedAuthority authority;

    public UserDetailsImpl(
            String id,
            String username,
            String email,
            String password,
            GrantedAuthority authority
    ){
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authority = authority;
    }
    public static UserDetailsImpl build(AuthenticatedUserDto user){

        GrantedAuthority authority = new SimpleGrantedAuthority(UserRole.getByDescription(user.getUserRole()).getDescription());

        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                authority
        );
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(this.authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
