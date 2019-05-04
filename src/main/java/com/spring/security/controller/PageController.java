package com.spring.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
	
	@GetMapping("/login/form")
	public String loginPage(String error) {
		if(error!=null && error.equals("e")) {
			System.out.println("인증실패");
		}else {
			System.out.println("로그인 페이지로 이동");
		}
		
		return "login";
	}
	
	@GetMapping("/login/logout")
	public String logoutSuccess() {
		System.out.println("로그아웃 -> 로그아웃 페이지로 이동");
		return "loginlogout";
	}
	
	@GetMapping("/index")
	public String loginSuccessPage() {
		System.out.println("로그인성공 -> index페이지로 이동");
		return "index";
	}
	
	@GetMapping("/errors/403")
	public String errorPage() {
		return "403";
	}
}
