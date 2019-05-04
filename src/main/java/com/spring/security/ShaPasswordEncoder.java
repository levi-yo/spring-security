package com.spring.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 
 * @author yun-yeoseong
 *
 */
@Component
public class ShaPasswordEncoder implements PasswordEncoder{

	@Override
	public String encode(CharSequence rawPassword) {
		// TODO Auto-generated method stub
		System.out.println("ShaPasswordEncoder.encode ::::");
		return Crypto.sha256(rawPassword.toString());
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		// TODO Auto-generated method stub
		System.out.println("ShaPasswordEncoder.matches ::::");
		return Crypto.sha256(rawPassword.toString()).equals(encodedPassword);
	}
	
	
}
