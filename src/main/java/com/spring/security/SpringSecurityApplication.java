package com.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.spring.security.model.ChatbotUser;
import com.spring.security.repository.UserRepository;

@SpringBootApplication
public class SpringSecurityApplication implements CommandLineRunner{
	
	@Autowired UserRepository repository;
	@Autowired PasswordEncoder encoder;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityApplication.class, args);
		
	}
	
	@Transactional
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		ChatbotUser user = new ChatbotUser();
		user.setId(1l);
		user.setUsername("yeoseong_yoon");
		user.setPassword(encoder.encode("rnb1234!@"));
		
		repository.save(user);
	}

}
