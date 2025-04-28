package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    // ユーザー名でユーザーを取得するクエリメソッドを追加できます
    User findByUsername(String username);
}