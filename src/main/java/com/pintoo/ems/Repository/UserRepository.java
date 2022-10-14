package com.pintoo.ems.Repository;

import com.pintoo.ems.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findUserById(Long userId);
    Optional<User> findUserByEmail(String email);
}
