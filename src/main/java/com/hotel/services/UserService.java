package com.hotel.services;

import com.hotel.entities.CustomUser;
import com.hotel.entities.UserEntity;
import com.hotel.repositories.RoleRepository;
import com.hotel.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public UserEntity getUserInfo(String username) {
        UserEntity user = userRepository.getByUsername(username);
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_USER");
        user.getGrantedAuthorityCollection().add(grantedAuthority);
        return user;
    }

    @Override
    public CustomUser loadUserByUsername(final String username) throws UsernameNotFoundException {
        UserEntity user = null;
        try {
            user = getUserInfo(username);
            CustomUser cu = new CustomUser(user);
            return cu;
        } catch(Exception e) {
            e.printStackTrace();
            throw new UsernameNotFoundException("User " + username + " was not found");
        }
    }
}
