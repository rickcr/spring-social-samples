package org.springframework.social.hybridcanvas.security;

import org.springframework.social.hybridcanvas.user.User;

public interface ISecurityService {
	User getUser(String userName);
	void addUser(User user);
}
