package com.spring.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	
	private boolean postOnly = true;
	
	public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
		super.setAuthenticationManager(authenticationManager);
	}
	
	/*
	 * 1)HttpServletRequest 메서드로부터 사용자명,패스워드,도메인을 획득한다.
	 * 2)Http 요청에서 얻은 정보로 UserpasswordAuthenticationToken 객체를 생성한다.
	 * 3)스프링 시큐리티가 토큰의 유효성을 검사하도록 요청하며, 이 작업은 DaoAuthenticationProvider에게 위임된다.
	 * 4)토큰의 유효성이 검증되면 새로운 계정을 생성한 후, 사용자를 인증할 때와 마찬가지로 토큰의 슈퍼클래스는 DaoAuthenticationProvier
	 *   SeucrityContextHolder에 반환한 인증 결과를 설정한다.
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		System.out.println("JwtAuthenticationFilter.attemptAuthentication ::::");
		/*
		 * POST로 넘어왔는지 체크
		 */
		if (postOnly && !request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException(
					"Authentication method not supported: " + request.getMethod());
		}
		
		String username = obtainUsername(request);
        String password = obtainPassword(request);
        
        if (username == null) {
			username = "";
		}

		if (password == null) {
			password = "";
		}
		
		username = username.trim();
		
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
		
		setDetails(request, authRequest);
		
		return this.getAuthenticationManager().authenticate(authRequest);
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		/*
		 * Cookie이든 Header이든 JWT Token을 발급하는 로직이 들어간 후에 
		 * 나머지 인증 성공 처리를 부모 객체에게 넘긴다. 
		 */
		System.out.println("JwtAuthenticationFilter.successfulAuthentication ::::");
		super.successfulAuthentication(request, response, chain, authResult);
	}
	
	
}
