package org.springframework.social.hybridcanvas.security;

import org.springframework.social.hybridcanvas.user.User;

import java.util.HashMap;
import java.util.Map;

public class SecurityService implements ISecurityService {

	//Real life you'd use a DB
	static Map<String,User> validUsers = new HashMap<String, User>();
	static {
		User user = new User("John","Doe","john@foo.com","foobar");
		validUsers.put(user.getUserName(), user);
	}

	@Override
	public User getUser(String userName) {
		return validUsers.get(userName);
	}

	@Override
	public synchronized void addUser(User user) {
		validUsers.put(user.getUserName(), user);
	}
}
