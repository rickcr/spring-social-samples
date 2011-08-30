package org.springframework.social.hybridcanvas;

import org.springframework.social.hybridcanvas.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UserInterceptor extends HandlerInterceptorAdapter {

	private final String signUpPath = "/signup";
	private final String standardSignInPath = "/standardSignIn";

	private static Logger logger = LoggerFactory.getLogger(UserInterceptor.class);

	private final UsersConnectionRepository usersConnectionRepository;

	public UserInterceptor(UsersConnectionRepository usersConnectionRepository) {
		this.usersConnectionRepository = usersConnectionRepository;
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		logger.debug("preHandle");
		//put user in SecurityContext if they are in session:
 		rememberUser(request, response);
		//allow user to signOut if that's what they're attempting (/signout)
		if (handleSignOut(request, response)) {
			new RedirectView("/signedOut.jsp", true).render(null, request, response);
			return false;
		}
		if (SecurityContext.userSignedIn() || requestForSignInOrSignUp(request)) {
			if (logger.isDebugEnabled()) {
				if (SecurityContext.userSignedIn()) { logger.debug("user is signed in, let filter continue"); }
				else if (requestForSignInOrSignUp(request)) { logger.debug("user isn't signed in but attempting to go to signin or signup, which is allowed"); }
			}
			return true;
		} else {
			//signin page will do a force connect to facebook
			return requireSignIn(request, response);
		}
	}

	// internal helpers
	private void rememberUser(HttpServletRequest request, HttpServletResponse response) {
		logger.debug("rememberUser");
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		if (user == null) {
			logger.debug("   user null, returning from remember user without setting SecurityContext");
			return;
		}
		logger.debug("   user not null, setting SecurityContext with user");
		SecurityContext.setCurrentUser(user);
	}

	// signout - TODO maybe just forward this to a controller to do it?
	private boolean handleSignOut(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (SecurityContext.userSignedIn() && request.getServletPath().startsWith("/signout")) {
			logger.debug("Doing a sign out, sing user requesting /signout");
			request.getSession().invalidate();
			SecurityContext.remove();
	        return true;
		}
		return false;
	}

	private boolean requestForSignInOrSignUp(HttpServletRequest request) {
		String servletPath = request.getServletPath();
		return servletPath.startsWith("/signin") ||
			servletPath.startsWith(standardSignInPath) ||
			servletPath.startsWith(signUpPath);
	}

	private boolean requireSignIn(HttpServletRequest request, HttpServletResponse response) throws Exception {
		new RedirectView("/signin.jsp", true).render(null, request, response);
		return false;
	}

}
