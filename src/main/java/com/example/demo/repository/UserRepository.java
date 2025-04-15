package com.example.demo.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.User;  // インポート追加  


public interface UserRepository extends JpaRepository<User, Long> {}  
