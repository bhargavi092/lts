package com.LTS.Backend.repository;

import com.LTS.Backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>{

    public User findByEmail(String email);
    List<User> findByRole(String role);
}
