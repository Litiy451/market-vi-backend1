package com.example.marketvibackend1.repository;

import com.example.marketvibackend1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}
