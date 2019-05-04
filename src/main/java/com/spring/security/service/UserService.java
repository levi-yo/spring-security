package com.spring.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.security.model.ChatbotUser;
import com.spring.security.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired private UserRepository repository;
	
	public ChatbotUser findByUserId(String userId) {
		
		return repository.findByUsername(userId);
		
	}
}
