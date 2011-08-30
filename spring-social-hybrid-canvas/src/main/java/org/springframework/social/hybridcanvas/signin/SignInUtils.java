package org.springframework.social.hybridcanvas.signin;

import org.springframework.social.hybridcanvas.SecurityContext;
import org.springframework.social.hybridcanvas.security.ISecurityService;
import org.springframework.social.hybridcanvas.security.SecurityService;
import org.springframework.social.hybridcanvas.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/*
Does the real sign in on the server side based on the 'userId'
we could have gotten this userId by them signing in the standard way
or this SignInUTils is used by the SignInAdapter used by ProviderSignInController
to sign the user in when the get the userId based on the userConnectionRepository and
being signed in through fb

TODO make this signIn non-static and inject ISecurityService
 */

public class SignInUtils {
	private static Logger logger = LoggerFactory.getLogger(SignInUtils.class);

	private static ISecurityService securityService = new SecurityService();

	public static void signin(String userId, NativeWebRequest request) {
		logger.debug("In signIn of SignInUtils for userId {}", userId);
		//sign the user in
		HttpSession session = ((HttpServletRequest)request.getNativeRequest(HttpServletRequest.class)).getSession();
		//get the real user from backend
		User user = securityService.getUser(userId);
		session.setAttribute("user", user);
		SecurityContext.setCurrentUser(user);
		logger.debug("   Setting user {} in session in securityContext", user);
	}
}
