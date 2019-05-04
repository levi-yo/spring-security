package com.spring.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.security.model.ChatbotUser;

public interface UserRepository extends JpaRepository<ChatbotUser, Long>{
	
	public ChatbotUser findByUsername(String username);
	
}
