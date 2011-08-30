package org.springframework.social.hybridcanvas.security;

import org.springframework.social.hybridcanvas.user.User;

import java.util.HashMap;
import java.util.Map;

//JUST a temp class, in real life a db
public class TempUsers {


	static Map<String,User> validUsers = new HashMap<String, User>();
	static {
		User user = new User("John","Doe","john@foo.com","foobar");
		validUsers.put(user.getUserName(), user);
	}

	public static User getUser(String userName) {
		return validUsers.get(userName);
	}

	public synchronized static void addUser(User user) {
		validUsers.put(user.getUserName(), user);
	}
}
