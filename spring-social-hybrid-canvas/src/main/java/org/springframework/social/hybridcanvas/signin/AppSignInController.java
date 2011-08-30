package org.springframework.social.hybridcanvas.signin;

import org.springframework.social.connect.Connection;
import org.springframework.social.hybridcanvas.security.TempUsers;
import org.springframework.social.hybridcanvas.signup.SignupForm;
import org.springframework.social.hybridcanvas.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;

@Controller
public class AppSignInController {

	private static Logger logger = LoggerFactory.getLogger(AppSignInController.class);

	@RequestMapping(value="/signinForm", method=RequestMethod.GET)
	public String signin(WebRequest request, ModelMap model)  {
		logger.debug("In /signinForm");
		model.addAttribute(new SignInForm());
	   	return "signin";
	}

	@RequestMapping(value="/standardSignIn",method=RequestMethod.POST)
	public String standardSignIn(@Valid SignInForm signInForm, BindingResult formBinding, NativeWebRequest request) {
		logger.debug("Here in /standardSignIn");
		if (formBinding.hasErrors()) {
			logger.debug(" /standardSignIn submitted form has errors!");
			//TODO doing everything from signup page right now
 			return "signup"; //returning null would attempt to find standardSignIn.jsp
		}
		logger.debug("   userName: {}", signInForm.getUsername());

		//this just temp demo type of stuff
		User validUser = TempUsers.getUser(signInForm.getUsername());
		if (validUser != null) {
		 	//session.setAttribute("user", user);
			//silly for this demo, it ends up going tothe map again in utils,
			//in real life we might skip even calling signInUtils here, but nice to have it one place
			// in real life based on username password could just return an "id" and then signInUtils could proceed
			//as it does for FB etc and go get the user from the db based on user id
			SignInUtils.signin( validUser.getUserName(), request);

			//now connect the user's signin with their facebook account
			ProviderSignInUtils.handlePostSignUp(validUser.getUserName(), request);

			return "redirect:http://apps.facebook.com/springsocialcanvas/";
		} else {
			return "signin";
		}

	}
}
