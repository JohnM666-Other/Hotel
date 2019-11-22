package com.hotel.entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class CustomUser extends User {
    public CustomUser(UserEntity user, List<GrantedAuthority> roles) {
        super(user.getEmail(), user.getPassword(), roles);
    }
}
