package com.spring.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.spring.security.model.ChatbotUser;
import com.spring.security.util.AuthoritiesUtils;



@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired private UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("UserDetailsServiceImpl.loadUserByUsername ::::");
		
		ChatbotUser user = userService.findByUserId(username);
		
		if(ObjectUtils.isEmpty(user)) {
			throw new UsernameNotFoundException("Invalid username");
		}
		
//		ArrayList<GrantedAuthority> authorities = new ArrayList<>();
//		authorities.add(new SimpleGrantedAuthority(user.getRole().getValue()));
//		추후에는 엔티티 안에 권한 필드를 넣어서 권한 정보를 전달하도록 한다. 예를 들어 getAuthorities를 하면 권한 필드의 내용을 리스트로 반환하는 등에 로직을 추가한다.
		
		return new User(user.getUsername(),user.getPassword(),AuthoritiesUtils.createAuthorities(user));
	}
	
}
/*
 * formLogin()가 스프링 시큐리티에게 UsernamePasswordAuthenticationFilter를 사용해 로그인 컨트롤러로 작동하도록 지시한다.
 * 
 * 1)http 요청에서 사용자명과 패스워드를 획득한다.
 * 2)http 요청에서 얻은 정보로 UserPasswordAuthenticationToken객체를 생성한다.
 * 3)스프링 시큐리티가 UsernamePasswordAuthenticationToken을 검증하도록 요청한다.
 * 4)토큰의 유효성이 검증되면, 새로운 사용자가 계정을 등록할 때와 마찬가지로 SecurityContextHolder에 반환된 인증결과를 설정한다.
 */
//public class UsernamePasswordAuthenticationFilter extends
//AbstractAuthenticationProcessingFilter {
//// ~ Static fields/initializers
//// =====================================================================================
//
//public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";
//public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";
//
//private String usernameParameter = SPRING_SECURITY_FORM_USERNAME_KEY;
//private String passwordParameter = SPRING_SECURITY_FORM_PASSWORD_KEY;
//private boolean postOnly = true;
//
//// ~ Constructors
//// ===================================================================================================
//
//public UsernamePasswordAuthenticationFilter() {
//super(new AntPathRequestMatcher("/login", "POST"));
//}
//
//// ~ Methods
//// ========================================================================================================
//
//public Authentication attemptAuthentication(HttpServletRequest request,
//	HttpServletResponse response) throws AuthenticationException {
//if (postOnly && !request.getMethod().equals("POST")) {
//	throw new AuthenticationServiceException(
//			"Authentication method not supported: " + request.getMethod());
//}
//
//String username = obtainUsername(request);
//String password = obtainPassword(request);
//
//if (username == null) {
//	username = "";
//}
//
//if (password == null) {
//	password = "";
//}
//
//username = username.trim();
//
//UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
//		username, password);
//
//// Allow subclasses to set the "details" property
//setDetails(request, authRequest);
//
//return this.getAuthenticationManager().authenticate(authRequest);
//}
//
///**
//* Enables subclasses to override the composition of the password, such as by
//* including additional values and a separator.
//* <p>
//* This might be used for example if a postcode/zipcode was required in addition to
//* the password. A delimiter such as a pipe (|) should be used to separate the
//* password and extended value(s). The <code>AuthenticationDao</code> will need to
//* generate the expected password in a corresponding manner.
//* </p>
//*
//* @param request so that request attributes can be retrieved
//*
//* @return the password that will be presented in the <code>Authentication</code>
//* request token to the <code>AuthenticationManager</code>
//*/
//protected String obtainPassword(HttpServletRequest request) {
//return request.getParameter(passwordParameter);
//}
//
///**
//* Enables subclasses to override the composition of the username, such as by
//* including additional values and a separator.
//*
//* @param request so that request attributes can be retrieved
//*
//* @return the username that will be presented in the <code>Authentication</code>
//* request token to the <code>AuthenticationManager</code>
//*/
//protected String obtainUsername(HttpServletRequest request) {
//return request.getParameter(usernameParameter);
//}
//
///**
//* Provided so that subclasses may configure what is put into the authentication
//* request's details property.
//*
//* @param request that an authentication request is being created for
//* @param authRequest the authentication request object that should have its details
//* set
//*/
//protected void setDetails(HttpServletRequest request,
//	UsernamePasswordAuthenticationToken authRequest) {
//authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
//}
//
///**
//* Sets the parameter name which will be used to obtain the username from the login
//* request.
//*
//* @param usernameParameter the parameter name. Defaults to "username".
//*/
//public void setUsernameParameter(String usernameParameter) {
//Assert.hasText(usernameParameter, "Username parameter must not be empty or null");
//this.usernameParameter = usernameParameter;
//}
//
///**
//* Sets the parameter name which will be used to obtain the password from the login
//* request..
//*
//* @param passwordParameter the parameter name. Defaults to "password".
//*/
//public void setPasswordParameter(String passwordParameter) {
//Assert.hasText(passwordParameter, "Password parameter must not be empty or null");
//this.passwordParameter = passwordParameter;
//}
//
///**
//* Defines whether only HTTP POST requests will be allowed by this filter. If set to
//* true, and an authentication request is received which is not a POST request, an
//* exception will be raised immediately and authentication will not be attempted. The
//* <tt>unsuccessfulAuthentication()</tt> method will be called as if handling a failed
//* authentication.
//* <p>
//* Defaults to <tt>true</tt> but may be overridden by subclasses.
//*/
//public void setPostOnly(boolean postOnly) {
//this.postOnly = postOnly;
//}
//
//public final String getUsernameParameter() {
//return usernameParameter;
//}
//
//public final String getPasswordParameter() {
//return passwordParameter;
//}
//}