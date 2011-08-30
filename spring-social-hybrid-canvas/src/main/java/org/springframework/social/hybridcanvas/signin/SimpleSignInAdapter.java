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

	public String signIn(String userId, Connection<?> connection, NativeWebRequest request) {
		//userId is userName
		logger.debug("In signIn for userId {}", userId);

		//do whatever it takes to sign the user into the application that is on the main web site when they click
		//"sign in" - this will be handled by our SignInUtils
		SignInUtils.signin(userId, request);
		return null;

	}

}
