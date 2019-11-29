package com.hotel.controllers;

import com.hotel.entities.UserEntity;
import com.hotel.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(AuthController.ROOT)
public class AuthController {

    public static final String ROOT = "/api/user";
    public static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/delete")
    @Secured("ROLE_ADMIN")
    public void delete(@RequestParam String email) {
        userService.deleteByEmail(email);
        logger.info("Removed user with email {}", email);
    }

    @GetMapping
    @Secured("ROLE_ADMIN")
    public List<UserEntity> getAll() {
        logger.info("Get all users");
        return userService.getAll();
    }

    @GetMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public UserEntity getById(@PathVariable("id") Long id) {
        logger.info("Get user by id {}", id);
        return userService.getById(id);
    }

    @PostMapping
    public UserEntity create(@RequestBody UserEntity userEntity) {
        logger.info("Create user {}", userEntity);
        return userService.create(userEntity);
    }

    @PutMapping
    public UserEntity update(@RequestBody UserEntity userEntity) {
        logger.info("Update user {}", userEntity);
        return userService.update(userEntity);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        userService.deleteById(id);
        logger.info("deleted user with id {}", id);
    }

    @PostMapping("/signup")
    public void signup(HttpServletResponse http,
                       @RequestParam String email,
                       @RequestParam String firstname,
                       @RequestParam String lastname,
                       @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) Date birthdate,
                       @RequestParam Character sex,
                       @RequestParam String password) throws IOException {
        UserEntity user = new UserEntity(firstname, lastname, email, birthdate, sex, new BCryptPasswordEncoder().encode(password));
        userService.create(user);
        logger.info("Sign up: {}", user);
        http.sendRedirect("/hotels");
    }

    @PostMapping("/profile-edit")
    @Secured("ROLE_USER")
    public void edit(HttpServletResponse http,
                     @RequestParam String email,
                     @RequestParam String firstname,
                     @RequestParam String lastname,
                     @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) Date birthdate,
                     @RequestParam Character sex) throws IOException {
        UserEntity user = userService.getByEmail(email);

        if(!user.getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
            http.sendError(400);
            return;
        }

        user.setFirstname(firstname);
        user.setSecondname(lastname);
        user.setEmail(email);
        user.setBirthDate(birthdate);
        user.setSex(sex);

        userService.update(user);
        logger.info("Edit user with email {}", email);
        http.sendRedirect("/hotels");
    }
}
