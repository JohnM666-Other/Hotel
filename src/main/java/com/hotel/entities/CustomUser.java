package com.hotel.entities;

import org.springframework.security.core.userdetails.User;

public class CustomUser extends User {
    public CustomUser(UserEntity user) {
        super(user.getUsername(), user.getPassword(), user.getGrantedAuthorityCollection());
    }
}
