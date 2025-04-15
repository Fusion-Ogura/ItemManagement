package com.example.demo.service;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.User;  // 追加  
import com.example.demo.repository.UserRepository;  // 追加    

@Service  
public class UserService {
	private final UserRepository repository;
	
	public UserService(UserRepository repository){
		this.repository =repository;
	} 
    // 追加実装  
    public List<User> findAll() {  
        return repository.findAll();  // JpaRepositoryのメソッドを利用  
    }  
    public User createUser(User user) {  
        return repository.save(user);  
    }  
}  