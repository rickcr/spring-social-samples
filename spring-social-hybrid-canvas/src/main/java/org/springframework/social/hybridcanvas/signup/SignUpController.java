package org.springframework.social.hybridcanvas.signup;

import org.omg.CORBA.Request;
import org.springframework.social.hybridcanvas.message.Message;
import org.springframework.social.hybridcanvas.message.MessageType;
import org.springframework.social.hybridcanvas.security.ISecurityService;
import org.springframework.social.hybridcanvas.signin.SignInForm;
import org.springframework.social.hybridcanvas.signin.SignInUtils;
import org.springframework.social.hybridcanvas.user.User;
import org.springframework.social.hybridcanvas.user.UsernameAlreadyInUseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.inject.Inject;
import javax.validation.Valid;

@Controller
public class SignUpController {

	private static Logger logger = LoggerFactory.getLogger(SignUpController.class);

	@Inject
	private ISecurityService securityService;

	//fb app will post to this, but validation fails will call it as get
	@RequestMapping(value="/signup")
	public String signupForm(ModelMap model, WebRequest request) {
		logger.debug("in /signup of SignUpController");
		Connection<?> connection = ProviderSignInUtils.getConnection(request);
		if (connection != null) {
			request.setAttribute("message", new Message(MessageType.INFO, "Your " + StringUtils.capitalize(connection.getKey().getProviderId()) + " account is not associated with a Spring Social account. If you're new, please sign up."), WebRequest.SCOPE_REQUEST);
			SignupForm signupForm = SignupForm.fromProviderUser(connection.fetchUserProfile());
			model.addAttribute(signupForm);
			model.addAttribute(new SignInForm());
			return "signup";
		} else {
			//redirect back to signin
			return "redirect:/";
		}
	}

	@RequestMapping(value="/signupSubmit")
	public String signup(ModelMap model, @Valid SignupForm form, BindingResult formBinding, WebRequest request) {
		logger.debug("in signupSubmit of form submit");
		if (formBinding.hasErrors()) {
			logger.debug("errors found in submit of signupSubmit");
			//since we're doing signIn and SignUp on some page, need signInForm in scope
			model.addAttribute(new SignInForm());
			return "signup";
		}
		User account = createAccount(form, formBinding);
		if (account != null) {
			SignInUtils.signin(account.getUserName(), (NativeWebRequest)request);
			ProviderSignInUtils.handlePostSignUp(account.getUserName(), request);
			return "redirect:/";
		}
		return null;
	}

	// internal helpers
	private User createAccount(SignupForm form, BindingResult formBinding) {
		try {
			User user = new User(form.getFirstName(), form.getLastName(), form.getUsername(), form.getPassword());
			if (securityService.getUser(user.getUserName()) != null) {
				throw new UsernameAlreadyInUseException(user.getUserName());
			}
			securityService.addUser(user);
			return user;
		} catch (UsernameAlreadyInUseException e) {
			formBinding.rejectValue("username", "user.duplicateUsername", "already in use");
			return null;
		}
	}

}
