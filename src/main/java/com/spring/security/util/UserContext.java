package com.spring.security.util;

import com.spring.security.model.ChatbotUser;

public interface UserContext {
	ChatbotUser getCurrentUser();
	void setCurrentUser(ChatbotUser user);
}
