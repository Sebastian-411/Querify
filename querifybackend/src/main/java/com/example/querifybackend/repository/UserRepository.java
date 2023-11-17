package com.example.querifybackend.repository;


import com.example.querifybackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
}
