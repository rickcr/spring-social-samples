package org.springframework.social.hybridcanvas.signin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.web.context.request.NativeWebRequest;

/*
Used by ProviderSignInController
 */
public final class SimpleSignInAdapter implements SignInAdapter {
	private static Logger logger = LoggerFactory.getLogger(SimpleSignInAdapter.class);
	//private final UserCookieGenerator userCookieGenerator = new UserCookieGenerator();


	public String signIn(String userId, Connection<?> connection, NativeWebRequest request) {
		//userId is userName
		logger.debug("In signIn for userId {}", userId);


		//SecurityContext.setCurrentUser(validUsers.get(userId));
		//userCookieGenerator.addCookie(userId, request.getNativeResponse(HttpServletResponse.class));

		//do whatever it takes to sign the user into the application that is on the main web site when they click
		//"sign in" - this will be handled by our SignInUtils (Since SignInUtils also used from a standard sign in in
		//SignInController
		SignInUtils.signin(userId, request);
		return null;

	}

}
