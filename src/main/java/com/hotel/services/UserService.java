package com.hotel.services;

import com.hotel.entities.CustomUser;
import com.hotel.entities.Role;
import com.hotel.entities.UserEntity;
import com.hotel.repositories.RoleRepository;
import com.hotel.repositories.UserRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public UserEntity getByEmail(String email) {
        UserEntity user = userRepository.getByEmail(email);
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_ADMIN");
        return user;
    }

    public UserEntity update(UserEntity user) {
        UserEntity oldUser = userRepository.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(oldUser != null) {
            oldUser.setFirstname(user.getFirstname());
            oldUser.setSecondname(user.getSecondname());
            oldUser.setBirthDate(user.getBirthDate());
            oldUser.setEmail(user.getEmail());
            oldUser.setSex(user.getSex());
            return userRepository.save(oldUser);
        } else {
            return null;
        }
    }

    public void deleteByEmail(String email) {
        userRepository.deleteByEmail(email);
    }

    public UserEntity create(UserEntity user) {
        return userRepository.save(user);
    }

    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }

    public UserEntity getById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "UserEntity"));
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public List<GrantedAuthority> getRoles(UserEntity user) {
        List<GrantedAuthority> roles = new ArrayList<>();

        for(Role role : user.getRoles())
            roles.add(new SimpleGrantedAuthority(role.getRole()));

        return roles;
    }

    @Override
    public CustomUser loadUserByUsername(final String username) throws UsernameNotFoundException {
        UserEntity user = null;
        try {
            user = getByEmail(username);
            CustomUser cu = new CustomUser(user, getRoles(user));
            return cu;
        } catch(Exception e) {
            e.printStackTrace();
            throw new UsernameNotFoundException("User " + username + " was not found");
        }
    }
}
