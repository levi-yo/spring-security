package com.spring.security.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.ObjectUtils;

import com.spring.security.model.ChatbotUser;
import com.spring.security.service.UserDetailsServiceImpl;
import com.spring.security.service.UserService;

/*
 * SecurityContext 안에 채워진 Authentication 객체는
 * 인증 시 수집한 모든 사용자 정보를 나타낸다.
 * 
 * 즉, SecurityContextHolder를 사용하여 현재 로그인한 사용자에 대한 정보를 얻을 수 있다.
 */
public class SpringSecurityUserContext implements UserContext {
	
	private UserService userService;
	private UserDetailsServiceImpl userDetailService;

	public SpringSecurityUserContext(UserService userService, 
									 UserDetailsServiceImpl userDetailService) {
		this.userService = userService;
		this.userDetailService = userDetailService;
	}
	
	@Override
	public ChatbotUser getCurrentUser() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		
		if(ObjectUtils.isEmpty(authentication)) {
			return null;
		}
		
		User user = (User) authentication.getPrincipal();
		
		String userId = user.getUsername();
		
		if(StringUtils.isEmpty(userId)) {
			return null;
		}
		
		ChatbotUser currentUser = userService.findByUserId(userId);
		
		if(ObjectUtils.isEmpty(currentUser)) {
			throw new IllegalStateException();
		}
		
		return currentUser;
	}

	@Override
	public void setCurrentUser(ChatbotUser user) {
		// TODO Auto-generated method stub
	}

}
