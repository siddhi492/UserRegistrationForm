package com.form.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.form.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
