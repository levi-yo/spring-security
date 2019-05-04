package com.spring.security.model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * 
 * @author yun-yeoseong
 *
 */
@Getter
@Setter
@ToString
@Entity
public class ChatbotUser implements UserDetails{
	
	private static final long serialVersionUID = -4608347932140057654L;
	
	@Id
	private Long id;
	private String username;
	private String password;
	
	@Transient
	private Collection<? extends GrantedAuthority> authorities;
	@Transient
	private boolean accountNonExpired = true;
	@Transient
	private boolean accountNonLocked = true;
	@Transient
	private boolean credentialsNonExpired = true;
	@Transient
	private boolean enabled = true;
	
}
