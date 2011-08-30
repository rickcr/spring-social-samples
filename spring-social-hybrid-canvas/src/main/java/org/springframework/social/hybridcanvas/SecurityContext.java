package org.springframework.social.hybridcanvas;

import org.springframework.social.hybridcanvas.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecurityContext {
	private static Logger logger = LoggerFactory.getLogger(SecurityContext.class);

	private static final ThreadLocal<User> currentUser = new ThreadLocal<User>();

	public static User getCurrentUser() {
		User user = currentUser.get();
		logger.debug("attempting to get current user {}", user);
		if (user == null) {
			throw new IllegalStateException("No user is currently signed in");
		}
		return user;
	}

	public static void setCurrentUser(User user) {
		logger.debug("setting current user {}", user);
		if (user != null) logger.debug(" userName {}", user.getUserName());
		currentUser.set(user);
	}

	public static boolean userSignedIn() {
		return currentUser.get() != null;
	}

	public static void remove() {
		currentUser.remove();
	}
}
