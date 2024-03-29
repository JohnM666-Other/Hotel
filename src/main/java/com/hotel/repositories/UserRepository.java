package com.hotel.repositories;

import com.hotel.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity getByEmail(String email);
    void deleteByEmail(String email);
}
