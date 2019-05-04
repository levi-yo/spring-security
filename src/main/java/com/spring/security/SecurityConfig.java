package com.spring.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	private UserDetailsService userDetailsService;
	private PasswordEncoder passwordEncoder;
	private AuthenticationProvider authenticationProvider;
	
	public SecurityConfig(UserDetailsService userDetailsService,
						  PasswordEncoder passwordEncoder,
						  AuthenticationProvider authenticationProvider) {
		this.userDetailsService = userDetailsService;
		this.passwordEncoder = passwordEncoder;
		this.authenticationProvider = authenticationProvider;
	}

	/*
	 * AuthenticationManagerBuilder 객체는 스프링 시큐리티가 사용자를 인증하는 방법이다.
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//UserDetailsService&DaoAuthenticationProvider를 이용하는 인증
//		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
		
		//사용자 정의 AuthenticationProvider를 이용하는 인증
		auth.authenticationProvider(authenticationProvider);
	}
	
	/*
	 * HttpSecurity객체는 현재 로그인한 사용자가 적절한 역할과 연결돼 있는지 확인하는 서블릿 필터를 생성한다.
	 * ant 패턴식 설명
	 * 1) ? -> 단일 문자와 일치한다.
	 * 2) * ->/를 제외하는 0자 이상의 문자와 일치한다. (ex "/events*" == "/events","/events123")
	 * 3) ** ->경로의 0개 이상의 디렉터리와 일치한다. (ex "/events/**" == "/events","/events/","/events/1","/events/1/form?test=1")
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/*
		 * antMatchers는 순서가 굉장히 중요하다.
		 * /** 의 패턴식을 처음에 위치시킨다면 나머지는 전부 적용되지 않을 것이다.
		 */
		http.authorizeRequests()
			.antMatchers("/login/**").permitAll()
			.antMatchers("/logout/**").permitAll()
			.antMatchers("/admin/*").hasRole("ADMIN")
			.antMatchers("/events/*").hasRole("ADMIN")
			.antMatchers("/**").hasRole("USER")
			
			.and().logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login/logout")
				
			.and().csrf()
				.disable()
				
			.addFilter(jwtAuthenticationFilter())
			.exceptionHandling()
				.accessDeniedPage("/errors/403")
				.authenticationEntryPoint(loginUrlAuthenticationEntryPoint());
			
		http.headers().frameOptions().disable();
	}
	
	/*
	 * Security Filter 적용을 무시한다.
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
				.antMatchers("/resources/**")
				.antMatchers("/css/**")
		;
	}
	
	/*
	 * 폼로그인시 걸리게되는 필터 빈생성.
	 * @Override UsernamePasswordAuthenticationFilter
	 */
	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
		JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager());
		jwtAuthenticationFilter.setFilterProcessesUrl("/login");
		jwtAuthenticationFilter.setUsernameParameter("username");
		jwtAuthenticationFilter.setPasswordParameter("password");
		
		jwtAuthenticationFilter.setAuthenticationSuccessHandler(
				new SavedRequestAwareAuthenticationSuccessHandler() {{
					setDefaultTargetUrl("/index");
				}}
		);
		
		jwtAuthenticationFilter.setAuthenticationFailureHandler(
				new SimpleUrlAuthenticationFailureHandler() {{
					setDefaultFailureUrl("/login/form?error=e");
				}}
		);
		
		jwtAuthenticationFilter.afterPropertiesSet();
		
		return jwtAuthenticationFilter;
	}
	
	/*
	 * 만약 인증되지 않은 사용자가 접근한다면 스프링 시큐리티는 URL을 인터셉트하고 이를 처리하기 위해
	 * LoginUrlAuthenticationEntryPoint 객체를 사용하여 사용자를 로그인 페이지로 리다이렉트한다.
	 */
	@Bean
	public LoginUrlAuthenticationEntryPoint loginUrlAuthenticationEntryPoint() {
		return new CustomAuthenticationEntryPoint("/login/form");
	}
}
