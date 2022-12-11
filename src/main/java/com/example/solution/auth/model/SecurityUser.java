package com.example.solution.auth.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class SecurityUser implements UserDetails {
    private String username;
    private String password;
    private List<GrantedAuthority> grantedAuthorityList;

    public static SecurityUser builder() {
        return new SecurityUser();
    }

    public SecurityUser withGrantedAuthorityList(List<GrantedAuthority> grantedAuthorityList) {
        this.grantedAuthorityList = grantedAuthorityList;
        return this;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.grantedAuthorityList;
    }

    public SecurityUser withPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public SecurityUser withUserName(String userName) {
        this.username = userName;
        return this;
    }

    @Override
    public String getUsername() {
        return this.username;
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